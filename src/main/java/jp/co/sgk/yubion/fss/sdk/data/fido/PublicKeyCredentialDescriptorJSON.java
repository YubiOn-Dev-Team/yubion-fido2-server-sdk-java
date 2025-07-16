package jp.co.sgk.yubion.fss.sdk.data.fido;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PublicKeyCredentialDescriptorJSON {
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("transports")
	private String[] transports;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String[] getTransports() {
		return transports;
	}
	public void setTransports(String[] transports) {
		this.transports = transports;
	}
}
