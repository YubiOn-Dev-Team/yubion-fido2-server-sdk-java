package jp.co.sgk.yubion.fss.sdk.internal;

import jp.co.sgk.yubion.fss.sdk.config.FssSdkConfig;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * API requester for NonceSignAuth.
 */
public class NonceSignAuthApiRequester extends SignAuthApiRequesterBase {

	public NonceSignAuthApiRequester(FssSdkConfig config, boolean denyUnknownResponseMember, boolean allowBadSslForDebug) {
		super(config, denyUnknownResponseMember, allowBadSslForDebug);
	}

	@Override
	protected SignInfo getSignInfo(byte[] body) throws Exception {
		// Call getNonce API without authentication headers
		GetNonceResponse nonceResponse = this.requestWithCookieResponse(
				"getNonce",
				Map.of(), // Empty parameter
				GetNonceResponse.class,
				false, // withAuth = false
				null // No cookie
		).getResponse();

		String nonce = nonceResponse.getNonce();
		byte[] signTargetPrefix = nonce.getBytes(StandardCharsets.UTF_8);
		Map<String, String> additionalHeaders = new HashMap<>();
		additionalHeaders.put("X-Fss-Auth-Nonce", nonce);

		return new SignInfo(signTargetPrefix, additionalHeaders);
	}
}
