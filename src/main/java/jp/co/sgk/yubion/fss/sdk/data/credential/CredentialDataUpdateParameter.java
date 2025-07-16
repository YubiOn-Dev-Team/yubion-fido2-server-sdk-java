package jp.co.sgk.yubion.fss.sdk.data.credential;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Parameters for updating a credential.
 */
public class CredentialDataUpdateParameter {

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("credentialId")
	private String credentialId;

	@JsonProperty("credentialName")
	private String credentialName;

	@JsonProperty("credentialAttributes")
	private Map<String, Object> credentialAttributes;

	@JsonProperty("disabled")
	private boolean disabled;

	@JsonProperty("updated")
	private OffsetDateTime updated;

	// Constructor, Getters and Setters
	public CredentialDataUpdateParameter(){}

	public CredentialDataUpdateParameter(String userId, String credentialId) {
		this.userId = userId;
		this.credentialId = credentialId;
	}
	public CredentialDataUpdateParameter(String userId, String credentialId, String credentialName, Map<String, Object> credentialAttributes, boolean disabled) {
		this.userId = userId;
		this.credentialId = credentialId;
		this.credentialName = credentialName;
		this.credentialAttributes = credentialAttributes;
		this.disabled = disabled;
	}
	public CredentialDataUpdateParameter(String userId, String credentialId, String credentialName, Map<String, Object> credentialAttributes, boolean disabled, OffsetDateTime updated) {
		this.userId = userId;
		this.credentialId = credentialId;
		this.credentialName = credentialName;
		this.credentialAttributes = credentialAttributes;
		this.disabled = disabled;
		this.updated = updated;
	}
	public CredentialDataUpdateParameter(CredentialDataUpdateParameter basePrm) {
		this.userId = basePrm.getUserId();
		this.credentialId = basePrm.getCredentialId();
		this.credentialName = basePrm.getCredentialName();
		this.credentialAttributes = basePrm.getCredentialAttributes();
		this.disabled = basePrm.isDisabled();
		this.updated = basePrm.getUpdated();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(String credentialId) {
		this.credentialId = credentialId;
	}

	public String getCredentialName() {
		return credentialName;
	}

	public void setCredentialName(String credentialName) {
		this.credentialName = credentialName;
	}

	public Map<String, Object> getCredentialAttributes() {
		return credentialAttributes;
	}

	public void setCredentialAttributes(Map<String, Object> credentialAttributes) {
		this.credentialAttributes = credentialAttributes;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public OffsetDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(OffsetDateTime updated) {
		this.updated = updated;
	}
}
