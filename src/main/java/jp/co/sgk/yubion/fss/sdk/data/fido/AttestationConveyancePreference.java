package jp.co.sgk.yubion.fss.sdk.data.fido;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents the attestation conveyance preference.
 * @see https://www.w3.org/TR/webauthn-3/#dom-publickeycredentialcreationoptions-attestation
 */
public enum AttestationConveyancePreference {
	NONE("none"),
	INDIRECT("indirect"),
	DIRECT("direct"),
	ENTERPRISE("enterprise");

	private final String value;

	AttestationConveyancePreference(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}
}
