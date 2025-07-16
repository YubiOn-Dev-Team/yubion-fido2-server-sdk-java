package jp.co.sgk.yubion.fss.sdk.data.fido;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for finishing the FIDO2 registration process.
 */
public class Fido2FinishRegisterParameter {

	@JsonProperty("createResponse")
	private CreateResponse createResponse;

	@JsonProperty("options")
	private Options options;

	// Inner classes for nested structure

	public static class CreateResponse {
		@JsonProperty("attestationResponse")
		private PublicKeyCredential attestationResponse;
		@JsonProperty("transports")
		private String[] transports;

		public PublicKeyCredential getAttestationResponse() {
			return attestationResponse;
		}

		public void setAttestationResponse(PublicKeyCredential attestationResponse) {
			this.attestationResponse = attestationResponse;
		}

		public String[] getTransports() {
			return transports;
		}

		public void setTransports(String[] transports) {
			this.transports = transports;
		}
	}

	public static class Options {
		@JsonProperty("credentialName")
		private Fido2CredentialNameParameter credentialName;

		public Fido2CredentialNameParameter getCredentialName() {
			return credentialName;
		}

		public void setCredentialName(Fido2CredentialNameParameter credentialName) {
			this.credentialName = credentialName;
		}
	}

	// Getters and Setters

	public CreateResponse getCreateResponse() {
		return createResponse;
	}

	public void setCreateResponse(CreateResponse createResponse) {
		this.createResponse = createResponse;
	}

	public Options getOptions() {
		return options;
	}

	public void setOptions(Options options) {
		this.options = options;
	}
}
