package jp.co.sgk.yubion.fss.sdk.data.credential;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Represents credential data.
 */
public class CredentialData {

	@JsonProperty("rpId")
	private String rpId;

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("credentialId")
	private String credentialId;

	@JsonProperty("credentialName")
	private String credentialName;

	@JsonProperty("credentialAttributes")
	private Map<String, Object> credentialAttributes;

	@JsonProperty("format")
	private String format;

	@JsonProperty("userPresence")
	private boolean userPresence;

	@JsonProperty("userVerification")
	private boolean userVerification;

	@JsonProperty("backupEligibility")
	private boolean backupEligibility;

	@JsonProperty("backupState")
	private boolean backupState;

	@JsonProperty("attestedCredentialData")
	private boolean attestedCredentialData;

	@JsonProperty("extensionData")
	private boolean extensionData;

	@JsonProperty("aaguid")
	private String aaguid;

	@JsonProperty("aaguidModelName")
	private String aaguidModelName;

	@JsonProperty("publicKey")
	private String publicKey;

	@JsonProperty("transportsRaw")
	private String transportsRaw;

	@JsonProperty("transportsBle")
	private Boolean transportsBle;

	@JsonProperty("transportsHybrid")
	private Boolean transportsHybrid;

	@JsonProperty("transportsInternal")
	private Boolean transportsInternal;

	@JsonProperty("transportsNfc")
	private Boolean transportsNfc;

	@JsonProperty("transportsUsb")
	private Boolean transportsUsb;

	@JsonProperty("discoverableCredential")
	private Boolean discoverableCredential;

	@JsonProperty("enterpriseAttestation")
	private boolean enterpriseAttestation;

	@JsonProperty("vendorId")
	private String vendorId;

	@JsonProperty("authenticatorId")
	private String authenticatorId;

	@JsonProperty("attestationObject")
	private String attestationObject;

	@JsonProperty("authenticatorAttachment")
	private String authenticatorAttachment;

	@JsonProperty("credentialType")
	private String credentialType;

	@JsonProperty("clientDataJson")
	private String clientDataJson;

	@JsonProperty("clientDataJsonRaw")
	private String clientDataJsonRaw;

	@JsonProperty("lastAuthenticated")
	private OffsetDateTime lastAuthenticated;

	@JsonProperty("lastSignCounter")
	private Long lastSignCounter;

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

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public boolean isUserPresence() {
		return userPresence;
	}

	public void setUserPresence(boolean userPresence) {
		this.userPresence = userPresence;
	}

	public boolean isUserVerification() {
		return userVerification;
	}

	public void setUserVerification(boolean userVerification) {
		this.userVerification = userVerification;
	}

	public boolean isBackupEligibility() {
		return backupEligibility;
	}

	public void setBackupEligibility(boolean backupEligibility) {
		this.backupEligibility = backupEligibility;
	}

	public boolean isBackupState() {
		return backupState;
	}

	public void setBackupState(boolean backupState) {
		this.backupState = backupState;
	}

	public boolean isAttestedCredentialData() {
		return attestedCredentialData;
	}

	public void setAttestedCredentialData(boolean attestedCredentialData) {
		this.attestedCredentialData = attestedCredentialData;
	}

	public boolean isExtensionData() {
		return extensionData;
	}

	public void setExtensionData(boolean extensionData) {
		this.extensionData = extensionData;
	}

	public String getAaguid() {
		return aaguid;
	}

	public void setAaguid(String aaguid) {
		this.aaguid = aaguid;
	}

	public String getAaguidModelName() {
		return aaguidModelName;
	}

	public void setAaguidModelName(String aaguidModelName) {
		this.aaguidModelName = aaguidModelName;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getTransportsRaw() {
		return transportsRaw;
	}

	public void setTransportsRaw(String transportsRaw) {
		this.transportsRaw = transportsRaw;
	}

	public Boolean getTransportsBle() {
		return transportsBle;
	}

	public void setTransportsBle(Boolean transportsBle) {
		this.transportsBle = transportsBle;
	}

	public Boolean getTransportsHybrid() {
		return transportsHybrid;
	}

	public void setTransportsHybrid(Boolean transportsHybrid) {
		this.transportsHybrid = transportsHybrid;
	}

	public Boolean getTransportsInternal() {
		return transportsInternal;
	}

	public void setTransportsInternal(Boolean transportsInternal) {
		this.transportsInternal = transportsInternal;
	}

	public Boolean getTransportsNfc() {
		return transportsNfc;
	}

	public void setTransportsNfc(Boolean transportsNfc) {
		this.transportsNfc = transportsNfc;
	}

	public Boolean getTransportsUsb() {
		return transportsUsb;
	}

	public void setTransportsUsb(Boolean transportsUsb) {
		this.transportsUsb = transportsUsb;
	}

	public Boolean getDiscoverableCredential() {
		return discoverableCredential;
	}

	public void setDiscoverableCredential(Boolean discoverableCredential) {
		this.discoverableCredential = discoverableCredential;
	}

	public boolean isEnterpriseAttestation() {
		return enterpriseAttestation;
	}

	public void setEnterpriseAttestation(boolean enterpriseAttestation) {
		this.enterpriseAttestation = enterpriseAttestation;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getAuthenticatorId() {
		return authenticatorId;
	}

	public void setAuthenticatorId(String authenticatorId) {
		this.authenticatorId = authenticatorId;
	}

	public String getAttestationObject() {
		return attestationObject;
	}

	public void setAttestationObject(String attestationObject) {
		this.attestationObject = attestationObject;
	}

	public String getAuthenticatorAttachment() {
		return authenticatorAttachment;
	}

	public void setAuthenticatorAttachment(String authenticatorAttachment) {
		this.authenticatorAttachment = authenticatorAttachment;
	}

	public String getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}

	public String getClientDataJson() {
		return clientDataJson;
	}

	public void setClientDataJson(String clientDataJson) {
		this.clientDataJson = clientDataJson;
	}

	public String getClientDataJsonRaw() {
		return clientDataJsonRaw;
	}

	public void setClientDataJsonRaw(String clientDataJsonRaw) {
		this.clientDataJsonRaw = clientDataJsonRaw;
	}

	public OffsetDateTime getLastAuthenticated() {
		return lastAuthenticated;
	}

	public void setLastAuthenticated(OffsetDateTime lastAuthenticated) {
		this.lastAuthenticated = lastAuthenticated;
	}

	public Long getLastSignCounter() {
		return lastSignCounter;
	}

	public void setLastSignCounter(Long lastSignCounter) {
		this.lastSignCounter = lastSignCounter;
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
