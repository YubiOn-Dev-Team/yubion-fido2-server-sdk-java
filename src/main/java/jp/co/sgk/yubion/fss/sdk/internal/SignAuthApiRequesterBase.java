package jp.co.sgk.yubion.fss.sdk.internal;

import jp.co.sgk.yubion.fss.sdk.config.FssSdkConfig;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.DLSequence;

public abstract class SignAuthApiRequesterBase extends FssApiRequesterBase {

	private PrivateKey privateKey;

	protected SignAuthApiRequesterBase(FssSdkConfig config, boolean denyUnknownResponseMember, boolean allowBadSslForDebug) {
		super(config, denyUnknownResponseMember, allowBadSslForDebug);
	}

	private PrivateKey getPrivateKey() throws Exception {
		if (this.privateKey == null) {
			byte[] keyBytes = Base64.getUrlDecoder().decode(config.getSecretKey());
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("EC");
			this.privateKey = keyFactory.generatePrivate(keySpec);
		}
		return this.privateKey;
	}

	@Override
	protected Map<String, String> getAdditionalHeaders(byte[] body) throws Exception {
		Map<String, String> headers = new HashMap<>();
		headers.put("X-Fss-Rp-Id", config.getRpId());
		headers.put("X-Fss-Api-Auth-Id", config.getApiAuthId());

		// Get sign info from subclass
		SignInfo signInfo = getSignInfo(body);
		headers.putAll(signInfo.getAdditionalHeaders());

		// Calculate body hash
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] bodyHash = digest.digest(body);
		String bodyHashBase64Url = Base64.getUrlEncoder().withoutPadding().encodeToString(bodyHash);
		headers.put("X-Fss-Auth-Body-Hash", bodyHashBase64Url);

		// Create sign target
		byte[] signTargetPrefix = signInfo.getSignTargetPrefix();
		byte[] signTarget = new byte[signTargetPrefix.length + bodyHash.length];
		System.arraycopy(signTargetPrefix, 0, signTarget, 0, signTargetPrefix.length);
		System.arraycopy(bodyHash, 0, signTarget, signTargetPrefix.length, bodyHash.length);

		// Sign
		Signature ecdsaSign = Signature.getInstance("SHA256withECDSA", "BC");
		ecdsaSign.initSign(getPrivateKey());
		ecdsaSign.update(signTarget);
		byte[] signatureBytes = convertDerToRs(ecdsaSign.sign(), 32);
		String signatureBase64Url = Base64.getUrlEncoder().withoutPadding().encodeToString(signatureBytes);
		headers.put("X-Fss-Auth-Signature", signatureBase64Url);

		return headers;
	}

	protected abstract SignInfo getSignInfo(byte[] body) throws Exception;

	/**
	 * ASN.1 DERエンコードされたECDSA署名からRとSを抽出し、R || S形式に変換します。
	 * @param derSignature ASN.1 DERエンコードされた署名バイト配列
	 * @param expectedRsLength RまたはSの期待されるバイト長 (例: P-256なら32)
	 * @return R || S形式の署名バイト配列
	 * @throws IOException ASN.1パースエラー
	 */
	public static byte[] convertDerToRs(byte[] derSignature, int expectedRsLength) throws IOException {
		try (ASN1InputStream asn1InputStream = new ASN1InputStream(derSignature)) {
			DLSequence sequence = (DLSequence) asn1InputStream.readObject();
			if (sequence == null || sequence.size() != 2) {
				throw new IOException("Invalid DER sequence for ECDSA signature.");
			}

			ASN1Integer rASN1 = (ASN1Integer) sequence.getObjectAt(0);
			ASN1Integer sASN1 = (ASN1Integer) sequence.getObjectAt(1);

			BigInteger r = rASN1.getPositiveValue(); // 正の値として取得
			BigInteger s = sASN1.getPositiveValue(); // 正の値として取得

			byte[] rBytes = toFixedLengthBytes(r, expectedRsLength);
			byte[] sBytes = toFixedLengthBytes(s, expectedRsLength);

			// RとSを連結
			return ByteBuffer.allocate(rBytes.length + sBytes.length)
							 .put(rBytes)
							 .put(sBytes)
							 .array();
		}
	}

	/**
	 * BigIntegerを指定された固定長バイト配列に変換します。
	 * 必要に応じてゼロパディングまたは先頭のゼロバイトをトリムします。
	 * @param value 変換するBigInteger
	 * @param length 目的のバイト長
	 * @return 指定された固定長のバイト配列
	 * @throws IllegalArgumentException 変換できない場合
	 */
	private static byte[] toFixedLengthBytes(BigInteger value, int length) {
		byte[] bytes = value.toByteArray(); // BigIntegerのバイト配列 (符号ビット付き、先頭のゼロはトリム済み)

		if (bytes.length == length) {
			return bytes;
		}

		// 長さが超過している場合（通常は発生しないはずだが、念のため）
		if (bytes.length > length) {
			// 先頭の0x00 (符号ビット) が付いている場合、それを除去できるか確認
			if (bytes[0] == 0 && bytes.length == length + 1) {
				byte[] trimmedBytes = new byte[length];
				System.arraycopy(bytes, 1, trimmedBytes, 0, length);
				return trimmedBytes;
			}
			throw new IllegalArgumentException("Value is too large for the specified length: " + bytes.length + " vs " + length);
		}

		// 長さが足りない場合、先頭を0x00でパディング
		byte[] paddedBytes = new byte[length];
		System.arraycopy(bytes, 0, paddedBytes, length - bytes.length, bytes.length);
		return paddedBytes;
	}
	/**
	 * Inner class to hold signing information.
	 */
	protected static class SignInfo {
		private final byte[] signTargetPrefix;
		private final Map<String, String> additionalHeaders;

		public SignInfo(byte[] signTargetPrefix, Map<String, String> additionalHeaders) {
			this.signTargetPrefix = signTargetPrefix;
			this.additionalHeaders = additionalHeaders;
		}

		public byte[] getSignTargetPrefix() {
			return signTargetPrefix;
		}

		public Map<String, String> getAdditionalHeaders() {
			return additionalHeaders;
		}
	}
}
