package jp.co.sgk.yubion.fss.sdk.data.fido;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters for finishing the FIDO2 authentication process.
 */
public class Fido2FinishAuthenticateParameter {

	@JsonProperty("requestResponse")
	private RequestResponse requestResponse;

	// Inner class for nested structure
	public static class RequestResponse {
		@JsonProperty("attestationResponse")
		private PublicKeyCredentialForAuthentication attestationResponse;

		public PublicKeyCredentialForAuthentication getAttestationResponse() {
			return attestationResponse;
		}

		public void setAttestationResponse(PublicKeyCredentialForAuthentication attestationResponse) {
			this.attestationResponse = attestationResponse;
		}
	}

	// Getters and Setters
	public RequestResponse getRequestResponse() {
		return requestResponse;
	}

	public void setRequestResponse(RequestResponse requestResponse) {
		this.requestResponse = requestResponse;
	}
}
