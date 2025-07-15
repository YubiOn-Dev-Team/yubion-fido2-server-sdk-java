package jp.co.sgk.yubion.fss.sdk.data.fido;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Base class for authenticator responses.
 */
public class AuthenticatorResponse {

	@JsonProperty("clientDataJSON")
	private String clientDataJSON;

	// Getters and Setters

	public String getClientDataJSON() {
		return clientDataJSON;
	}

	public void setClientDataJSON(String clientDataJSON) {
		this.clientDataJSON = clientDataJSON;
	}
}
