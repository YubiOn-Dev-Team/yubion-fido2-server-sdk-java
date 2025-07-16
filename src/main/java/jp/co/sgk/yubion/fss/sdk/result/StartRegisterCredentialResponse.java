package jp.co.sgk.yubion.fss.sdk.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.sgk.yubion.fss.sdk.data.fido.PublicKeyCredentialCreationOptionsJSON;
import jp.co.sgk.yubion.fss.sdk.data.user.UserDataWithCredentialCount;

/**
 * The response from the startRegisterCredential API call.
 */
public class StartRegisterCredentialResponse {

	@JsonProperty("creationOptions")
	private PublicKeyCredentialCreationOptionsJSON creationOptions;
	
	@JsonProperty("user")
	private UserDataWithCredentialCount user;

	public PublicKeyCredentialCreationOptionsJSON getCreationOptions() {
		return creationOptions;
	}

	public void setCreationOptions(PublicKeyCredentialCreationOptionsJSON creationOptions) {
		this.creationOptions = creationOptions;
	}

	public UserDataWithCredentialCount getUser() {
		return user;
	}

	public void setUser(UserDataWithCredentialCount user) {
		this.user = user;
	}
}
