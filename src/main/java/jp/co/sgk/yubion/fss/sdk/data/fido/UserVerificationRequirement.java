package jp.co.sgk.yubion.fss.sdk.data.fido;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents the user verification requirement.
 * @see https://www.w3.org/TR/webauthn-3/#dom-publickeycredentialrequestoptions-userverification
 */
public enum UserVerificationRequirement {
	REQUIRED("required"),
	PREFERRED("preferred"),
	DISCOURAGED("discouraged");

	private final String value;

	UserVerificationRequirement(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}
}
