package jp.co.sgk.yubion.fss.sdk.data.fido;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a public key credential for authentication.
 */
public class PublicKeyCredentialForAuthentication {

	@JsonProperty("id")
	private String id;

	@JsonProperty("rawId")
	private String rawId;

	@JsonProperty("type")
	private String type;

	@JsonProperty("response")
	private AuthenticatorAssertionResponse response;

	// Getters and Setters

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRawId() {
		return rawId;
	}

	public void setRawId(String rawId) {
		this.rawId = rawId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AuthenticatorAssertionResponse getResponse() {
		return response;
	}

	public void setResponse(AuthenticatorAssertionResponse response) {
		this.response = response;
	}
}
