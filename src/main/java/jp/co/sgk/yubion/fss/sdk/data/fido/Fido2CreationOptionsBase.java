package jp.co.sgk.yubion.fss.sdk.data.fido;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 * Base creation options for the registration process.
 */
public class Fido2CreationOptionsBase {

	@JsonProperty("authenticatorSelection")
	private AuthenticatorSelectionCriteria authenticatorSelection;

	@JsonProperty("timeout")
	private Integer timeout;

	@JsonProperty("hints")
	private List<String> hints;

	@JsonProperty("attestation")
	private AttestationConveyancePreference attestation;

	@JsonProperty("extensions")
	private Map<String, Object> extensions;

	// Getters and Setters

	public AuthenticatorSelectionCriteria getAuthenticatorSelection() {
		return authenticatorSelection;
	}

	public void setAuthenticatorSelection(AuthenticatorSelectionCriteria authenticatorSelection) {
		this.authenticatorSelection = authenticatorSelection;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public List<String> getHints() {
		return hints;
	}

	public void setHints(List<String> hints) {
		this.hints = hints;
	}

	public AttestationConveyancePreference getAttestation() {
		return attestation;
	}

	public void setAttestation(AttestationConveyancePreference attestation) {
		this.attestation = attestation;
	}

	public Map<String, Object> getExtensions() {
		return extensions;
	}

	public void setExtensions(Map<String, Object> extensions) {
		this.extensions = extensions;
	}
}
