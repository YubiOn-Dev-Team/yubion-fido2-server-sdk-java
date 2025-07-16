package jp.co.sgk.yubion.fss.sdk.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.function.IntFunction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jp.co.sgk.yubion.fss.sdk.YubiOnFssSdk;
import jp.co.sgk.yubion.fss.sdk.data.credential.CredentialDataUpdateParameter;
import jp.co.sgk.yubion.fss.sdk.data.user.UserDataRegisterParameter;
import jp.co.sgk.yubion.fss.sdk.data.user.UserDataUpdateParameter;
import jp.co.sgk.yubion.fss.sdk.result.FssApiException;
import jp.co.sgk.yubion.fss.sdk.result.FssApiResultStatus;
import jp.co.sgk.yubion.fss.sdk.result.GetCredentialResponse;
import jp.co.sgk.yubion.fss.sdk.result.UpdateCredentialResponse;
import jp.co.sgk.yubion.fss.sdk.test.util.FidoTestUtil;

public class YubiOnFssSdkCredentialTest {
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

	private static YubiOnFssSdk _sdk() {
		return new YubiOnFssSdk(YubiOnFssSdkTestConfig.createNonceConfig(true), DENY_UNKNOWN_RESPONSE_MEMBER,
				ALLOW_BAD_SSL);
	}

	private static final UserDataRegisterParameter[] testUsers = new UserDataRegisterParameter[] {
		new UserDataRegisterParameter(
			_makeUserId(i -> (byte) (i + 40)),
			"user1@example.com",
			"user 1",
			new HashMap<String, Object>() {
				{
					put("attr1", "user1");
				}
			},
			false),
		new UserDataRegisterParameter(
			_makeUserId(i -> (byte) (i + 41)),
			"user2@example.com",
			"user 2",
			new HashMap<String, Object>() {
				{
					put("attr1", "user2");
				}
			},
			false)
	};

	private static CredentialDataUpdateParameter[] testCreds = new CredentialDataUpdateParameter[] {
		new CredentialDataUpdateParameter(
			testUsers[0].getUserId(),
			"", // set after test data registered
			"user 1 - credential 1",
			new HashMap<String, Object>() {
				{
					put("cred-attr1", "user1-cred1-attr1");
					put("cred-attr2", "user1-cred1-attr2");
				}
			},
			false),
		new CredentialDataUpdateParameter(
			testUsers[0].getUserId(),
			"", // set after test data registered
			"user 1 - credential 2",
			new HashMap<String, Object>() {
				{
					put("cred-attr1", "user2-cred1-attr1");
					put("cred-attr2", "user2-cred1-attr2");
				}
			},
			false),
		new CredentialDataUpdateParameter(
			testUsers[0].getUserId(),
			"", // set after test data registered
			"user 1 - credential 2",
			new HashMap<String, Object>() {
				{
					put("cred-attr1", "user2-cred1-attr1");
					put("cred-attr2", "user2-cred1-attr2");
				}
			},
			true),
		new CredentialDataUpdateParameter(
			testUsers[1].getUserId(),
			"", // set after test data registered
			"user 2 - credential 1",
			new HashMap<String, Object>() {
				{
					put("cred-attr1", "user2-cred1-attr1");
					put("cred-attr2", "user2-cred1-attr2");
				}
			},
			false)
	};

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		YubiOnFssSdkTestConfig.init();
	}

	@BeforeEach
	public void setUp() throws IOException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		var sdk = _sdk();
		var users = sdk.getAllUsers(true);
		for (var user : users.getUsers()) {
			sdk.deleteUser(user.getUserId());
		}
		for (var user : testUsers) {
			sdk.registerUser(user);
		}

		for (var cred : testCreds) {
			var finishResponse = FidoTestUtil.createCredential(sdk, Arrays.stream(testUsers).filter(u -> u.getUserId().equals(cred.getUserId())).findFirst().get());
			cred.setCredentialId(finishResponse.getCredential().getCredentialId());
			sdk.updateCredential(cred);
		}
		var user2 = new UserDataUpdateParameter(testUsers[1]);
		user2.setDisabled(true);
		sdk.updateUser(user2);
	}

	@AfterEach
	public void tearDown() throws IOException {
	}

	@Test
	public void getCredential() {
		var sdk = _sdk();
		// Not found user
		try {
			sdk.getCredential("hoge", testCreds[0].getCredentialId());
			fail();
		} catch (FssApiException e) {
			assertEquals(FssApiResultStatus.NOT_FOUND, e.getAppStatus());
		}
		// Not found credential
		try {
			sdk.getCredential(testCreds[0].getUserId(), "hoge");
			fail();
		} catch (FssApiException e) {
			assertEquals(FssApiResultStatus.NOT_FOUND, e.getAppStatus());
		}
		// Success
		{
			GetCredentialResponse cred = sdk.getCredential(testCreds[0].getUserId(), testCreds[0].getCredentialId());
			assertEquals(testUsers[0].getUserId(), cred.getUser().getUserId());
			assertEquals(testUsers[0].getUserName(), cred.getUser().getUserName());
			assertEquals(testUsers[0].getDisplayName(), cred.getUser().getDisplayName());
			assertEquals(testUsers[0].getUserAttributes(), cred.getUser().getUserAttributes());
			assertEquals(testUsers[0].isDisabled(), cred.getUser().isDisabled());
			assertEquals(testCreds[0].getUserId(), cred.getCredential().getUserId());
			assertEquals(testCreds[0].getCredentialId(), cred.getCredential().getCredentialId());
			assertEquals(testCreds[0].getCredentialName(), cred.getCredential().getCredentialName());
			assertEquals(testCreds[0].getCredentialAttributes(), cred.getCredential().getCredentialAttributes());
			assertEquals(testCreds[0].isDisabled(), cred.getCredential().isDisabled());
		}
		// Disabled user
		try {
			sdk.getCredential(testCreds[3].getUserId(), testCreds[3].getCredentialId());
			fail();
		} catch (FssApiException e) {
			assertEquals(FssApiResultStatus.NOT_FOUND, e.getAppStatus());
		}
		// Disabled user with withDisabledUser=true
		{
			GetCredentialResponse cred = sdk.getCredential(testCreds[3].getUserId(), testCreds[3].getCredentialId(), true, false);
			assertEquals(testUsers[1].getUserId(), cred.getUser().getUserId());
			assertEquals(testUsers[1].getUserName(), cred.getUser().getUserName());
			assertEquals(testUsers[1].getDisplayName(), cred.getUser().getDisplayName());
			assertEquals(testUsers[1].getUserAttributes(), cred.getUser().getUserAttributes());
			assertTrue(cred.getUser().isDisabled());
			assertEquals(testCreds[3].getUserId(), cred.getCredential().getUserId());
			assertEquals(testCreds[3].getCredentialId(), cred.getCredential().getCredentialId());
			assertEquals(testCreds[3].getCredentialName(), cred.getCredential().getCredentialName());
			assertEquals(testCreds[3].getCredentialAttributes(), cred.getCredential().getCredentialAttributes());
			assertEquals(testCreds[3].isDisabled(), cred.getCredential().isDisabled());
		}
		// Disabled credential
		try {
			sdk.getCredential(testCreds[2].getUserId(), testCreds[2].getCredentialId());
			fail();
		} catch (FssApiException e) {
			assertEquals(FssApiResultStatus.NOT_FOUND, e.getAppStatus());
		}
		// Disabled credential with withDisabledCredential=true
		{
			GetCredentialResponse cred = sdk.getCredential(testCreds[2].getUserId(), testCreds[2].getCredentialId(), false, true);
			assertEquals(testUsers[0].getUserId(), cred.getUser().getUserId());
			assertEquals(testUsers[0].getUserName(), cred.getUser().getUserName());
			assertEquals(testUsers[0].getDisplayName(), cred.getUser().getDisplayName());
			assertEquals(testUsers[0].getUserAttributes(), cred.getUser().getUserAttributes());
			assertEquals(testUsers[0].isDisabled(), cred.getUser().isDisabled());
			assertEquals(testCreds[2].getUserId(), cred.getCredential().getUserId());
			assertEquals(testCreds[2].getCredentialId(), cred.getCredential().getCredentialId());
			assertEquals(testCreds[2].getCredentialName(), cred.getCredential().getCredentialName());
			assertEquals(testCreds[2].getCredentialAttributes(), cred.getCredential().getCredentialAttributes());
			assertEquals(testCreds[2].isDisabled(), cred.getCredential().isDisabled());
		}
	}

	@Test
	public void updateCredential() {
		var sdk = _sdk();
		// Not found user
		try {
			var credPrm = new CredentialDataUpdateParameter(testCreds[0]);
			credPrm.setUserId("hoge");
			sdk.updateCredential(credPrm);
			fail();
		} catch (FssApiException e) {
			assertEquals(FssApiResultStatus.NOT_FOUND, e.getAppStatus());
		}
		// Not found credential
		try {
			var credPrm = new CredentialDataUpdateParameter(testCreds[0]);
			credPrm.setCredentialId("hoge");
			sdk.updateCredential(credPrm);
			fail();
		} catch (FssApiException e) {
			assertEquals(FssApiResultStatus.NOT_FOUND, e.getAppStatus());
		}
		// Empty credential name
		try {
			var credPrm = new CredentialDataUpdateParameter(testCreds[0]);
			credPrm.setCredentialName("");
			sdk.updateCredential(credPrm);
			fail();
		} catch (FssApiException e) {
			assertEquals(FssApiResultStatus.PARAMETER_ERROR, e.getAppStatus());
		}
		// Update credential name
		{
			var credPrm = new CredentialDataUpdateParameter(testCreds[0]);
			credPrm.setCredentialName("updated");
			UpdateCredentialResponse result = sdk.updateCredential(credPrm);
			assertEquals(credPrm.getCredentialName(), result.getCredential().getCredentialName());
			sdk.updateCredential(testCreds[0]);
		}
		// Null attributes
		{
			var credPrm = new CredentialDataUpdateParameter(testCreds[0]);
			credPrm.setCredentialAttributes(null);
			UpdateCredentialResponse result = sdk.updateCredential(credPrm);
			assertNull(result.getCredential().getCredentialAttributes());
			sdk.updateCredential(testCreds[0]);
		}
		// Update check
		try {
			var credPrm = new CredentialDataUpdateParameter(testCreds[0]);
			credPrm.setCredentialName("updated");
			credPrm.setUpdated(OffsetDateTime.now());
			sdk.updateCredential(credPrm, true);
			fail();
		} catch (FssApiException e) {
			assertEquals(FssApiResultStatus.UPDATE_ERROR, e.getAppStatus());
		}
	}

	@Test
	public void deleteCredential() {
		var sdk = _sdk();
		// Not found user
		try {
			sdk.deleteCredential("hoge", testCreds[0].getCredentialId());
			fail();
		} catch (FssApiException e) {
			assertEquals(FssApiResultStatus.NOT_FOUND, e.getAppStatus());
		}
		// Not found credential
		try {
			sdk.deleteCredential(testCreds[0].getUserId(), "hoge");
			fail();
		} catch (FssApiException e) {
			assertEquals(FssApiResultStatus.NOT_FOUND, e.getAppStatus());
		}
		// Success
		{
			var beforeDeleteUser = sdk.getUser(testCreds[0].getUserId());
			assertTrue(beforeDeleteUser.getCredentials().stream()
					.anyMatch(c -> c.getCredentialId().equals(testCreds[0].getCredentialId())));

			var result = sdk.deleteCredential(testCreds[0].getUserId(), testCreds[0].getCredentialId());
			assertEquals(testUsers[0].getUserId(), result.getUser().getUserId());
			assertEquals(testUsers[0].getUserName(), result.getUser().getUserName());
			assertEquals(testUsers[0].getDisplayName(), result.getUser().getDisplayName());
			assertEquals(testUsers[0].getUserAttributes(), result.getUser().getUserAttributes());
			assertEquals(testUsers[0].isDisabled(), result.getUser().isDisabled());
			assertEquals(testCreds[0].getUserId(), result.getCredential().getUserId());
			assertEquals(testCreds[0].getCredentialId(), result.getCredential().getCredentialId());
			assertEquals(testCreds[0].getCredentialName(), result.getCredential().getCredentialName());
			assertEquals(testCreds[0].getCredentialAttributes(), result.getCredential().getCredentialAttributes());
			assertEquals(testCreds[0].isDisabled(), result.getCredential().isDisabled());

			var deletedUser = sdk.getUser(testCreds[0].getUserId());
			assertFalse(deletedUser.getCredentials().stream()
					.anyMatch(c -> c.getCredentialId().equals(testCreds[0].getCredentialId())));
		}
	}
}
