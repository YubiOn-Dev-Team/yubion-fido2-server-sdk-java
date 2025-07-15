package jp.co.sgk.yubion.fss.sdk.result;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the sub-status of an API error.
 */
public class FssApiResultSubStatus {

	@JsonProperty("errorCode")
	private Fido2ErrorStatus errorCode;

	@JsonProperty("errorMessage")
	private String errorMessage;

	@JsonProperty("info")
	private Object info;

	// Getters and Setters

	public Fido2ErrorStatus getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Fido2ErrorStatus errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}
}
