package jp.co.sgk.yubion.fss.sdk.result;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The response from the checkLicense API call.
 */
public class CheckLicenseResponse {

	@JsonProperty("valid")
	private boolean valid;

	@JsonProperty("message")
	private String message;

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
