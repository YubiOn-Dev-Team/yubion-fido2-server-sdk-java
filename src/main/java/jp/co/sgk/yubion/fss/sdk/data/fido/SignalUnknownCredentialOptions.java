package jp.co.sgk.yubion.fss.sdk.data.fido;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Options for signaling an unknown credential.
 */
public class SignalUnknownCredentialOptions {

	@JsonProperty("rpId")
	private String rpId;

	@JsonProperty("credentialId")
	private String credentialId;
	public SignalUnknownCredentialOptions() {
	}

	public SignalUnknownCredentialOptions(String rpId, String credentialId) {
		this.rpId = rpId;
		this.credentialId = credentialId;
	}

	public String getRpId() {
		return rpId;
	}

	public void setRpId(String rpId) {
		this.rpId = rpId;
	}

	public String getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(String credentialId) {
		this.credentialId = credentialId;
	}
}
