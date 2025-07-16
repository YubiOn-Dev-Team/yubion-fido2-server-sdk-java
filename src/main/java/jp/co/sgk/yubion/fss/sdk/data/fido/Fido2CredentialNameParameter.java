package jp.co.sgk.yubion.fss.sdk.data.fido;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This parameter specifies the credential name.
 */
public class Fido2CredentialNameParameter {

	@JsonProperty("name")
	private String name;

	@JsonProperty("nameIfModelNameExists")
	private String nameIfModelNameExists;

	@JsonProperty("nameIfEnterpriseAttestationExists")
	private String nameIfEnterpriseAttestationExists;

	// Constructors

	public Fido2CredentialNameParameter(String name) {
		this.name = name;
	}

	public Fido2CredentialNameParameter(String name, String nameIfModelNameExists, String nameIfEnterpriseAttestationExists) {
		this.name = name;
		this.nameIfModelNameExists = nameIfModelNameExists;
		this.nameIfEnterpriseAttestationExists = nameIfEnterpriseAttestationExists;
	}

	// Getters and Setters

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameIfModelNameExists() {
		return nameIfModelNameExists;
	}

	public void setNameIfModelNameExists(String nameIfModelNameExists) {
		this.nameIfModelNameExists = nameIfModelNameExists;
	}

	public String getNameIfEnterpriseAttestationExists() {
		return nameIfEnterpriseAttestationExists;
	}

	public void setNameIfEnterpriseAttestationExists(String nameIfEnterpriseAttestationExists) {
		this.nameIfEnterpriseAttestationExists = nameIfEnterpriseAttestationExists;
	}
}
