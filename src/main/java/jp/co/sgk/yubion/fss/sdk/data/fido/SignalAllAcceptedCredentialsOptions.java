package jp.co.sgk.yubion.fss.sdk.data.fido;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Options for signaling all accepted credentials.
 */
public class SignalAllAcceptedCredentialsOptions {

	@JsonProperty("rpId")
	private String rpId;

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("allAcceptedCredentialIds")
	private List<String> allAcceptedCredentialIds;
	public SignalAllAcceptedCredentialsOptions() {
	}

	public SignalAllAcceptedCredentialsOptions(String rpId, String userId, List<String> allAcceptedCredentialIds) {
		this.rpId = rpId;
		this.userId = userId;
		this.allAcceptedCredentialIds = allAcceptedCredentialIds;
	}

	public String getRpId() {
		return rpId;
	}

	public void setRpId(String rpId) {
		this.rpId = rpId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getAllAcceptedCredentialIds() {
		return allAcceptedCredentialIds;
	}

	public void setAllAcceptedCredentialIds(List<String> allAcceptedCredentialIds) {
		this.allAcceptedCredentialIds = allAcceptedCredentialIds;
	}
}
