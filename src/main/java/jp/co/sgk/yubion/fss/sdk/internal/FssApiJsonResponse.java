package jp.co.sgk.yubion.fss.sdk.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import jp.co.sgk.yubion.fss.sdk.result.FssApiResultStatus;

/**
 * Represents a generic JSON response from the FSS API.
 * @param <T> The type of the data payload.
 */
public class FssApiJsonResponse<T> {

	@JsonProperty("appStatus")
	private FssApiResultStatus appStatus;

	@JsonProperty("appSubStatus")
	private JsonNode appSubStatus;

	@JsonProperty("data")
	private T data;
	
	@JsonProperty("message")
	private String message;

	// Getters and Setters

	public FssApiResultStatus getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(FssApiResultStatus appStatus) {
		this.appStatus = appStatus;
	}

	public JsonNode getAppSubStatus() {
		return appSubStatus;
	}

	public void setAppSubStatus(JsonNode appSubStatus) {
		this.appSubStatus = appSubStatus;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
