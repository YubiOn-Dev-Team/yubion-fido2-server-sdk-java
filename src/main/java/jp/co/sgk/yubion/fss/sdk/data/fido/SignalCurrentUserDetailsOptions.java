package jp.co.sgk.yubion.fss.sdk.data.fido;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Options for signaling current user details.
 */
public class SignalCurrentUserDetailsOptions {

	@JsonProperty("rpId")
	private String rpId;

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("name")
	private String name;

	@JsonProperty("displayName")
	private String displayName;
	public SignalCurrentUserDetailsOptions() {
	}

	public SignalCurrentUserDetailsOptions(String rpId, String userId, String name) {
		this.rpId = rpId;
		this.userId = userId;
		this.name = name;
	}

	public String getRpId() {
		return rpId;
	}

	public void setRpId(String rpId) {
		this.rpId = rpId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
