package jp.co.sgk.yubion.fss.sdk.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jp.co.sgk.yubion.fss.sdk.YubiOnFssSdk;
import jp.co.sgk.yubion.fss.sdk.config.FssApiAuthType;
import jp.co.sgk.yubion.fss.sdk.config.FssSdkConfig;
import jp.co.sgk.yubion.fss.sdk.data.fido.Fido2CreationOptionsBase;
import jp.co.sgk.yubion.fss.sdk.data.fido.Fido2StartRegisterParameter;
import jp.co.sgk.yubion.fss.sdk.data.user.UserDataRegisterParameter;
import jp.co.sgk.yubion.fss.sdk.result.FssApiException;
import jp.co.sgk.yubion.fss.sdk.result.FssApiResultStatus;
import jp.co.sgk.yubion.fss.sdk.test.util.FidoTestUtil;

public class YubiOnFssSdkLicenseTest {

	private static final boolean ALLOW_BAD_SSL = YubiOnFssSdkTestConfig.ALLOW_BAD_SSL;
	private static final boolean DENY_UNKNOWN_RESPONSE_MEMBER = YubiOnFssSdkTestConfig.DENY_UNKNOWN_RESPONSE_MEMBER;

	private static String _makeUserId(IntFunction<Byte> func) {
		return _makeUserId(func, 64);
	}

	private static String _makeUserId(IntFunction<Byte> func, int length) {
		var buf = new byte[length];
		for (int i = 0; i < length; i++) {
			buf[i] = func.apply(i);
		}
		return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
	}

	private static final UserDataRegisterParameter[] testUsers = IntStream.range(0, 20)
			.mapToObj(i -> new UserDataRegisterParameter(
					_makeUserId(j -> (byte) ((j * 7 + i) % 256)),
					String.format("user%d@example.com", i),
					String.format("user %d", i),
					new HashMap<String, Object>() {
						{
							put("attr1", String.format("user%d", i));
						}
					},
					false))
			.toArray(UserDataRegisterParameter[]::new);

	private YubiOnFssSdk _sdk() {
		FssSdkConfig config = new FssSdkConfig(
				YubiOnFssSdkTestConfig.RP_ID_FOR_LICENSE_TEST,
				YubiOnFssSdkTestConfig.API_AUTH_ID_FOR_LICENSE_TEST,
				FssApiAuthType.NONCE_SIGN_AUTH,
				YubiOnFssSdkTestConfig.API_SECRET_KEY_FOR_LICENSE_TEST);
		config.setEndpoint(YubiOnFssSdkTestConfig.ENDPOINT);
		return new YubiOnFssSdk(config, DENY_UNKNOWN_RESPONSE_MEMBER, ALLOW_BAD_SSL);
	}

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		YubiOnFssSdkTestConfig.init();
	}
	
	@BeforeEach
	public void setUp() throws IOException {
		var sdk = _sdk();
		var users = sdk.getAllUsers(true);
		for (var user : users.getUsers()) {
			sdk.deleteUser(user.getUserId());
		}
	}

	@Test
	public void registerUserLimitation() {
		var sdk = _sdk();
		// Up to 10 users can be registered.
		for (int i = 0; i < 10; ++i) {
			var user = sdk.registerUser(testUsers[i]);
			assertNotNull(user);
		}

		// Over 10 users cannot be registered.
		try {
			sdk.registerUser(testUsers[10]);
			fail();
		} catch (FssApiException e) {
			assertEquals(FssApiResultStatus.LICENSE_ERROR, e.getAppStatus());
		}
	}

	@Test
	public void registerCredentialWithUserLimitation() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, IOException {
		var sdk = _sdk();
		// Up to 10 users can be registered.
		for (int i = 0; i < 10; ++i) {
			var finishResponse = FidoTestUtil.createCredential(sdk, testUsers[i]);
			assertNotNull(finishResponse);
		}

		// Over 10 users cannot be registered.
		try {
			FidoTestUtil.createCredential(sdk, testUsers[10]);
			fail();
		} catch (FssApiException e) {
			assertEquals(FssApiResultStatus.LICENSE_ERROR, e.getAppStatus());
		}
	}
}