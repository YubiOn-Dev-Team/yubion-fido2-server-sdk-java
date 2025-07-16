package jp.co.sgk.yubion.fss.sdk.internal;

import jp.co.sgk.yubion.fss.sdk.config.FssSdkConfig;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * API requester for DatetimeSignAuth.
 */
public class DatetimeSignAuthApiRequester extends SignAuthApiRequesterBase {

	public DatetimeSignAuthApiRequester(FssSdkConfig config, boolean denyUnknownResponseMember, boolean allowBadSslForDebug) {
		super(config, denyUnknownResponseMember, allowBadSslForDebug);
	}

	@Override
	protected SignInfo getSignInfo(byte[] body) throws Exception {
		String now = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		byte[] signTargetPrefix = now.getBytes(StandardCharsets.UTF_8);
		Map<String, String> additionalHeaders = new HashMap<>();
		additionalHeaders.put("X-Fss-Auth-Request-Time", now);
		return new SignInfo(signTargetPrefix, additionalHeaders);
	}
}
