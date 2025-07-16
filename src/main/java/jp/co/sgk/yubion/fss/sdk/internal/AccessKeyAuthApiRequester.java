package jp.co.sgk.yubion.fss.sdk.internal;

import jp.co.sgk.yubion.fss.sdk.config.FssSdkConfig;
import java.util.HashMap;
import java.util.Map;

/**
 * API requester for AccessKeyAuth.
 */
public class AccessKeyAuthApiRequester extends FssApiRequesterBase {

	public AccessKeyAuthApiRequester(FssSdkConfig config, boolean denyUnknownResponseMember, boolean allowBadSslForDebug) {
		super(config, denyUnknownResponseMember, allowBadSslForDebug);
	}

	@Override
	protected Map<String, String> getAdditionalHeaders(byte[] body) throws Exception {
		Map<String, String> headers = new HashMap<>();
		headers.put("X-Fss-Rp-Id", config.getRpId());
		headers.put("X-Fss-Api-Auth-Id", config.getApiAuthId());
		headers.put("X-Fss-Auth-Access-Key", config.getSecretKey());
		return headers;
	}
}
