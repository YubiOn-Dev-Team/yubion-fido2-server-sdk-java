package jp.co.sgk.yubion.fss.sdk.config;

import java.util.Objects;

/**
 * Configuration for the FIDO2 Server SDK.
 */
public class FssSdkConfig {

	private static final String DEFAULT_ENDPOINT = "https://fss.yubion.com/api/";
	private static final String DEFAULT_AGENT = "yubion-fido2-server-sdk-java";

	private final String rpId;
	private final String apiAuthId;
	private final FssApiAuthType apiAuthType;
	private final String secretKey;

	private String endpoint;
	private String agent;

	/**
	 * Constructor with required parameters.
	 *
	 * @param rpId        The Relying Party ID.
	 * @param apiAuthId   The API authentication ID.
	 * @param apiAuthType The API authentication type.
	 * @param secretKey   The secret key for API authentication.
	 */
	public FssSdkConfig(String rpId, String apiAuthId, FssApiAuthType apiAuthType, String secretKey) {
		this.rpId = Objects.requireNonNull(rpId, "rpId must not be null");
		this.apiAuthId = Objects.requireNonNull(apiAuthId, "apiAuthId must not be null");
		this.apiAuthType = Objects.requireNonNull(apiAuthType, "apiAuthType must not be null");
		this.secretKey = Objects.requireNonNull(secretKey, "secretKey must not be null");

		// Set default values
		this.endpoint = DEFAULT_ENDPOINT;
		this.agent = DEFAULT_AGENT;
	}

	// Getters and Setters

	public String getRpId() {
		return rpId;
	}

	public String getApiAuthId() {
		return apiAuthId;
	}

	public FssApiAuthType getApiAuthType() {
		return apiAuthType;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public String getEndpoint() {
		return endpoint;
	}

	/**
	 * Sets the API endpoint URL.
	 * @param endpoint The API endpoint URL.
	 * @return This config instance for chaining.
	 */
	public FssSdkConfig setEndpoint(String endpoint) {
		this.endpoint = endpoint;
		return this;
	}

	public String getAgent() {
		return agent;
	}

	/**
	 * Sets the user agent string for HTTP requests.
	 * @param agent The user agent string.
	 * @return This config instance for chaining.
	 */
	public FssSdkConfig setAgent(String agent) {
		this.agent = agent;
		return this;
	}
}
