package jp.co.sgk.yubion.fss.sdk.data.fido;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PublicKeyCredentialRequestOptionsJSON {
	@JsonProperty("allowCredentials")
	private PublicKeyCredentialDescriptorJSON[] allowCredentials;
	@JsonProperty("challenge")
	private String challenge;
	@JsonProperty("extensions")
	private Map<String, Object> extensions;
	@JsonProperty("hints")
	private String[] hints;
	@JsonProperty("rpId")
	private String rpId;
	@JsonProperty("timeout")
	private int timeout;
	@JsonProperty("userVerification")
	private UserVerificationRequirement userVerification;
	
	//getter/setter
	public PublicKeyCredentialDescriptorJSON[] getAllowCredentials() {
		return allowCredentials;
	}
	public void setAllowCredentials(PublicKeyCredentialDescriptorJSON[] allowCredentials) {
		this.allowCredentials = allowCredentials;
	}
	public String getChallenge() {
		return challenge;
	}
	public void setChallenge(String challenge) {
		this.challenge = challenge;
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
	public String getRpId() {
		return rpId;
	}
	public void setRpId(String rpId) {
		this.rpId = rpId;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public UserVerificationRequirement getUserVerification() {
		return userVerification;
	}
	public void setUserVerification(UserVerificationRequirement userVerification) {
		this.userVerification = userVerification;
	}
	
}
