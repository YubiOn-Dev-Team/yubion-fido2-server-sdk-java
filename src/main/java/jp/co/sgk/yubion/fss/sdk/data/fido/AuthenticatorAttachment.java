package jp.co.sgk.yubion.fss.sdk.data.fido;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AuthenticatorAttachment {
	ANY(null),
	PLATFORM("platform"),
	CROSS_PLATFORM("cross-platform");
	
	private final String value;
	
	private AuthenticatorAttachment(String value) {
		this.value = value;
	}
	@JsonValue
	public String getValue() {
		return value;
	}
}