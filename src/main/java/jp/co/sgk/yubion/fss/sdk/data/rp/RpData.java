package jp.co.sgk.yubion.fss.sdk.data.rp;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents Relying Party (RP) data.
 */
public class RpData {

	@JsonProperty("rpId")
	private String rpId;

	@JsonProperty("rpName")
	private String rpName;

	// Getters and Setters

	public String getRpId() {
		return rpId;
	}

	public void setRpId(String rpId) {
		this.rpId = rpId;
	}

	public String getRpName() {
		return rpName;
	}

	public void setRpName(String rpName) {
		this.rpName = rpName;
	}
}
