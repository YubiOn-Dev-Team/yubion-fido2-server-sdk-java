package jp.co.sgk.yubion.fss.sdk.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import jp.co.sgk.yubion.fss.sdk.YubiOnFssSdk;
import jp.co.sgk.yubion.fss.sdk.config.FssSdkConfig;
import jp.co.sgk.yubion.fss.sdk.result.FssApiException;
import jp.co.sgk.yubion.fss.sdk.result.FssApiResultStatus;

public class YubiOnFssSdkAuthTest {
	private static final boolean ALLOW_BAD_SSL = YubiOnFssSdkTestConfig.ALLOW_BAD_SSL;
	private static final boolean DENY_UNKNOWN_RESPONSE_MEMBER = YubiOnFssSdkTestConfig.DENY_UNKNOWN_RESPONSE_MEMBER;
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		YubiOnFssSdkTestConfig.init();
	}
	@BeforeEach
	public void setUp() throws IOException {
	}
	@AfterEach
	public void tearDown() throws IOException {
	}
	private FssSdkConfig createNonceConfig(boolean correctKey) {
		return YubiOnFssSdkTestConfig.createNonceConfig(correctKey);
	}

	private FssSdkConfig createDatetimeConfig(boolean correctKey) {
		return YubiOnFssSdkTestConfig.createDatetimeConfig(correctKey);
	}

	private FssSdkConfig createAccesskeyConfig(boolean correctKey) {
		return YubiOnFssSdkTestConfig.createAccesskeyConfig(correctKey);
	}

	@Test
	public void nonceAuthSuccess() {
		{
			var sdk = new YubiOnFssSdk(createNonceConfig(true), DENY_UNKNOWN_RESPONSE_MEMBER, ALLOW_BAD_SSL);
			var result = sdk.getAllUsers();
			assertNotNull(result);
		}
	}

	@Test
	public void nonceAuthFail() {
		try {
			var sdk = new YubiOnFssSdk(createNonceConfig(false), DENY_UNKNOWN_RESPONSE_MEMBER, ALLOW_BAD_SSL);
			sdk.getAllUsers();
			fail();
		} catch(FssApiException e) {
			assertEquals(FssApiResultStatus.AUTHENTICATION_FAILED, e.getAppStatus());
		}
	}

	@Test
	public void datetimeAuthSuccess() {
		{
			var sdk = new YubiOnFssSdk(createDatetimeConfig(true), DENY_UNKNOWN_RESPONSE_MEMBER, ALLOW_BAD_SSL);
			var result = sdk.getAllUsers();
			assertNotNull(result);
		}
	}

	@Test
	public void datetimeAuthFail() {
		try {
			var sdk = new YubiOnFssSdk(createDatetimeConfig(false), DENY_UNKNOWN_RESPONSE_MEMBER, ALLOW_BAD_SSL);
			sdk.getAllUsers();
			fail();
		} catch(FssApiException e) {
			assertEquals(FssApiResultStatus.AUTHENTICATION_FAILED, e.getAppStatus());
		}
	}

	@Test
	public void accesskeyAuthSuccess() {
		{
			var sdk = new YubiOnFssSdk(createAccesskeyConfig(true), DENY_UNKNOWN_RESPONSE_MEMBER, ALLOW_BAD_SSL);
			var result = sdk.getAllUsers();
			assertNotNull(result);
		}
	}

	@Test
	public void accesskeyAuthFail() {
		try {
			var sdk = new YubiOnFssSdk(createAccesskeyConfig(false), DENY_UNKNOWN_RESPONSE_MEMBER, ALLOW_BAD_SSL);
			sdk.getAllUsers();
			fail();
		} catch(FssApiException e) {
			assertEquals(FssApiResultStatus.AUTHENTICATION_FAILED, e.getAppStatus());
		}
	}
}
