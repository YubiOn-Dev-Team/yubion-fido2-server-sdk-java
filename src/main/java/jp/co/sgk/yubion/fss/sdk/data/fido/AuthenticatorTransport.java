package jp.co.sgk.yubion.fss.sdk.data.fido;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents the authenticator transport methods.
 * @see https://developer.mozilla.org/en-US/docs/Web/API/AuthenticatorAttestationResponse/getTransports
 */
public enum AuthenticatorTransport {
	USB("usb"),
	NFC("nfc"),
	BLE("ble"),
	HYBRID("hybrid"),
	INTERNAL("internal");

	private final String value;

	AuthenticatorTransport(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}
}
