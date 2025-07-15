package jp.co.sgk.yubion.fss.sdk.data.fido;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.sgk.yubion.fss.sdk.data.user.UserDataRegisterParameter;

/**
 * Defines the parameters for initiating a FIDO2 registration process.
 */
public class Fido2StartRegisterParameter {

	@JsonProperty("creationOptionsBase")
	private Fido2CreationOptionsBase creationOptionsBase;

	@JsonProperty("user")
	private UserDataRegisterParameter user;

	@JsonProperty("options")
	private Fido2RegisterOptions options;

	// Constructor
	public Fido2StartRegisterParameter() {}

	public Fido2StartRegisterParameter(Fido2CreationOptionsBase creationOptionsBase) {
		this.creationOptionsBase = creationOptionsBase;
	}
	public Fido2StartRegisterParameter(Fido2CreationOptionsBase creationOptionsBase, UserDataRegisterParameter user) {
		this.creationOptionsBase = creationOptionsBase;
		this.user = user;
	}
	public Fido2StartRegisterParameter(Fido2CreationOptionsBase creationOptionsBase, UserDataRegisterParameter user, Fido2RegisterOptions options) {
		this.creationOptionsBase = creationOptionsBase;
		this.user = user;
		this.options = options;
	}

	// Getters and Setters

	public Fido2CreationOptionsBase getCreationOptionsBase() {
		return creationOptionsBase;
	}

	public void setCreationOptionsBase(Fido2CreationOptionsBase creationOptionsBase) {
		this.creationOptionsBase = creationOptionsBase;
	}

	public UserDataRegisterParameter getUser() {
		return user;
	}

	public void setUser(UserDataRegisterParameter user) {
		this.user = user;
	}

	public Fido2RegisterOptions getOptions() {
		return options;
	}

	public void setOptions(Fido2RegisterOptions options) {
		this.options = options;
	}
}
