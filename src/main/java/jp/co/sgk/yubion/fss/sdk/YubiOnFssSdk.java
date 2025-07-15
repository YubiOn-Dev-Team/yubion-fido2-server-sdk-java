package jp.co.sgk.yubion.fss.sdk;

import jp.co.sgk.yubion.fss.sdk.config.FssSdkConfig;
import jp.co.sgk.yubion.fss.sdk.data.credential.CredentialDataUpdateParameter;
import jp.co.sgk.yubion.fss.sdk.data.fido.Fido2FinishAuthenticateParameter;
import jp.co.sgk.yubion.fss.sdk.data.fido.Fido2FinishRegisterParameter;
import jp.co.sgk.yubion.fss.sdk.data.fido.Fido2StartAuthenticateParameter;
import jp.co.sgk.yubion.fss.sdk.data.fido.Fido2StartRegisterParameter;
import jp.co.sgk.yubion.fss.sdk.data.user.UserDataRegisterParameter;
import jp.co.sgk.yubion.fss.sdk.data.user.UserDataUpdateParameter;
import jp.co.sgk.yubion.fss.sdk.internal.FssApiCookieResponse;
import jp.co.sgk.yubion.fss.sdk.internal.FssApiRequester;
import jp.co.sgk.yubion.fss.sdk.internal.FssApiRequesterFactory;
import jp.co.sgk.yubion.fss.sdk.result.*;

import java.security.Security;
import java.util.Map;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Main class for interacting with the YubiOn FIDO2 Server Service API.
 */
public class YubiOnFssSdk {
	
	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	private final FssApiRequester requester;
	private final FssSdkConfig config;

	/**
	 * Initializes a new instance of the SDK.
	 * @param config The SDK configuration.
	 */
	public YubiOnFssSdk(FssSdkConfig config) {
		this(config, false, false);
	}
	public YubiOnFssSdk(FssSdkConfig config, boolean denyUnknownResponseMember, boolean allowBadSslForDebug) {
		this.config = config;
		this.requester = FssApiRequesterFactory.create(config, denyUnknownResponseMember, allowBadSslForDebug);
	}
	public String getRpId(){
		return this.config.getRpId();
	}

	/* --- FIDO Ceremony Flow --- */

	public ApiSessionResponse<StartRegisterCredentialResponse> startRegisterCredential(Fido2StartRegisterParameter parameter)
			throws FssApiException {
		FssApiCookieResponse<StartRegisterCredentialResponse> cookieResponse = this.requester.requestWithCookieResponse(
				"registerCredential/start", parameter, StartRegisterCredentialResponse.class, true, null);
		return new ApiSessionResponse<>(cookieResponse.getResponse(), cookieResponse.getCookie());
	}

	public FinishRegisterCredentialResponse verifyRegisterCredential(
			Fido2FinishRegisterParameter parameter, String sessionCookie)
			throws FssApiException {
		return this.requester.requestWithCookieResponse(
				"registerCredential/verify", parameter, FinishRegisterCredentialResponse.class, true, sessionCookie).getResponse();
	}

	public FinishRegisterCredentialResponse finishRegisterCredential(
			Fido2FinishRegisterParameter parameter, String sessionCookie)
			throws FssApiException {
		return this.requester.requestWithCookieResponse(
				"registerCredential/finish", parameter, FinishRegisterCredentialResponse.class, true, sessionCookie).getResponse();
	}

	public ApiSessionResponse<StartAuthenticateResponse> startAuthenticate(Fido2StartAuthenticateParameter parameter)
			throws FssApiException {
		FssApiCookieResponse<StartAuthenticateResponse> cookieResponse = this.requester.requestWithCookieResponse(
				"authenticate/start", parameter, StartAuthenticateResponse.class, true, null);
		return new ApiSessionResponse<>(cookieResponse.getResponse(), cookieResponse.getCookie());
	}

	public FinishAuthenticateResponse finishAuthenticate(
			Fido2FinishAuthenticateParameter parameter, String sessionCookie)
			throws FssApiException {
		return this.requester.requestWithCookieResponse(
				"authenticate/finish", parameter, FinishAuthenticateResponse.class, true, sessionCookie).getResponse();
	}

	/* --- User Management --- */

	public GetUsersResponse getAllUsers(boolean withDisabledUser) throws FssApiException {
		return this.requester.request("getAllUsers", Map.of("withDisabledUser", withDisabledUser), GetUsersResponse.class);
	}
	public GetUsersResponse getAllUsers() throws FssApiException {
		return this.getAllUsers(false);
	}
	public GetUsersResponse getUsersByUserName(String userName, boolean withDisabledUser) throws FssApiException {
		return this.requester.request("getUsersByUserName", Map.of("userName", userName, "withDisabledUser", withDisabledUser), GetUsersResponse.class);
	}
	public GetUsersResponse getUsersByUserName(String userName) throws FssApiException {
		return this.getUsersByUserName(userName, false);
	}
	public GetUserResponse getUser(String userId, boolean withDisabledUser, boolean withDisabledCredential) throws FssApiException {
		return this.requester.request("getUser", Map.of("userId", userId, "withDisabledUser", withDisabledUser, "withDisabledCredential", withDisabledCredential), GetUserResponse.class);
	}
	public GetUserResponse getUser(String userId, boolean withDisabledUser) throws FssApiException {
		return this.getUser(userId, withDisabledUser, false);
	}
	public GetUserResponse getUser(String userId) throws FssApiException {
		return this.getUser(userId, false);
	}

	public RegisterUserResponse registerUser(UserDataRegisterParameter parameter) throws FssApiException {
		return this.requester.request("registerUser", Map.of("user", parameter), RegisterUserResponse.class);
	}


	public UpdateUserResponse updateUser(UserDataUpdateParameter parameter) throws FssApiException {
		return updateUser(parameter, false);
	}
	public UpdateUserResponse updateUser(UserDataUpdateParameter parameter, boolean withUpdatedCheck) throws FssApiException {
		return this.requester.request("updateUser", Map.of("user", parameter, "options", Map.of("withUpdatedCheck", withUpdatedCheck)), UpdateUserResponse.class);
	}

	public DeleteUserResponse deleteUser(String userId) throws FssApiException {
		return this.requester.request("deleteUser", Map.of("userId", userId), DeleteUserResponse.class);
	}

	/* --- Credential Management --- */

	public GetCredentialResponse getCredential(String userId, String credentialId) throws FssApiException {
		return this.getCredential(userId, credentialId, false);
	}
	public GetCredentialResponse getCredential(String userId, String credentialId, boolean withDisabledUser) throws FssApiException {
		return this.getCredential(userId, credentialId, withDisabledUser, false);
	}
	public GetCredentialResponse getCredential(String userId, String credentialId, boolean withDisabledUser, boolean withDisabledCredential) throws FssApiException {
		return this.requester.request("getCredential", Map.of(
			"userId", userId, 
			"credentialId", credentialId,
			"withDisabledUser", withDisabledUser, 
			"withDisabledCredential", withDisabledCredential), GetCredentialResponse.class);
	}

	public UpdateCredentialResponse updateCredential(CredentialDataUpdateParameter parameter) throws FssApiException {
		return this.updateCredential(parameter, false);
	}
	public UpdateCredentialResponse updateCredential(CredentialDataUpdateParameter parameter, boolean withUpdatedCheck) throws FssApiException {
		return this.requester.request("updateCredential", Map.of(
			"credential", parameter, 
			"options", Map.of(
				"withUpdatedCheck", withUpdatedCheck
			)), UpdateCredentialResponse.class);
	}

	public DeleteCredentialResponse deleteCredential(String userId, String credentialId) throws FssApiException {
		return this.requester.request("deleteCredential", Map.of("userId", userId, "credentialId", credentialId), DeleteCredentialResponse.class);
	}
}
