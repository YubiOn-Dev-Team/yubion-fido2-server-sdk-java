package jp.co.sgk.yubion.fss.sdk.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.sgk.yubion.fss.sdk.data.user.UserDataWithCredentialCount;

/**
 * The response from the getUser API call.
 */
public class RegisterUserResponse {

	@JsonProperty("user")
	private UserDataWithCredentialCount user;

	public UserDataWithCredentialCount getUser() {
		return user;
	}

	public void setUser(UserDataWithCredentialCount user) {
		this.user = user;
	}

}
