package jp.co.sgk.yubion.fss.sdk.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.sgk.yubion.fss.sdk.data.credential.CredentialData;
import jp.co.sgk.yubion.fss.sdk.data.fido.SignalUnknownCredentialOptions;
import jp.co.sgk.yubion.fss.sdk.data.user.UserDataWithCredentialCount;

/**
 * The response from the getCredentials API call.
 */
public class DeleteCredentialResponse {

	@JsonProperty("credential")
	private CredentialData credential;
	@JsonProperty("user")
	private UserDataWithCredentialCount user;
	@JsonProperty("signalUnknownCredentialOptions")
	private SignalUnknownCredentialOptions signalUnknownCredentialOptions;

	public CredentialData getCredential() {
		return credential;
	}

	public void setCredential(CredentialData credential) {
		this.credential = credential;
	}

	public UserDataWithCredentialCount getUser() {
		return user;
	}

	public void setUser(UserDataWithCredentialCount user) {
		this.user = user;
	}

	public SignalUnknownCredentialOptions getSignalUnknownCredentialOptions() {
		return signalUnknownCredentialOptions;
	}

	public void setSignalUnknownCredentialOptions(SignalUnknownCredentialOptions signalUnknownCredentialOptions) {
		this.signalUnknownCredentialOptions = signalUnknownCredentialOptions;
	}
}
