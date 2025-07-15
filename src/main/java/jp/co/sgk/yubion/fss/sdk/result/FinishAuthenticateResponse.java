package jp.co.sgk.yubion.fss.sdk.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.sgk.yubion.fss.sdk.data.credential.CredentialData;
import jp.co.sgk.yubion.fss.sdk.data.fido.SignalAllAcceptedCredentialsOptions;
import jp.co.sgk.yubion.fss.sdk.data.fido.SignalCurrentUserDetailsOptions;
import jp.co.sgk.yubion.fss.sdk.data.user.UserDataWithCredentialCount;

/**
 * The response from the finishAuthenticate API call.
 */
public class FinishAuthenticateResponse {

	@JsonProperty("user")
	private UserDataWithCredentialCount user;

	@JsonProperty("credential")
	private CredentialData credential;

	@JsonProperty("signalAllAcceptedCredentialsOptions")
	private SignalAllAcceptedCredentialsOptions signalAllAcceptedCredentialsOptions;
	
	@JsonProperty("signalCurrentUserDetailsOptions")
	private SignalCurrentUserDetailsOptions signalCurrentUserDetailsOptions;


	public UserDataWithCredentialCount getUser() {
		return user;
	}

	public void setUser(UserDataWithCredentialCount user) {
		this.user = user;
	}

	public CredentialData getCredential() {
		return credential;
	}

	public void setCredential(CredentialData credential) {
		this.credential = credential;
	}

	public SignalAllAcceptedCredentialsOptions getSignalAllAcceptedCredentialsOptions() {
		return signalAllAcceptedCredentialsOptions;
	}

	public void setSignalAllAcceptedCredentialsOptions(SignalAllAcceptedCredentialsOptions signalAllAcceptedCredentialsOptions) {
		this.signalAllAcceptedCredentialsOptions = signalAllAcceptedCredentialsOptions;
	}

	public SignalCurrentUserDetailsOptions getSignalCurrentUserDetailsOptions() {
		return signalCurrentUserDetailsOptions;
	}

	public void setSignalCurrentUserDetailsOptions(SignalCurrentUserDetailsOptions signalCurrentUserDetailsOptions) {
		this.signalCurrentUserDetailsOptions = signalCurrentUserDetailsOptions;
	}
}
