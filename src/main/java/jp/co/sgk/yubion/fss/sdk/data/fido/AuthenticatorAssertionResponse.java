package jp.co.sgk.yubion.fss.sdk.data.fido;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the response from an authenticator for an authentication ceremony.
 * @see https://developer.mozilla.org/ja/docs/Web/API/AuthenticatorAssertionResponse
 */
public class AuthenticatorAssertionResponse extends AuthenticatorResponse {

	@JsonProperty("authenticatorData")
	private String authenticatorData;

	@JsonProperty("signature")
	private String signature;

	@JsonProperty("userHandle")
	private String userHandle;

	// Getters and Setters

	public String getAuthenticatorData() {
		return authenticatorData;
	}

	public void setAuthenticatorData(String authenticatorData) {
		this.authenticatorData = authenticatorData;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getUserHandle() {
		return userHandle;
	}

	public void setUserHandle(String userHandle) {
		this.userHandle = userHandle;
	}
}
