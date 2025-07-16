package jp.co.sgk.yubion.fss.sdk.test.util;

import com.upokecenter.cbor.CBORObject;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPoint;
import java.security.spec.ECParameterSpec;
import java.util.Arrays;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.ECGenParameterSpec;

public class PublicKeyToCoseKeyConverter {

	// COSE Key Types (kty)
	public static final int COSE_KTY_OKP = 1; // Octet Key Pair
	public static final int COSE_KTY_EC2 = 2; // Elliptic Curve Public Key
	public static final int COSE_KTY_RSA = 3; // RSA Public Key

	// COSE Algorithms (alg)
	public static final int COSE_ALG_ES256 = -7; // ECDSA with P-256 and SHA-256
	public static final int COSE_ALG_RS256 = -257; // RSASSA-PKCS1-v1_5 with SHA-256

	// COSE Elliptic Curves (crv)
	public static final int COSE_CRV_P256 = 1; // NIST P-256

	/**
	 * java.security.PublicKey を COSE Key (CBOR byte array) に変換します。
	 * 現在は EC 公開鍵 (P-256) のみサポート。
	 * RSA 鍵や他の曲線、アルゴリズムが必要な場合は追加の実装が必要です。
	 *
	 * @param publicKey 変換する PublicKey オブジェクト
	 * @return COSE Key 形式のバイト配列
	 * @throws IllegalArgumentException サポートされていない鍵タイプの場合
	 */
	public static byte[] toCoseKeyBytes(PublicKey publicKey) throws IllegalArgumentException {
		CBORObject coseKeyMap = CBORObject.NewMap();

		String algorithm = publicKey.getAlgorithm();

		if ("EC".equalsIgnoreCase(algorithm)) {
			ECPublicKey ecPublicKey = (ECPublicKey) publicKey;
			//ECParameterSpec params = ecPublicKey.getParams();
			ECPoint point = ecPublicKey.getW();

			// COSE Key Type (kty): EC2
			coseKeyMap.Add(CBORObject.FromObject(1), CBORObject.FromObject(COSE_KTY_EC2));

			// COSE Curve (crv): P-256
			// WebAuthnでは通常P-256が使われる
			// 実際のECParameterSpecからCOSE_CRV_P256を特定する必要があるが、
			// ここでは簡単のため決め打ち（secp256r1 == P-256）
			//if (params.getCurve().equals(getNistP256Curve())) { // 適切な比較方法に注意
				coseKeyMap.Add(CBORObject.FromObject(-1), CBORObject.FromObject(COSE_CRV_P256));
			//} else {
			//	throw new IllegalArgumentException("Unsupported EC curve for COSE Key conversion: " + params.getCurve().toString());
			//}

			// COSE Algorithm (alg): ES256 (P-256の場合)
			coseKeyMap.Add(CBORObject.FromObject(3), CBORObject.FromObject(COSE_ALG_ES256));

			// EC x-coordinate (-2)
			byte[] xBytes = toByteArray(point.getAffineX(), 32); // P-256は256ビット=32バイト
			coseKeyMap.Add(CBORObject.FromObject(-2), CBORObject.FromObject(xBytes));

			// EC y-coordinate (-3)
			byte[] yBytes = toByteArray(point.getAffineY(), 32); // P-256は256ビット=32バイト
			coseKeyMap.Add(CBORObject.FromObject(-3), CBORObject.FromObject(yBytes));

		} else if ("RSA".equalsIgnoreCase(algorithm)) {
			// TODO: RSA PublicKey の変換ロジックを追加
			// RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
			// BigInteger modulus = rsaPublicKey.getModulus();
			// BigInteger exponent = rsaPublicKey.getPublicExponent();
			// ...
			throw new IllegalArgumentException("RSA Public Key conversion to COSE Key not yet implemented.");
		} else {
			throw new IllegalArgumentException("Unsupported Public Key algorithm for COSE Key conversion: " + algorithm);
		}

		// CBORオブジェクトをバイト配列にエンコード
		return coseKeyMap.EncodeToBytes();
	}

	/**
	 * BigIntegerを固定長バイト配列に変換します。
	 * バイト配列は常に正の値として扱われ、指定された長さになるようにゼロパディングされます。
	 * @param bi BigInteger to convert
	 * @param fixedLength 目的のバイト配列の長さ
	 * @return 固定長バイト配列
	 */
	private static byte[] toByteArray(BigInteger bi, int fixedLength) {
		byte[] bytes = bi.toByteArray();
		// BigInteger.toByteArray() は必要に応じて符号ビットのために先頭に0x00を追加する
		// その場合はその0x00を取り除く
		int offset = 0;
		if (bytes.length == fixedLength + 1 && bytes[0] == 0) {
			offset = 1;
		} else if (bytes.length < fixedLength) {
			// パディングが必要な場合
			byte[] padded = new byte[fixedLength];
			System.arraycopy(bytes, 0, padded, fixedLength - bytes.length, bytes.length);
			return padded;
		} else if (bytes.length > fixedLength) {
			throw new IllegalArgumentException("BigInteger value is too large for fixed length: " + fixedLength + " bytes");
		}
		return Arrays.copyOfRange(bytes, offset, offset + fixedLength);
	}

	// NIST P-256曲線のECParameterSpecを取得するヘルパーメソッド
	// これは簡易的なものであり、実際の比較ではより堅牢な方法が必要です。
	private static java.security.spec.ECParameterSpec getNistP256Curve() {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
			kpg.initialize(new ECGenParameterSpec("secp256r1"));
			KeyPair kp = kpg.generateKeyPair();
			return ((ECPublicKey) kp.getPublic()).getParams();
		} catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
			throw new RuntimeException("Failed to get NIST P-256 curve spec", e);
		}
	}


	public static void main(String[] args) throws Exception {
		// EC 公開鍵の生成 (P-256)
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
		ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1"); // P-256 (NIST-P256)
		keyGen.initialize(ecSpec);
		KeyPair keyPair = keyGen.generateKeyPair();
		PublicKey ecPublicKey = keyPair.getPublic();

		System.out.println("Generated EC Public Key:");
		System.out.println("  Algorithm: " + ecPublicKey.getAlgorithm());
		ECPublicKey ecPub = (ECPublicKey) ecPublicKey;
		System.out.println("  X: " + ecPub.getW().getAffineX().toString(16));
		System.out.println("  Y: " + ecPub.getW().getAffineY().toString(16));
		System.out.println("  Curve: " + ecPub.getParams().getCurve().getField().getFieldSize() + " bits");


		// COSE Key 形式に変換
		byte[] coseKeyBytes = toCoseKeyBytes(ecPublicKey);

		System.out.println("\nCOSE Key (Hex): " + bytesToHex(coseKeyBytes));
		System.out.println("COSE Key Length: " + coseKeyBytes.length + " bytes");

		// COSE Key をCBORObjectとして再パースして内容を確認（デバッグ用）
		CBORObject parsedCoseKey = CBORObject.DecodeFromBytes(coseKeyBytes);
		System.out.println("\nParsed COSE Key CBOR (for verification):");
		System.out.println(parsedCoseKey.toString());
		// 例えば、ktyの確認: parsedCoseKey.Get(CBORObject.FromObject(1)).AsInt32() == COSE_KTY_EC2
		// x, y 座標の確認: parsedCoseKey.Get(CBORObject.FromObject(-2)).GetByteString()
	}

	// バイト配列を16進数文字列に変換するヘルパー
	private static String bytesToHex(byte[] hash) {
		StringBuilder hexString = new StringBuilder(2 * hash.length);
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
}