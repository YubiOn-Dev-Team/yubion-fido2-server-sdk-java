package jp.co.sgk.yubion.fss.sdk.test.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;

import jp.co.sgk.yubion.fss.sdk.YubiOnFssSdk;
import jp.co.sgk.yubion.fss.sdk.data.fido.AttestationConveyancePreference;
import jp.co.sgk.yubion.fss.sdk.data.fido.AuthenticatorSelectionCriteria;
import jp.co.sgk.yubion.fss.sdk.data.fido.Fido2CreationOptionsBase;
import jp.co.sgk.yubion.fss.sdk.data.fido.Fido2FinishRegisterParameter;
import jp.co.sgk.yubion.fss.sdk.data.fido.Fido2RegisterOptions;
import jp.co.sgk.yubion.fss.sdk.data.fido.Fido2StartRegisterParameter;
import jp.co.sgk.yubion.fss.sdk.data.fido.ResidentKeyRequirement;
import jp.co.sgk.yubion.fss.sdk.data.fido.UserVerificationRequirement;
import jp.co.sgk.yubion.fss.sdk.data.user.UserDataRegisterParameter;
import jp.co.sgk.yubion.fss.sdk.result.ApiSessionResponse;
import jp.co.sgk.yubion.fss.sdk.result.FinishRegisterCredentialResponse;
import jp.co.sgk.yubion.fss.sdk.result.StartRegisterCredentialResponse;

public class FidoTestUtil {

	public static FinishRegisterCredentialResponse createCredential(YubiOnFssSdk sdk, UserDataRegisterParameter user) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, IOException {
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
			new UserDataRegisterParameter(user),
			new Fido2RegisterOptions(true, false, null, null)
		));
		var startRegisterResp = resp.getData();
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
		return resp2;
	}
}
