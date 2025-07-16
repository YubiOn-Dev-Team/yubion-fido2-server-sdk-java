package jp.co.sgk.yubion.fss.sdk.data.user;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents user data with credential counts.
 */
public class UserDataWithCredentialCount extends UserData {

	@JsonProperty("enabledCredentialCount")
	private int enabledCredentialCount;

	@JsonProperty("credentialCount")
	private int credentialCount;

	// Getters and Setters

	public int getEnabledCredentialCount() {
		return enabledCredentialCount;
	}

	public void setEnabledCredentialCount(int enabledCredentialCount) {
		this.enabledCredentialCount = enabledCredentialCount;
	}

	public int getCredentialCount() {
		return credentialCount;
	}

	public void setCredentialCount(int credentialCount) {
		this.credentialCount = credentialCount;
	}
}
