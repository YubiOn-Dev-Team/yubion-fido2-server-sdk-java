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
	@Deprecated
	public YubiOnFssSdk(FssSdkConfig config, boolean denyUnknownResponseMember, boolean allowBadSslForDebug) {
		this.config = config;
		this.requester = FssApiRequesterFactory.create(config, denyUnknownResponseMember, allowBadSslForDebug);
	}
	
	/**
	 * Gets the RP ID configured in the SDK configuration.
	 * @return The RP ID.
	 */
	public String getRpId(){
		return this.config.getRpId();
	}

	/* --- FIDO Ceremony Flow --- */

	/**
	 * Starts a fido credential registration.
	 * @param parameter The registration parameter.
	 * @return The started response.
	 */
	public ApiSessionResponse<StartRegisterCredentialResponse> startRegisterCredential(Fido2StartRegisterParameter parameter)
			throws FssApiException {
		FssApiCookieResponse<StartRegisterCredentialResponse> cookieResponse = this.requester.requestWithCookieResponse(
				"registerCredential/start", parameter, StartRegisterCredentialResponse.class, true, null);
		return new ApiSessionResponse<>(cookieResponse.getResponse(), cookieResponse.getCookie());
	}

	/**
	 * Verifies a fido credential registration.
	 * @param parameter The registration parameter.
	 * @param sessionCookie The session cookie from the start method.
	 * @return The verification response.
	 */
	public FinishRegisterCredentialResponse verifyRegisterCredential(
			Fido2FinishRegisterParameter parameter, String sessionCookie)
			throws FssApiException {
		return this.requester.requestWithCookieResponse(
				"registerCredential/verify", parameter, FinishRegisterCredentialResponse.class, true, sessionCookie).getResponse();
	}

	/**
	 * Finishes a fido credential registration.
	 * @param parameter The registration parameter.
	 * @param sessionCookie The session cookie from the start method.
	 * @return The finish response.
	 * @throws FssApiException
	 */
	public FinishRegisterCredentialResponse finishRegisterCredential(
			Fido2FinishRegisterParameter parameter, String sessionCookie)
			throws FssApiException {
		return this.requester.requestWithCookieResponse(
				"registerCredential/finish", parameter, FinishRegisterCredentialResponse.class, true, sessionCookie).getResponse();
	}

	/**
	 * Starts a fido credential authentication.
	 * @param parameter The authentication parameter.
	 * @return The started response.
	 * @throws FssApiException
	 */
	public ApiSessionResponse<StartAuthenticateResponse> startAuthenticate(Fido2StartAuthenticateParameter parameter)
			throws FssApiException {
		FssApiCookieResponse<StartAuthenticateResponse> cookieResponse = this.requester.requestWithCookieResponse(
				"authenticate/start", parameter, StartAuthenticateResponse.class, true, null);
		return new ApiSessionResponse<>(cookieResponse.getResponse(), cookieResponse.getCookie());
	}

	/**
	 * Finishes a fido credential authentication.
	 * @param parameter The authentication parameter.
	 * @param sessionCookie The session cookie from the start method.
	 * @return The finish response.
	 * @throws FssApiException
	 */
	public FinishAuthenticateResponse finishAuthenticate(
			Fido2FinishAuthenticateParameter parameter, String sessionCookie)
			throws FssApiException {
		return this.requester.requestWithCookieResponse(
				"authenticate/finish", parameter, FinishAuthenticateResponse.class, true, sessionCookie).getResponse();
	}

	/* --- User Management --- */

	/**
	 * Gets all users
	 * @param withDisabledUser contains disabled users
	 * @return Users
	 * @throws FssApiException
	 */
	public GetUsersResponse getAllUsers(boolean withDisabledUser) throws FssApiException {
		return this.requester.request("getAllUsers", Map.of("withDisabledUser", withDisabledUser), GetUsersResponse.class);
	}
	/**
	 * Gets all users
	 * @return Users
	 * @throws FssApiException
	 */
	public GetUsersResponse getAllUsers() throws FssApiException {
		return this.getAllUsers(false);
	}
	/**
	 * Gets users by user name
	 * @param userName user name
	 * @param withDisabledUser contains disabled users
	 * @return Users
	 * @throws FssApiException
	 */
	public GetUsersResponse getUsersByUserName(String userName, boolean withDisabledUser) throws FssApiException {
		return this.requester.request("getUsersByUserName", Map.of("userName", userName, "withDisabledUser", withDisabledUser), GetUsersResponse.class);
	}
	/**
	 * Gets users by user name
	 * @param userName user name
	 * @return Users
	 * @throws FssApiException
	 */
	public GetUsersResponse getUsersByUserName(String userName) throws FssApiException {
		return this.getUsersByUserName(userName, false);
	}
	/**
	 * Gets a user
	 * @param userId user id
	 * @param withDisabledUser contains disabled users
	 * @param withDisabledCredential contains disabled credentials
	 * @return User
	 * @throws FssApiException
	 */
	public GetUserResponse getUser(String userId, boolean withDisabledUser, boolean withDisabledCredential) throws FssApiException {
		return this.requester.request("getUser", Map.of("userId", userId, "withDisabledUser", withDisabledUser, "withDisabledCredential", withDisabledCredential), GetUserResponse.class);
	}
	/**
	 * Gets a user
	 * @param userId user id
	 * @param withDisabledUser contains disabled users
	 * @return User
	 * @throws FssApiException
	 */
	public GetUserResponse getUser(String userId, boolean withDisabledUser) throws FssApiException {
		return this.getUser(userId, withDisabledUser, false);
	}
	/**
	 * Gets a user
	 * @param userId user id
	 * @return User
	 * @throws FssApiException
	 */
	public GetUserResponse getUser(String userId) throws FssApiException {
		return this.getUser(userId, false);
	}

	/**
	 * Registers a user
	 * @param parameter The registration parameter
	 * @return The response
	 * @throws FssApiException
	 */
	public RegisterUserResponse registerUser(UserDataRegisterParameter parameter) throws FssApiException {
		return this.requester.request("registerUser", Map.of("user", parameter), RegisterUserResponse.class);
	}

	/**
	 * Updates a user
	 * @param parameter The update parameter
	 * @return The response
	 * @throws FssApiException
	 */
	public UpdateUserResponse updateUser(UserDataUpdateParameter parameter) throws FssApiException {
		return updateUser(parameter, false);
	}
	/**
	 * Updates a user
	 * @param parameter The update parameter
	 * @param withUpdatedCheck If true, the updated check will be performed
	 * @return The response
	 * @throws FssApiException
	 */
	public UpdateUserResponse updateUser(UserDataUpdateParameter parameter, boolean withUpdatedCheck) throws FssApiException {
		return this.requester.request("updateUser", Map.of("user", parameter, "options", Map.of("withUpdatedCheck", withUpdatedCheck)), UpdateUserResponse.class);
	}

	/**
	 * Deletes a user
	 * @param userId user id
	 * @return The response
	 * @throws FssApiException
	 */
	public DeleteUserResponse deleteUser(String userId) throws FssApiException {
		return this.requester.request("deleteUser", Map.of("userId", userId), DeleteUserResponse.class);
	}

	/* --- Credential Management --- */

	/**
	 * Gets a credential
	 * @param userId User ID
	 * @param credentialId Credential ID
	 * @return Credential
	 * @throws FssApiException
	 */
	public GetCredentialResponse getCredential(String userId, String credentialId) throws FssApiException {
		return this.getCredential(userId, credentialId, false);
	}
	/**
	 * Gets a credential
	 * @param userId User ID
	 * @param credentialId Credential ID
	 * @param withDisabledUser contains disabled users
	 * @return Credential
	 * @throws FssApiException
	 */
	public GetCredentialResponse getCredential(String userId, String credentialId, boolean withDisabledUser) throws FssApiException {
		return this.getCredential(userId, credentialId, withDisabledUser, false);
	}
	/**
	 * Gets a credential
	 * @param userId User ID
	 * @param credentialId Credential ID
	 * @param withDisabledUser contains disabled users
	 * @param withDisabledCredential contains disabled credentials
	 * @return Credential
	 * @throws FssApiException
	 */
	public GetCredentialResponse getCredential(String userId, String credentialId, boolean withDisabledUser, boolean withDisabledCredential) throws FssApiException {
		return this.requester.request("getCredential", Map.of(
			"userId", userId, 
			"credentialId", credentialId,
			"withDisabledUser", withDisabledUser, 
			"withDisabledCredential", withDisabledCredential), GetCredentialResponse.class);
	}

	/**
	 * Updates a credential
	 * @param parameter The update parameter
	 * @return The response
	 * @throws FssApiException
	 */
	public UpdateCredentialResponse updateCredential(CredentialDataUpdateParameter parameter) throws FssApiException {
		return this.updateCredential(parameter, false);
	}
	/**
	 * Updates a credential
	 * @param parameter The update parameter
	 * @param withUpdatedCheck If true, the updated check will be performed
	 * @return The response
	 * @throws FssApiException
	 */
	public UpdateCredentialResponse updateCredential(CredentialDataUpdateParameter parameter, boolean withUpdatedCheck) throws FssApiException {
		return this.requester.request("updateCredential", Map.of(
			"credential", parameter, 
			"options", Map.of(
				"withUpdatedCheck", withUpdatedCheck
			)), UpdateCredentialResponse.class);
	}

	/**
	 * Deletes a credential
	 * @param userId user id
	 * @param credentialId credential id
	 * @return The response
	 * @throws FssApiException
	 */
	public DeleteCredentialResponse deleteCredential(String userId, String credentialId) throws FssApiException {
		return this.requester.request("deleteCredential", Map.of("userId", userId, "credentialId", credentialId), DeleteCredentialResponse.class);
	}
}
