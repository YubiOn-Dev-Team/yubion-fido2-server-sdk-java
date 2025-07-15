package jp.co.sgk.yubion.fss.sdk.result;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Custom exception for API errors.
 */
public class FssApiException extends RuntimeException {

	private final FssApiResultStatus appStatus;
	private final JsonNode appSubStatus;

	public FssApiException(String message, FssApiResultStatus appStatus, JsonNode appSubStatus) {
		super(message);
		this.appStatus = appStatus;
		this.appSubStatus = appSubStatus;
	}

	public FssApiException(String message, FssApiResultStatus appStatus, JsonNode appSubStatus, Throwable cause) {
		super(message, cause);
		this.appStatus = appStatus;
		this.appSubStatus = appSubStatus;
	}

	public FssApiResultStatus getAppStatus() {
		return appStatus;
	}

	public JsonNode getAppSubStatus() {
		return appSubStatus;
	}
}
