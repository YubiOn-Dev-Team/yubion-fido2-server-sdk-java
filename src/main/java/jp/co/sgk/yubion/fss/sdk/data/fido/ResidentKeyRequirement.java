package jp.co.sgk.yubion.fss.sdk.data.fido;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResidentKeyRequirement {
	ANY(null),
	DISCOURAGED("discouraged"),
	PREFERRED("preferred"),
	REQUIRED("required");
	
	private final String value;
	
	private ResidentKeyRequirement(String value) {
		this.value = value;
	}
	@JsonValue
	public String getValue() {
		return value;
	}

}
