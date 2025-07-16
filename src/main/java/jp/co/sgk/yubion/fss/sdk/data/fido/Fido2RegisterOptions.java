package jp.co.sgk.yubion.fss.sdk.data.fido;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/**
 * Provides additional options for the registration process.
 */
public class Fido2RegisterOptions {

	@JsonProperty("createUserIfNotExists")
	private Boolean createUserIfNotExists;

	@JsonProperty("updateUserIfExists")
	private Boolean updateUserIfExists;

	@JsonProperty("credentialName")
	private Fido2CredentialNameParameter credentialName;

	@JsonProperty("credentialAttributes")
	private Map<String, Object> credentialAttributes;

	public Fido2RegisterOptions() {}

	public Fido2RegisterOptions(Boolean createUserIfNotExists, Boolean updateUserIfExists, Fido2CredentialNameParameter credentialName, Map<String, Object> credentialAttributes) {
		this.createUserIfNotExists = createUserIfNotExists;
		this.updateUserIfExists = updateUserIfExists;
		this.credentialName = credentialName;
		this.credentialAttributes = credentialAttributes;
	}
	
	// Getters and Setters

	public Boolean getCreateUserIfNotExists() {
		return createUserIfNotExists;
	}

	public void setCreateUserIfNotExists(Boolean createUserIfNotExists) {
		this.createUserIfNotExists = createUserIfNotExists;
	}

	public Boolean getUpdateUserIfExists() {
		return updateUserIfExists;
	}

	public void setUpdateUserIfExists(Boolean updateUserIfExists) {
		this.updateUserIfExists = updateUserIfExists;
	}

	public Fido2CredentialNameParameter getCredentialName() {
		return credentialName;
	}

	public void setCredentialName(Fido2CredentialNameParameter credentialName) {
		this.credentialName = credentialName;
	}

	public Map<String, Object> getCredentialAttributes() {
		return credentialAttributes;
	}

	public void setCredentialAttributes(Map<String, Object> credentialAttributes) {
		this.credentialAttributes = credentialAttributes;
	}
}
