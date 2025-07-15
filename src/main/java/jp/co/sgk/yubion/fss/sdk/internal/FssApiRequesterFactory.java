package jp.co.sgk.yubion.fss.sdk.internal;

import jp.co.sgk.yubion.fss.sdk.config.FssSdkConfig;

/**
 * Factory for creating FssApiRequester instances.
 */
public class FssApiRequesterFactory {

	public static FssApiRequester create(FssSdkConfig config) {
		return create(config, false, false);
	}
	public static FssApiRequester create(FssSdkConfig config, boolean denyUnknownResponseMember, boolean allowBadSslForDebug) {
		switch (config.getApiAuthType()) {
			case NonceSignAuth:
				return new NonceSignAuthApiRequester(config, denyUnknownResponseMember, allowBadSslForDebug);
			case DatetimeSignAuth:
				return new DatetimeSignAuthApiRequester(config, denyUnknownResponseMember, allowBadSslForDebug);
			case AccessKeyAuth:
				return new AccessKeyAuthApiRequester(config, denyUnknownResponseMember, allowBadSslForDebug);
			default:
				// This should not happen if FssApiAuthType is an enum
				throw new IllegalArgumentException("Unsupported API authentication type: " + config.getApiAuthType());
		}
	}
}
