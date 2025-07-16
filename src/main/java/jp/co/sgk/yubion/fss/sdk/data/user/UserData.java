package jp.co.sgk.yubion.fss.sdk.data.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Represents user data retrieved from the FIDO2 server.
 */
public class UserData {

	@JsonProperty("rpId")
	private String rpId;

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("userName")
	private String userName;

	@JsonProperty("displayName")
	private String displayName;

	@JsonProperty("userAttributes")
	private Map<String, Object> userAttributes;

	@JsonProperty("disabled")
	private boolean disabled;

	@JsonProperty("registered")
	private OffsetDateTime registered;

	@JsonProperty("updated")
	private OffsetDateTime updated;

	// Getters and Setters

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Map<String, Object> getUserAttributes() {
		return userAttributes;
	}

	public void setUserAttributes(Map<String, Object> userAttributes) {
		this.userAttributes = userAttributes;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public OffsetDateTime getRegistered() {
		return registered;
	}

	public void setRegistered(OffsetDateTime registered) {
		this.registered = registered;
	}

	public OffsetDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(OffsetDateTime updated) {
		this.updated = updated;
	}
}
