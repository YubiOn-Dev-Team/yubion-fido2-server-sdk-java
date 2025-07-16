package jp.co.sgk.yubion.fss.sdk.data.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * Parameters for registering a new user.
 */
public class UserDataRegisterParameter {

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

	// Constructor, Getters and Setters

	public UserDataRegisterParameter(){
		
	}
	public UserDataRegisterParameter(String userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}
	public UserDataRegisterParameter(String userId, String userName, String displayName, Map<String, Object> userAttributes, boolean disabled) {
		this(userId, userName);
		this.displayName = displayName;
		this.userAttributes = userAttributes;
		this.disabled = disabled;
	}
	public UserDataRegisterParameter(UserDataRegisterParameter basePrm) {
		this.userId = basePrm.getUserId();
		this.userName = basePrm.getUserName();
		this.displayName = basePrm.getDisplayName();
		this.userAttributes = basePrm.getUserAttributes() != null ? new HashMap<>(basePrm.getUserAttributes()) : null;
		this.disabled = basePrm.isDisabled();
	}
	public UserDataRegisterParameter(UserData baseData) {
		this.userId = baseData.getUserId();
		this.userName = baseData.getUserName();
		this.displayName = baseData.getDisplayName();
		this.userAttributes = baseData.getUserAttributes() != null ? new HashMap<>(baseData.getUserAttributes()) : null;
		this.disabled = baseData.isDisabled();
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
}
