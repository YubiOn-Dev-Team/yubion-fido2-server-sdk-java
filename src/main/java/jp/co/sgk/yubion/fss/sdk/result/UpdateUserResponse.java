package jp.co.sgk.yubion.fss.sdk.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.sgk.yubion.fss.sdk.data.fido.SignalCurrentUserDetailsOptions;
import jp.co.sgk.yubion.fss.sdk.data.user.UserDataWithCredentialCount;

/**
 * The response from the getUser API call.
 */
public class UpdateUserResponse {

	@JsonProperty("user")
	private UserDataWithCredentialCount user;

	@JsonProperty("signalCurrentUserDetailsOptions")
	private SignalCurrentUserDetailsOptions signalCurrentUserDetailsOptions;

	public UserDataWithCredentialCount getUser() {
		return user;
	}

	public void setUser(UserDataWithCredentialCount user) {
		this.user = user;
	}

	public SignalCurrentUserDetailsOptions getSignalCurrentUserDetailsOptions() {
		return signalCurrentUserDetailsOptions;
	}

	public void setSignalCurrentUserDetailsOptions(SignalCurrentUserDetailsOptions signalCurrentUserDetailsOptions) {
		this.signalCurrentUserDetailsOptions = signalCurrentUserDetailsOptions;
	}
}
