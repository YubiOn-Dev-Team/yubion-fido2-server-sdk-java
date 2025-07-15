package jp.co.sgk.yubion.fss.sdk.data.fido;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents the response from an authenticator for a registration ceremony.
 * @see https://developer.mozilla.org/ja/docs/Web/API/AuthenticatorAttestationResponse
 */
public class AuthenticatorAttestationResponse extends AuthenticatorResponse {

	@JsonProperty("attestationObject")
	private String attestationObject;

	@JsonProperty("transports")
	private List<AuthenticatorTransport> transports;

	// Getters and Setters

	public String getAttestationObject() {
		return attestationObject;
	}

	public void setAttestationObject(String attestationObject) {
		this.attestationObject = attestationObject;
	}

	public List<AuthenticatorTransport> getTransports() {
		return transports;
	}

	public void setTransports(List<AuthenticatorTransport> transports) {
		this.transports = transports;
	}
}
