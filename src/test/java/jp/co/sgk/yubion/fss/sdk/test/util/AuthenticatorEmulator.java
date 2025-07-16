package jp.co.sgk.yubion.fss.sdk.test.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.upokecenter.cbor.CBORObject;

import jp.co.sgk.yubion.fss.sdk.data.fido.AuthenticatorAssertionResponse;
import jp.co.sgk.yubion.fss.sdk.data.fido.AuthenticatorAttestationResponse;
import jp.co.sgk.yubion.fss.sdk.data.fido.PublicKeyCredential;
import jp.co.sgk.yubion.fss.sdk.data.fido.PublicKeyCredentialCreationOptionsJSON;
import jp.co.sgk.yubion.fss.sdk.data.fido.PublicKeyCredentialForAuthentication;
import jp.co.sgk.yubion.fss.sdk.data.fido.PublicKeyCredentialRequestOptionsJSON;
import jp.co.sgk.yubion.fss.sdk.data.fido.ResidentKeyRequirement;

public class AuthenticatorEmulator {
	private class Cred {
		private String credId;
		private boolean residentKey;
		private KeyPair keyPair;
		private String userId;
		
		public Cred(String credId, boolean residentKey, KeyPair keyPair, String userId) {
			this.credId = credId;
			this.residentKey = residentKey;
			this.keyPair = keyPair;
			this.userId = userId;
		}
		
	}
	private String rpId;
	private Map<String, Cred> credentials;

	public AuthenticatorEmulator(String rpId){
		this.rpId = rpId;
		credentials = new HashMap<String, Cred>();
		
	}
	public PublicKeyCredential makeCred(String origin, PublicKeyCredentialCreationOptionsJSON options) throws IOException, NoSuchAlgorithmException, InvalidAlgorithmParameterException{
		if(!options.getRp().getId().equals(rpId)){
			throw new InvalidParameterException("rpId is not match");
		}
		boolean residentKey = false;
		
		if(options.getAuthenticatorSelection() != null){
			var sel = options.getAuthenticatorSelection();
			if(sel.getRequireResidentKey() != null && sel.getRequireResidentKey().equals(true)){
				residentKey = true;
			}
			if(sel.getResidentKey() == ResidentKeyRequirement.REQUIRED || sel.getResidentKey() == ResidentKeyRequirement.PREFERRED){
				residentKey = true;
			}
		}
		
		// 鍵ペア生成器
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC"); // Elliptic Curve
		ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1");

		// KeyPairGenerator を指定した曲線で初期化
		//keyGen.initialize(ecSpec);
		// 乱数生成器
		SecureRandom randomGen = SecureRandom.getInstance("SHA1PRNG");
		// 鍵サイズと乱数生成器を指定して鍵ペア生成器を初期化
		keyGen.initialize(ecSpec, randomGen);

		// 鍵ペア生成
		KeyPair keyPair = keyGen.generateKeyPair();
		// 公開鍵
		PublicKey publicKey = keyPair.getPublic();
				
		byte[] credId = null;
		String credIdStr = null;
		SecureRandom random = new SecureRandom();
		do {
			byte[] bytes = new byte[16];
			random.nextBytes(bytes);
			credId = bytes;
			credIdStr = Base64.getUrlEncoder().withoutPadding().encodeToString(credId);
		} while (credentials.containsKey(credIdStr));
		
		Cred cred;
		cred = new Cred(credIdStr, residentKey, keyPair, options.getUser().getId());
		credentials.put(credIdStr, cred);
		PublicKeyCredential ret = new PublicKeyCredential();
		ret.setId(credIdStr);
		ret.setRawId(credIdStr);
		ret.setType("public-key");
		var attestationResp = new AuthenticatorAttestationResponse();
		
		ByteArrayOutputStream authDataBaos = new ByteArrayOutputStream();
		DataOutputStream authDataDos = new DataOutputStream(authDataBaos);
		MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
		byte[] rpIdHash = sha256.digest(rpId.getBytes());
		authDataDos.write(rpIdHash);
		authDataDos.write((byte)(0x45));	//UP,UV,AT
		authDataDos.writeInt(0);	//signcount
		authDataDos.write(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,});	//AAGUID(16)
		authDataDos.writeShort(16);	//credIdLen
		authDataDos.write(credId);
		authDataDos.write(PublicKeyToCoseKeyConverter.toCoseKeyBytes(publicKey));
		
		CBORObject attestationObj = CBORObject.NewMap();
		attestationObj.Add(CBORObject.FromObject("fmt"), CBORObject.FromObject("none"));
		attestationObj.Add(CBORObject.FromObject("attStmt"), CBORObject.NewMap());
		attestationObj.Add(CBORObject.FromObject("authData"), CBORObject.FromObject(authDataBaos.toByteArray()));
		
		attestationResp.setAttestationObject(Base64.getUrlEncoder().withoutPadding().encodeToString(attestationObj.EncodeToBytes()));
		var clientDataJson = "{\"type\":\"webauthn.create\",\"challenge\":\"" + options.getChallenge() + "\",\"origin\":\"" + origin + "\"}";
		attestationResp.setClientDataJSON(Base64.getUrlEncoder().withoutPadding().encodeToString(clientDataJson.getBytes(StandardCharsets.UTF_8)));
		ret.setResponse(attestationResp);

		return ret;
	}
	public PublicKeyCredentialForAuthentication getAssr(String origin, PublicKeyCredentialRequestOptionsJSON options) throws IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException{
		if(!options.getRpId().equals(rpId)){
			throw new InvalidParameterException("rpId is not match");
		}
		Cred cred;
		if(options.getAllowCredentials().length > 0){
			cred = credentials.values().stream().filter((x) -> Arrays.stream(options.getAllowCredentials()).filter((y) -> y.getId().equals(x.credId)).count() > 0).findFirst().orElse(null);
		} else {
			cred = credentials.values().stream().findFirst().orElse(null);
		}
		if(cred == null){
			throw new InvalidParameterException("allowCredentials is not match");
		}
		PublicKeyCredentialForAuthentication ret = new PublicKeyCredentialForAuthentication();
		ret.setId(cred.credId);
		ret.setRawId(cred.credId);
		ret.setType("public-key");
		var assertionResp = new AuthenticatorAssertionResponse();
		var clientDataJson = "{\"type\":\"webauthn.get\",\"challenge\":\"" + options.getChallenge() + "\",\"origin\":\"" + origin + "\"}";
		var clientDataJsonBin = clientDataJson.getBytes(StandardCharsets.UTF_8);
		assertionResp.setClientDataJSON(Base64.getUrlEncoder().withoutPadding().encodeToString(clientDataJsonBin));
		assertionResp.setUserHandle(cred.userId);
		byte[] authDataBin;
		try(ByteArrayOutputStream authDataBaos = new ByteArrayOutputStream()){
			try(DataOutputStream authDataDos = new DataOutputStream(authDataBaos)){
				MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
				byte[] rpIdHash = sha256.digest(rpId.getBytes());
				authDataDos.write(rpIdHash);
				authDataDos.write((byte)(0x05));	//UP,UV
				authDataDos.writeInt(0);	//signcount
				authDataBin = authDataBaos.toByteArray();
				assertionResp.setAuthenticatorData(Base64.getUrlEncoder().withoutPadding().encodeToString(authDataBin));
			}
		}
		String signatureAlgorithm = "SHA256withECDSA"; // EC鍵ペアなのでECDSAを使用
		
		MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
		byte[] clientDataJsonHash = sha256.digest(clientDataJsonBin);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(authDataBin);
		baos.write(clientDataJsonHash);
		Signature signer = Signature.getInstance(signatureAlgorithm);
		// 署名モードに初期化し、秘密鍵を設定
		signer.initSign(cred.keyPair.getPrivate());
		// 署名対象のデータを設定
		signer.update(baos.toByteArray());
		// 署名を生成
		byte[] signatureBytes = signer.sign();
		assertionResp.setSignature(Base64.getUrlEncoder().withoutPadding().encodeToString(signatureBytes));

		ret.setResponse(assertionResp);
		
		return ret;
	}
}