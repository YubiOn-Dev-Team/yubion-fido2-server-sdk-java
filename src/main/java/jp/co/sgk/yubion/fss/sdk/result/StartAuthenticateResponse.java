package jp.co.sgk.yubion.fss.sdk.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.sgk.yubion.fss.sdk.data.fido.PublicKeyCredentialRequestOptionsJSON;
import jp.co.sgk.yubion.fss.sdk.data.user.UserDataWithCredentialCount;

/**
 * The response from the startAuthenticate API call.
 */
public class StartAuthenticateResponse {

	@JsonProperty("requestOptions")
	private PublicKeyCredentialRequestOptionsJSON requestOptions;
	
	@JsonProperty("user")
	private UserDataWithCredentialCount user;

	public PublicKeyCredentialRequestOptionsJSON getRequestOptions() {
		return requestOptions;
	}

	public void setRequestOptions(PublicKeyCredentialRequestOptionsJSON requestOptions) {
		this.requestOptions = requestOptions;
	}

	public UserDataWithCredentialCount getUser() {
		return user;
	}

	public void setUser(UserDataWithCredentialCount user) {
		this.user = user;
	}
}
