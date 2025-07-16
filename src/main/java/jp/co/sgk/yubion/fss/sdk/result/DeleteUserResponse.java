package jp.co.sgk.yubion.fss.sdk.result;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.sgk.yubion.fss.sdk.data.credential.CredentialData;
import jp.co.sgk.yubion.fss.sdk.data.fido.SignalAllAcceptedCredentialsOptions;
import jp.co.sgk.yubion.fss.sdk.data.user.UserDataWithCredentialCount;

/**
 * The response from the getUser API call.
 */
public class DeleteUserResponse {

	@JsonProperty("user")
	private UserDataWithCredentialCount user;
	@JsonProperty("credentials")
	private List<CredentialData> credentials;
	@JsonProperty("signalAllAcceptedCredentialsOptions")
	private SignalAllAcceptedCredentialsOptions signalAllAcceptedCredentialsOptions;

	public UserDataWithCredentialCount getUser() {
		return user;
	}

	public void setUser(UserDataWithCredentialCount user) {
		this.user = user;
	}

	public List<CredentialData> getCredentials() {
		return credentials;
	}

	public void setCredentials(List<CredentialData> credentials) {
		this.credentials = credentials;
	}

	public SignalAllAcceptedCredentialsOptions getSignalAllAcceptedCredentialsOptions() {
		return signalAllAcceptedCredentialsOptions;
	}

	public void setSignalAllAcceptedCredentialsOptions(SignalAllAcceptedCredentialsOptions signalAllAcceptedCredentialsOptions) {
		this.signalAllAcceptedCredentialsOptions = signalAllAcceptedCredentialsOptions;
	}
}
