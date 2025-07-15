package jp.co.sgk.yubion.fss.sdk.data.fido;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines the parameters for initiating a FIDO2 authentication process.
 */
public class Fido2StartAuthenticateParameter {

	@JsonProperty("requestOptionsBase")
	private Fido2RequestOptionsBase requestOptionsBase;

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("options")
	private Fido2AuthenticateOptions options;

	// Constructor

	public Fido2StartAuthenticateParameter(Fido2RequestOptionsBase requestOptionsBase) {
		this.requestOptionsBase = requestOptionsBase;
	}

	// Getters and Setters

	public Fido2RequestOptionsBase getRequestOptionsBase() {
		return requestOptionsBase;
	}

	public void setRequestOptionsBase(Fido2RequestOptionsBase requestOptionsBase) {
		this.requestOptionsBase = requestOptionsBase;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Fido2AuthenticateOptions getOptions() {
		return options;
	}

	public void setOptions(Fido2AuthenticateOptions options) {
		this.options = options;
	}
}
