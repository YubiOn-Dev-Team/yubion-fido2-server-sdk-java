package jp.co.sgk.yubion.fss.sdk.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.sgk.yubion.fss.sdk.data.credential.CredentialData;
import jp.co.sgk.yubion.fss.sdk.data.user.UserDataWithCredentialCount;

/**
 * The response from the finishRegisterCredential API call.
 */
public class FinishRegisterCredentialResponse {

	@JsonProperty("credential")
	private CredentialData credential;
	@JsonProperty("user")
	private UserDataWithCredentialCount user;

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

}
