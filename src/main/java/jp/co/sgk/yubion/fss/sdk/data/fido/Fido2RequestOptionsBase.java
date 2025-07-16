package jp.co.sgk.yubion.fss.sdk.data.fido;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 * Base options for the authentication request.
 */
public class Fido2RequestOptionsBase {

	@JsonProperty("timeout")
	private Integer timeout;

	@JsonProperty("hints")
	private List<String> hints;

	@JsonProperty("userVerification")
	private UserVerificationRequirement userVerification;

	@JsonProperty("extensions")
	private Map<String, Object> extensions;

	// Getters and Setters

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public List<String> getHints() {
		return hints;
	}

	public void setHints(List<String> hints) {
		this.hints = hints;
	}

	public UserVerificationRequirement getUserVerification() {
		return userVerification;
	}

	public void setUserVerification(UserVerificationRequirement userVerification) {
		this.userVerification = userVerification;
	}

	public Map<String, Object> getExtensions() {
		return extensions;
	}

	public void setExtensions(Map<String, Object> extensions) {
		this.extensions = extensions;
	}
}
