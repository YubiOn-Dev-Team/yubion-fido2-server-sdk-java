package jp.co.sgk.yubion.fss.sdk.data.fido;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PublicKeyCredentialCreationOptionsJSON {
	@JsonProperty("attestation")
	private String attestation;
	
	@JsonProperty("authenticatorSelection")
	private AuthenticatorSelectionCriteria authenticatorSelection;
	
	@JsonProperty("challenge")
	private String challenge;
	
	@JsonProperty("excludeCredentials")
	private PublicKeyCredentialDescriptorJSON[] excludeCredentials;
	
	@JsonProperty("extensions")
	private Map<String, Object> extensions;
	
	@JsonProperty("hints")
	private String[] hints;
	
	@JsonProperty("pubKeyCredParams")
	private PublicKeyCredentialParameters[] pubKeyCredParams;
	
	@JsonProperty("rp")
	private PublicKeyCredentialRpEntity rp;
	
	@JsonProperty("timeout")
	private Integer timeout;
	
	@JsonProperty("user")
	private PublicKeyCredentialUserEntityJSON user;
	
	public String getAttestation() {
		return attestation;
	}
	public void setAttestation(String attestation) {
		this.attestation = attestation;
	}
	public AuthenticatorSelectionCriteria getAuthenticatorSelection() {
		return authenticatorSelection;
	}
	public void setAuthenticatorSelection(AuthenticatorSelectionCriteria authenticatorSelection) {
		this.authenticatorSelection = authenticatorSelection;
	}
	public String getChallenge() {
		return challenge;
	}
	public void setChallenge(String challenge) {
		this.challenge = challenge;
	}
	public PublicKeyCredentialDescriptorJSON[] getExcludeCredentials() {
		return excludeCredentials;
	}
	public void setExcludeCredentials(PublicKeyCredentialDescriptorJSON[] excludeCredentials) {
		this.excludeCredentials = excludeCredentials;
	}
	public Map<String, Object> getExtensions() {
		return extensions;
	}
	public void setExtensions(Map<String, Object> extensions) {
		this.extensions = extensions;
	}
	public String[] getHints() {
		return hints;
	}
	public void setHints(String[] hints) {
		this.hints = hints;
	}
	public PublicKeyCredentialParameters[] getPubKeyCredParams() {
		return pubKeyCredParams;
	}
	public void setPubKeyCredParams(PublicKeyCredentialParameters[] pubKeyCredParams) {
		this.pubKeyCredParams = pubKeyCredParams;
	}
	public PublicKeyCredentialRpEntity getRp() {
		return rp;
	}
	public void setRp(PublicKeyCredentialRpEntity rp) {
		this.rp = rp;
	}
	public Integer getTimeout() {
		return timeout;
	}
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	public PublicKeyCredentialUserEntityJSON getUser() {
		return user;
	}
	public void setUser(PublicKeyCredentialUserEntityJSON user) {
		this.user = user;
	}
}
