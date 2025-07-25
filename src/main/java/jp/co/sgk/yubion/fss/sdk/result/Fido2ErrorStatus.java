package jp.co.sgk.yubion.fss.sdk.result;

/**
 * Represents detailed FIDO2 error statuses.
 */
public enum Fido2ErrorStatus {
	USER_NOT_FOUND,
	REQUIRE_USER_NAME,
	REQUIRE_USER_ID_OR_USER_HANDLE,
	USER_IS_DISABLED,
	USER_HANDLE_NOT_MATCH,
	CREDENTIAL_NOT_FOUND,
	REQUIRE_CREDENTIAL_ID,
	CREDENTIAL_IS_DISABLED,
	CREDENTIAL_ID_MISMATCH,
	BAD_CREDENTIAL_TYPE,
	CREDENTIAL_ALREADY_REGISTERED,
	CLIENT_DATA_JSON_PARSE_FAILED,
	REQUIRE_ATTESTED_CREDENTIAL_DATA,
	BAD_REQUEST_TYPE,
	RP_NOT_FOUND,
	RP_ID_HASH_MISMATCH,
	ORIGIN_NOT_ALLOWED,
	REQUIRE_USER_VERIFICATION,
	LICENSE_LIMIT_EXCEEDED,
	INTERNAL_ERROR,
	UNEXPECTED_ERROR,
	INVALID_SESSION;
}
