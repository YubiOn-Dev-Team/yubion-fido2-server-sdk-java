package jp.co.sgk.yubion.fss.sdk.internal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the response from the /getNonce API call.
 */
public class GetNonceResponse {
	@JsonProperty("nonce")
	private String nonce;

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
}
