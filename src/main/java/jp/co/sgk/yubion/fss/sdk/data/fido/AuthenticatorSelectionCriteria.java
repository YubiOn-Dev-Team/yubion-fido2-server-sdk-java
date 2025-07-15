package jp.co.sgk.yubion.fss.sdk.data.fido;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the authenticator selection criteria.
 * @see https://www.w3.org/TR/webauthn-3/#dom-publickeycredentialcreationoptions-authenticatorselection
 */
public class AuthenticatorSelectionCriteria {

	@JsonProperty("authenticatorAttachment")
	private AuthenticatorAttachment authenticatorAttachment;

	@JsonProperty("residentKey")
	private ResidentKeyRequirement residentKey;

	@JsonProperty("requireResidentKey")
	private Boolean requireResidentKey;

	@JsonProperty("userVerification")
	private UserVerificationRequirement userVerification;

	// Getters and Setters

	public AuthenticatorAttachment getAuthenticatorAttachment() {
		return authenticatorAttachment;
	}

	public void setAuthenticatorAttachment(AuthenticatorAttachment authenticatorAttachment) {
		this.authenticatorAttachment = authenticatorAttachment;
	}

	public ResidentKeyRequirement getResidentKey() {
		return residentKey;
	}

	public void setResidentKey(ResidentKeyRequirement residentKey) {
		this.residentKey = residentKey;
	}

	public Boolean getRequireResidentKey() {
		return requireResidentKey;
	}

	public void setRequireResidentKey(Boolean requireResidentKey) {
		this.requireResidentKey = requireResidentKey;
	}

	public UserVerificationRequirement getUserVerification() {
		return userVerification;
	}

	public void setUserVerification(UserVerificationRequirement userVerification) {
		this.userVerification = userVerification;
	}
}
