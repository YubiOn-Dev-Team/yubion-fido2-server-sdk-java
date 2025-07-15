package jp.co.sgk.yubion.fss.sdk.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Base64;
import java.util.HashMap;
import java.util.function.IntFunction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.devtools.v85.webauthn.model.Credential;
//import org.openqa.selenium.virtualauthenticator.HasVirtualAuthenticator;
//import org.openqa.selenium.virtualauthenticator.VirtualAuthenticator;
//import org.openqa.selenium.virtualauthenticator.VirtualAuthenticatorOptions;

import jp.co.sgk.yubion.fss.sdk.YubiOnFssSdk;
import jp.co.sgk.yubion.fss.sdk.data.fido.AttestationConveyancePreference;
import jp.co.sgk.yubion.fss.sdk.data.fido.AuthenticatorSelectionCriteria;
import jp.co.sgk.yubion.fss.sdk.data.fido.Fido2CreationOptionsBase;
import jp.co.sgk.yubion.fss.sdk.data.fido.Fido2FinishAuthenticateParameter;
import jp.co.sgk.yubion.fss.sdk.data.fido.Fido2FinishRegisterParameter;
import jp.co.sgk.yubion.fss.sdk.data.fido.Fido2RequestOptionsBase;
import jp.co.sgk.yubion.fss.sdk.data.fido.Fido2StartAuthenticateParameter;
import jp.co.sgk.yubion.fss.sdk.data.fido.Fido2StartRegisterParameter;
import jp.co.sgk.yubion.fss.sdk.data.fido.ResidentKeyRequirement;
import jp.co.sgk.yubion.fss.sdk.data.fido.UserVerificationRequirement;
import jp.co.sgk.yubion.fss.sdk.data.user.UserDataRegisterParameter;
import jp.co.sgk.yubion.fss.sdk.test.util.AuthenticatorEmulator;

public class YubiOnFssSdkFidoTest {
	private static final boolean ALLOW_BAD_SSL = YubiOnFssSdkTestConfig.ALLOW_BAD_SSL;
	private static final boolean DENY_UNKNOWN_RESPONSE_MEMBER = YubiOnFssSdkTestConfig.DENY_UNKNOWN_RESPONSE_MEMBER;
	private static String _makeUserId(IntFunction<Byte> func) {
		return _makeUserId(func, 64);
	}
	private static String _makeUserId(IntFunction<Byte> func, int length) {
		var buf = new byte[length];
		for(int i = 0; i < length; i++) {
			buf[i] = func.apply(i);
		}
		return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
	}
	private static YubiOnFssSdk _sdk(){
		return new YubiOnFssSdk(YubiOnFssSdkTestConfig.createNonceConfig(true), DENY_UNKNOWN_RESPONSE_MEMBER, ALLOW_BAD_SSL);
	}
	private static final UserDataRegisterParameter[] testUsers = new UserDataRegisterParameter[] {
		new UserDataRegisterParameter(
			_makeUserId(i -> (byte)(i + 30)),
			"user1@example.com",
			"user 1",
			new HashMap<String, Object>(){ { put("attr1", "user1"); } },
			false
		),
		new UserDataRegisterParameter(
			_makeUserId(i -> (byte)(i + 31)),
			"user2@example.com",
			"user 2",
			new HashMap<String, Object>(){ { put("attr1", "user2"); } },
			false
		),
		new UserDataRegisterParameter(
			_makeUserId(i -> (byte)(i + 32)),
			"user3@example.com",
			"user 3",
			new HashMap<String, Object>(){ { put("attr1", "user3"); } },
			false
		),
		new UserDataRegisterParameter(
			_makeUserId(i -> (byte)(i + 33)),
			"user4@example.com",
			"user 4",
			new HashMap<String, Object>(){ { put("attr1", "user4"); } },
			true
		),
	};
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		YubiOnFssSdkTestConfig.init();
	}
	
	@BeforeEach
	public void setUp() throws IOException {
		var sdk = _sdk();
		var users = sdk.getAllUsers(true);
		for(var user : users.getUsers()) {
			sdk.deleteUser(user.getUserId());
		}
		for(var user : testUsers) {
			sdk.registerUser(user);
		}
	}
	@AfterEach
	public void tearDown() throws IOException {
	}
	@Test
	public void register() throws NoSuchAlgorithmException, IOException, InvalidAlgorithmParameterException {
		{
			var sdk = _sdk();
			var authenticator = new AuthenticatorEmulator(sdk.getRpId());
			var resp = sdk.startRegisterCredential(new Fido2StartRegisterParameter(
				new Fido2CreationOptionsBase(){
					{
						setAttestation(AttestationConveyancePreference.NONE);
						setAuthenticatorSelection(new AuthenticatorSelectionCriteria()
						{
							{
								setUserVerification(UserVerificationRequirement.REQUIRED);
								setAttestation(AttestationConveyancePreference.NONE);
								setResidentKey(ResidentKeyRequirement.PREFERRED);
							}
						});
						
					}
				}, 
				new UserDataRegisterParameter(testUsers[0])
			));
			assertNotNull(resp);
			var startRegisterResp = resp.getData();
			assertNotNull(startRegisterResp);
			assertNotNull(startRegisterResp.getCreationOptions());
			var clientResp = authenticator.makeCred("https://"+sdk.getRpId(), startRegisterResp.getCreationOptions());
			
			var resp2 = sdk.finishRegisterCredential(new Fido2FinishRegisterParameter(){
				{
					setCreateResponse(new CreateResponse(){
						{
							setAttestationResponse(clientResp);
							setTransports(new String[]{ "usb" });
						}
					});
				}
			}, resp.getSessionCookie());
		}
	}
	@Test
	public void authenticate() throws NoSuchAlgorithmException, IOException, InvalidAlgorithmParameterException, InvalidKeyException, SignatureException {
		var sdk = _sdk();
		var authenticator = new AuthenticatorEmulator(sdk.getRpId());
		{
			var resp = sdk.startRegisterCredential(new Fido2StartRegisterParameter(
				new Fido2CreationOptionsBase(){
					{
						setAttestation(AttestationConveyancePreference.NONE);
						setAuthenticatorSelection(new AuthenticatorSelectionCriteria()
						{
							{
								setUserVerification(UserVerificationRequirement.REQUIRED);
								setAttestation(AttestationConveyancePreference.NONE);
								setResidentKey(ResidentKeyRequirement.PREFERRED);
							}
						});
						
					}
				}, 
				new UserDataRegisterParameter(testUsers[0])
			));
			assertNotNull(resp);
			var startRegisterResp = resp.getData();
			assertNotNull(startRegisterResp);
			assertNotNull(startRegisterResp.getCreationOptions());
			var clientResp = authenticator.makeCred("https://"+sdk.getRpId(), startRegisterResp.getCreationOptions());
			
			var resp2 = sdk.finishRegisterCredential(new Fido2FinishRegisterParameter(){
				{
					setCreateResponse(new CreateResponse(){
						{
							setAttestationResponse(clientResp);
							setTransports(new String[]{ "usb" });
						}
					});
				}
			}, resp.getSessionCookie());
			assertNotNull(resp2);
		}
		{
			var reqOpt = new Fido2RequestOptionsBase();
			reqOpt.setUserVerification(UserVerificationRequirement.REQUIRED);
			var prm = new Fido2StartAuthenticateParameter(reqOpt);
			prm.setUserId(testUsers[0].getUserId());
			var resp = sdk.startAuthenticate(prm);
			assertNotNull(resp);
			var startRegisterResp = resp.getData();
			assertNotNull(startRegisterResp);
			assertNotNull(startRegisterResp.getRequestOptions());
			var clientResp = authenticator.getAssr("https://"+sdk.getRpId(), resp.getData().getRequestOptions());
			
			var resp2 = sdk.finishAuthenticate(new Fido2FinishAuthenticateParameter(){{
				setRequestResponse(new RequestResponse(){{
					setAttestationResponse(clientResp);
				}});
			}}, resp.getSessionCookie());
			assertNotNull(resp2);
			
		}
	}
}