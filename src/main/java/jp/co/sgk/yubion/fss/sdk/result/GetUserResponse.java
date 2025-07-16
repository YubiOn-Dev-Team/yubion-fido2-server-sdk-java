package jp.co.sgk.yubion.fss.sdk.result;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.sgk.yubion.fss.sdk.data.credential.CredentialData;
import jp.co.sgk.yubion.fss.sdk.data.fido.SignalCurrentUserDetailsOptions;
import jp.co.sgk.yubion.fss.sdk.data.user.UserDataWithCredentialCount;

/**
 * The response from the getUser API call.
 */
public class GetUserResponse {

	@JsonProperty("user")
	private UserDataWithCredentialCount user;
	@JsonProperty("credentials")
	private List<CredentialData> credentials;
	@JsonProperty("signalCurrentUserDetailsOptions")
	private SignalCurrentUserDetailsOptions signalCurrentUserDetailsOptions;

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

	public SignalCurrentUserDetailsOptions getSignalCurrentUserDetailsOptions() {
		return signalCurrentUserDetailsOptions;
	}

	public void setSignalCurrentUserDetailsOptions(SignalCurrentUserDetailsOptions signalCurrentUserDetailsOptions) {
		this.signalCurrentUserDetailsOptions = signalCurrentUserDetailsOptions;
	}
}
