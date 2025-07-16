package jp.co.sgk.yubion.fss.sdk.data.fido;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PublicKeyCredentialParameters {
	@JsonProperty("alg")
	private int alg;
	@JsonProperty("type")
	private String type;
	public int getAlg() {
		return alg;
	}
	public void setAlg(int alg) {
		this.alg = alg;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
