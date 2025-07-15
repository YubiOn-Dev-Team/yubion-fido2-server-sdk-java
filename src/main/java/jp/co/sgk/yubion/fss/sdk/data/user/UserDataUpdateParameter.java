package jp.co.sgk.yubion.fss.sdk.data.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Parameters for updating an existing user.
 */
public class UserDataUpdateParameter extends UserDataRegisterParameter {

	@JsonProperty("updated")
	private OffsetDateTime updated;

	public UserDataUpdateParameter() {
		super();
	}
	public UserDataUpdateParameter(String userId, String userName) {
		super(userId, userName);
	}
	public UserDataUpdateParameter(String userId, String userName, String displayName, Map<String, Object> userAttributes, boolean disabled) {
		super(userId, userName, displayName, userAttributes, disabled);
	}
	public UserDataUpdateParameter(String userId, String userName, String displayName, Map<String, Object> userAttributes, boolean disabled, OffsetDateTime updated) {
		super(userId, userName, displayName, userAttributes, disabled);
		this.updated = updated;
	}
	public UserDataUpdateParameter(UserDataUpdateParameter basePrm) {
		super(basePrm);
		this.updated = basePrm.getUpdated();
	}
	public UserDataUpdateParameter(UserDataRegisterParameter basePrm) {
		super(basePrm);
	}
	public UserDataUpdateParameter(UserData user) {
		super(user);
		this.updated = user.getUpdated();
	}

	public OffsetDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(OffsetDateTime updated) {
		this.updated = updated;
	}
}
