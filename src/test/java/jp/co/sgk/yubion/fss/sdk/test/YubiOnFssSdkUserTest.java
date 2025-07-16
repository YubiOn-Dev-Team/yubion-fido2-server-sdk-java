package jp.co.sgk.yubion.fss.sdk.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import jp.co.sgk.yubion.fss.sdk.YubiOnFssSdk;
import jp.co.sgk.yubion.fss.sdk.data.user.UserDataRegisterParameter;
import jp.co.sgk.yubion.fss.sdk.data.user.UserDataUpdateParameter;
import jp.co.sgk.yubion.fss.sdk.result.FssApiException;
import jp.co.sgk.yubion.fss.sdk.result.FssApiResultStatus;

public class YubiOnFssSdkUserTest {
	private static final boolean ALLOW_BAD_SSL = YubiOnFssSdkTestConfig.ALLOW_BAD_SSL;
	private static final boolean DENY_UNKNOWN_RESPONSE_MEMBER = YubiOnFssSdkTestConfig.DENY_UNKNOWN_RESPONSE_MEMBER;
	private static String _makeUserId(IntFunction<Byte> func) {
		return _makeUserId(func, 64);
	}
	private static String _makeUserId(IntFunction<Byte> func, int length) {
		var buf = new byte[length];
		for(int i = 0; i < length; i++) {
			buf[i] = func.apply(i);
		}
		return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
	}
	private static YubiOnFssSdk _sdk(){
		return new YubiOnFssSdk(YubiOnFssSdkTestConfig.createNonceConfig(true), DENY_UNKNOWN_RESPONSE_MEMBER, ALLOW_BAD_SSL);
	}
	
	private static final UserDataRegisterParameter[] testUsers = new UserDataRegisterParameter[] {
		new UserDataRegisterParameter(
			_makeUserId(i -> (byte)i),
			"user1@example.com",
			"user 1",
			new HashMap<String, Object>(){ { put("attr1", "user1"); } },
			false
		),
		new UserDataRegisterParameter(
			_makeUserId(i -> (byte)(i + 1)),
			"user2@example.com",
			"user 2",
			new HashMap<String, Object>(){ { put("attr1", "user2"); } },
			false
		),
		new UserDataRegisterParameter(
			_makeUserId(i -> (byte)(i + 2)),
			"user3@example.com",
			"user 3",
			new HashMap<String, Object>(){ { put("attr1", "user3"); } },
			false
		),
		new UserDataRegisterParameter(
			_makeUserId(i -> (byte)(i + 3)),
			"user4@example.com",
			"user 4",
			new HashMap<String, Object>(){ { put("attr1", "user4"); } },
			true
		),
	};
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		YubiOnFssSdkTestConfig.init();
	}
	
	@BeforeEach
	public void setUp() throws IOException {
		var sdk = _sdk();
		var users = sdk.getAllUsers(true);
		for(var user : users.getUsers()) {
			sdk.deleteUser(user.getUserId());
		}
		for(var user : testUsers) {
			sdk.registerUser(user);
		}
	}
	@AfterEach
	public void tearDown() throws IOException {
	}
	@Test
	public void getAllUsers() {
		{
			var sdk = _sdk();
			var result = sdk.getAllUsers();
			assertNotNull(result);
			assertNotNull(result.getUsers());
			assertEquals(result.getUsers().size(), 3);
			assertEquals(result.getUsers().stream().filter(x -> x.isDisabled()).count(), 0);
		}
		{
			var sdk = _sdk();
			var result = sdk.getAllUsers(true);
			assertNotNull(result);
			assertNotNull(result.getUsers());
			assertEquals(result.getUsers().size(), 4);
			assertEquals(result.getUsers().stream().filter(x -> x.isDisabled()).count(), 1);
		}
		{
			var sdk = _sdk();
			var result = sdk.getAllUsers(true);
			assertNotNull(result);
			assertNotNull(result.getUsers());
			for(var user : testUsers){
				var targetUser = result.getUsers().stream().filter(x -> x.getUserId().equals(user.getUserId())).findFirst();
				assertNotNull(targetUser);
				assertEquals(user.getUserId(), targetUser.get().getUserId());
				assertEquals(user.getUserName(), targetUser.get().getUserName());
				assertEquals(user.getDisplayName(), targetUser.get().getDisplayName());
				assertEquals(user.getUserAttributes().get("attr1"), targetUser.get().getUserAttributes().get("attr1"));
				assertEquals(user.isDisabled(), targetUser.get().isDisabled());
			}
		}
	}
	@Test
	public void getUser(){
		{
			var sdk = _sdk();
			var result = sdk.getUser(testUsers[0].getUserId());
			assertNotNull(result);
			assertNotNull(result.getUser());
			assertEquals(testUsers[0].getUserId(), result.getUser().getUserId());
			assertEquals(testUsers[0].getUserName(), result.getUser().getUserName());
			assertEquals(testUsers[0].getDisplayName(), result.getUser().getDisplayName());
			assertEquals(testUsers[0].getUserAttributes().get("attr1"), result.getUser().getUserAttributes().get("attr1"));
			assertEquals(testUsers[0].isDisabled(), result.getUser().isDisabled());
		}
		{
			var sdk = _sdk();
			var result = sdk.getUser(testUsers[3].getUserId(), true);
			assertNotNull(result);
			assertNotNull(result.getUser());
			assertEquals(testUsers[3].getUserId(), result.getUser().getUserId());
			assertEquals(testUsers[3].getUserName(), result.getUser().getUserName());
			assertEquals(testUsers[3].getDisplayName(), result.getUser().getDisplayName());
			assertEquals(testUsers[3].getUserAttributes().get("attr1"), result.getUser().getUserAttributes().get("attr1"));
			assertEquals(testUsers[3].isDisabled(), result.getUser().isDisabled());
		}
		{
			var sdk = _sdk();
			try {
				sdk.getUser(testUsers[3].getUserId(), false);
				fail();
			} catch (FssApiException e) {
				assertEquals(FssApiResultStatus.NOT_FOUND, e.getAppStatus());
			}
		}
		{
			var sdk = _sdk();
			try {
				sdk.getUser("hoge", true);
				fail();
			} catch (FssApiException e) {
				assertEquals(FssApiResultStatus.NOT_FOUND, e.getAppStatus());
			}
		}
	}
	@Test
	public void getUsersByUserName(){
		{
			var sdk = _sdk();
			var result = sdk.getUsersByUserName(testUsers[0].getUserName());
			assertNotNull(result);
			assertNotNull(result.getUsers());
			assertEquals(1, result.getUsers().size());
			assertEquals(testUsers[0].getUserId(), result.getUsers().get(0).getUserId());
			assertEquals(testUsers[0].getUserName(), result.getUsers().get(0).getUserName());
			assertEquals(testUsers[0].getDisplayName(), result.getUsers().get(0).getDisplayName());
			assertEquals(testUsers[0].getUserAttributes().get("attr1"), result.getUsers().get(0).getUserAttributes().get("attr1"));
			assertEquals(testUsers[0].isDisabled(), result.getUsers().get(0).isDisabled());
		}
		{
			var sdk = _sdk();
			var result = sdk.getUsersByUserName(testUsers[3].getUserName(), true);
			assertNotNull(result);
			assertNotNull(result.getUsers());
			assertEquals(1, result.getUsers().size());
			assertEquals(testUsers[3].getUserId(), result.getUsers().get(0).getUserId());
			assertEquals(testUsers[3].getUserName(), result.getUsers().get(0).getUserName());
			assertEquals(testUsers[3].getDisplayName(), result.getUsers().get(0).getDisplayName());
			assertEquals(testUsers[3].getUserAttributes().get("attr1"), result.getUsers().get(0).getUserAttributes().get("attr1"));
			assertEquals(testUsers[3].isDisabled(), result.getUsers().get(0).isDisabled());
		}
		{
			var sdk = _sdk();
			try {
				sdk.getUsersByUserName(testUsers[3].getUserName(), false);
				fail();
			} catch (FssApiException e) {
				assertEquals(FssApiResultStatus.NOT_FOUND, e.getAppStatus());
			}
		}
		{
			var sdk = _sdk();
			try{
				sdk.getUsersByUserName("hoge", true);
				fail();
			} catch (FssApiException e) {
				assertEquals(FssApiResultStatus.NOT_FOUND, e.getAppStatus());
			}
		}
	}
	@Test
	public void registerUser() {
		var baseUserPrm = new UserDataRegisterParameter(
			_makeUserId((i) -> (byte)(i + 10)),
			"register_test@example.com",
			"register user",
			Map.of("attr1", "user register"),
			false
		);
		{
			var userPrm = new UserDataRegisterParameter(baseUserPrm);
			var sdk = _sdk();
			var result = sdk.registerUser(userPrm);
			assertNotNull(result);
			assertNotNull(result.getUser());
			assertEquals(userPrm.getUserId(), result.getUser().getUserId());
			assertEquals(userPrm.getUserName(), result.getUser().getUserName());
			assertEquals(userPrm.getDisplayName(), result.getUser().getDisplayName());
			assertEquals(userPrm.getUserAttributes().get("attr1"), result.getUser().getUserAttributes().get("attr1"));
			assertEquals(userPrm.isDisabled(), result.getUser().isDisabled());
			assertEquals(0, result.getUser().getCredentialCount());
			assertEquals(0, result.getUser().getEnabledCredentialCount());
			
			sdk.deleteUser(userPrm.getUserId());
		}
		{
			//duplicated id
			var userPrm = new UserDataRegisterParameter(baseUserPrm);
			var sdk = _sdk();
			userPrm.setUserId(_makeUserId((i) -> (byte)(i)));
			try {
				sdk.registerUser(userPrm);
				fail();
			} catch (FssApiException e) {
				assertEquals(FssApiResultStatus.ALREADY_EXISTS, e.getAppStatus());
			}
		}
		{
			//null id
			var userPrm = new UserDataRegisterParameter(baseUserPrm);
			var sdk = _sdk();
			userPrm.setUserId(null);
			try {
				sdk.registerUser(userPrm);
				fail();
			} catch (FssApiException e) {
				assertEquals(FssApiResultStatus.PARAMETER_ERROR, e.getAppStatus());
			}
		}
		{
			//empty id
			var userPrm = new UserDataRegisterParameter(baseUserPrm);
			var sdk = _sdk();
			userPrm.setUserId("");
			try {
				sdk.registerUser(userPrm);
				fail();
			} catch (FssApiException e) {
				assertEquals(FssApiResultStatus.PARAMETER_ERROR, e.getAppStatus());
			}
		}
		{
			//not base64url id
			var userPrm = new UserDataRegisterParameter(baseUserPrm);
			var sdk = _sdk();
			userPrm.setUserId("!!!");
			try {
				sdk.registerUser(userPrm);
				fail();
			} catch (FssApiException e) {
				assertEquals(FssApiResultStatus.PARAMETER_ERROR, e.getAppStatus());
			}
		}
		{
			//too long id
			var userPrm = new UserDataRegisterParameter(baseUserPrm);
			var sdk = _sdk();
			userPrm.setUserId(_makeUserId((i) -> (byte)(i + 10), 65));
			try {
				sdk.registerUser(userPrm);
				fail();
			} catch (FssApiException e) {
				assertEquals(FssApiResultStatus.PARAMETER_ERROR, e.getAppStatus());
			}
		}
		{
			//too long id(2)
			var userPrm = new UserDataRegisterParameter(baseUserPrm);
			var sdk = _sdk();
			userPrm.setUserId("123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
			try {
				sdk.registerUser(userPrm);
				fail();
			} catch (FssApiException e) {
				assertEquals(FssApiResultStatus.PARAMETER_ERROR, e.getAppStatus());
			}
		}
		{
			//null name
			var userPrm = new UserDataRegisterParameter(baseUserPrm);
			var sdk = _sdk();
			userPrm.setUserName(null);
			try {
				sdk.registerUser(userPrm);
				fail();
			} catch (FssApiException e) {
				assertEquals(FssApiResultStatus.PARAMETER_ERROR, e.getAppStatus());
			}
		}
		{
			//empty name
			var userPrm = new UserDataRegisterParameter(baseUserPrm);
			var sdk = _sdk();
			userPrm.setUserName("");
			try {
				sdk.registerUser(userPrm);
				fail();
			} catch (FssApiException e) {
				assertEquals(FssApiResultStatus.PARAMETER_ERROR, e.getAppStatus());
			}
		}
		{
			//null display name
			var userPrm = new UserDataRegisterParameter(baseUserPrm);
			userPrm.setDisplayName(null);
			var sdk = _sdk();
			var result = sdk.registerUser(userPrm);
			assertNotNull(result);
			assertNotNull(result.getUser());
			assertNull(result.getUser().getDisplayName());
			
			sdk.deleteUser(userPrm.getUserId());
		}
		{
			//null attribute
			var userPrm = new UserDataRegisterParameter(baseUserPrm);
			userPrm.setUserAttributes(null);
			var sdk = _sdk();
			var result = sdk.registerUser(userPrm);
			assertNotNull(result);
			assertNotNull(result.getUser());
			assertNull(result.getUser().getUserAttributes());
			
			sdk.deleteUser(userPrm.getUserId());
		}
	}
	@Test
	public void updateUser() {
		var baseUserPrm = new UserDataUpdateParameter(
			_makeUserId((i) -> (byte)(i + 20)),
			"update_test@example.com",
			"update user",
			Map.of("attr1", "user update"),
			false
		);
		{//initialize
			var sdk = _sdk();
			try {
				sdk.deleteUser(baseUserPrm.getUserId());
			} catch(Exception e){}
			sdk.registerUser(baseUserPrm);
		}
		{
			//non-exists user
			var userPrm = new UserDataUpdateParameter(baseUserPrm);
			userPrm.setUserId(_makeUserId((i) -> (byte)(i * 2)));
			var sdk = _sdk();
			try {
				sdk.updateUser(userPrm);
				fail();
			} catch (FssApiException e) {
				assertEquals(FssApiResultStatus.NOT_FOUND, e.getAppStatus());
			}
		}
		{
			//null id
			var userPrm = new UserDataUpdateParameter(baseUserPrm);
			userPrm.setUserId(null);
			var sdk = _sdk();
			try {
				sdk.updateUser(userPrm);
				fail();
			} catch (FssApiException e) {
				assertEquals(FssApiResultStatus.PARAMETER_ERROR, e.getAppStatus());
			}
		}
		{
			//empty id
			var userPrm = new UserDataUpdateParameter(baseUserPrm);
			userPrm.setUserId("");
			var sdk = _sdk();
			try {
				sdk.updateUser(userPrm);
				fail();
			} catch (FssApiException e) {
				assertEquals(FssApiResultStatus.PARAMETER_ERROR, e.getAppStatus());
			}
		}
		{
			// not base64url id
			var userPrm = new UserDataUpdateParameter(baseUserPrm);
			userPrm.setUserId("!!!");
			var sdk = _sdk();
			try {
				sdk.updateUser(userPrm);
				fail();
			} catch (FssApiException e) {
				assertEquals(FssApiResultStatus.PARAMETER_ERROR, e.getAppStatus());
			}
		}
		{
			//too long id
			var userPrm = new UserDataUpdateParameter(baseUserPrm);
			userPrm.setUserId(_makeUserId((i) -> (byte)(i + 20), 65));
			var sdk = _sdk();
			try {
				sdk.updateUser(userPrm);
				fail();
			} catch (FssApiException e) {
				assertEquals(FssApiResultStatus.PARAMETER_ERROR, e.getAppStatus());
			}
		}
		{
			//too long id(2)
			var userPrm = new UserDataUpdateParameter(baseUserPrm);
			userPrm.setUserId("123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
			var sdk = _sdk();
			try {
				sdk.updateUser(userPrm);
				fail();
			} catch (FssApiException e) {
				assertEquals(FssApiResultStatus.PARAMETER_ERROR, e.getAppStatus());
			}
		}
		{
			//update name
			var userPrm = new UserDataUpdateParameter(baseUserPrm);
			userPrm.setUserName("user_updated@example.com");
			var sdk = _sdk();
			var result = sdk.updateUser(userPrm);
			assertNotNull(result);
			assertNotNull(result.getUser());
			assertEquals(userPrm.getUserId(), result.getUser().getUserId());
			assertEquals(userPrm.getUserName(), result.getUser().getUserName());
			assertEquals(userPrm.getDisplayName(), result.getUser().getDisplayName());
			assertEquals(userPrm.getUserAttributes().get("attr1"), result.getUser().getUserAttributes().get("attr1"));
			assertEquals(userPrm.isDisabled(), result.getUser().isDisabled());
			
			assertEquals(sdk.getRpId(), result.getSignalCurrentUserDetailsOptions().getRpId());
			assertEquals(userPrm.getUserId(), result.getSignalCurrentUserDetailsOptions().getUserId());
			assertEquals(userPrm.getUserName(), result.getSignalCurrentUserDetailsOptions().getName());
			assertEquals(userPrm.getDisplayName(), result.getSignalCurrentUserDetailsOptions().getDisplayName());
			
			sdk.updateUser(baseUserPrm);
		}
		{
			//update display name
			var userPrm = new UserDataUpdateParameter(baseUserPrm);
			userPrm.setDisplayName("user_updated");
			var sdk = _sdk();
			var result = sdk.updateUser(userPrm);
			assertNotNull(result);
			assertNotNull(result.getUser());
			assertEquals(userPrm.getUserId(), result.getUser().getUserId());
			assertEquals(userPrm.getUserName(), result.getUser().getUserName());
			assertEquals(userPrm.getDisplayName(), result.getUser().getDisplayName());
			assertEquals(userPrm.getUserAttributes().get("attr1"), result.getUser().getUserAttributes().get("attr1"));
			assertEquals(userPrm.isDisabled(), result.getUser().isDisabled());
			
			sdk.updateUser(baseUserPrm);
		}
		{
			//empty name
			var userPrm = new UserDataUpdateParameter(baseUserPrm);
			userPrm.setUserName("");
			var sdk = _sdk();
			try {
				sdk.updateUser(userPrm);
				fail();
			} catch (FssApiException e) {
				assertEquals(FssApiResultStatus.PARAMETER_ERROR, e.getAppStatus());
			}
		}
		{
			//duplicated user name
			var userPrm = new UserDataUpdateParameter(baseUserPrm);
			userPrm.setUserName(testUsers[0].getUserName());
			var sdk = _sdk();
			try {
				sdk.updateUser(userPrm);
				fail();
			} catch (FssApiException e) {
				assertEquals(FssApiResultStatus.DUPLICATED, e.getAppStatus());
			}
		}
		{
			//duplicated display name
			var userPrm = new UserDataUpdateParameter(baseUserPrm);
			userPrm.setDisplayName(testUsers[0].getDisplayName());
			var sdk = _sdk();
			var result = sdk.updateUser(userPrm);
			assertNotNull(result);
			assertNotNull(result.getUser());
			assertEquals(userPrm.getUserId(), result.getUser().getUserId());
			assertEquals(userPrm.getUserName(), result.getUser().getUserName());
			assertEquals(userPrm.getDisplayName(), result.getUser().getDisplayName());
			assertEquals(userPrm.getUserAttributes().get("attr1"), result.getUser().getUserAttributes().get("attr1"));
			assertEquals(userPrm.isDisabled(), result.getUser().isDisabled());
			
			sdk.updateUser(baseUserPrm);
		}
		{
			//null display name
			var userPrm = new UserDataUpdateParameter(baseUserPrm);
			userPrm.setDisplayName(null);
			var sdk = _sdk();
			var result = sdk.updateUser(userPrm);
			assertNotNull(result);
			assertNotNull(result.getUser());
			assertEquals(userPrm.getUserId(), result.getUser().getUserId());
			assertEquals(userPrm.getUserName(), result.getUser().getUserName());
			assertEquals(userPrm.getDisplayName(), result.getUser().getDisplayName());
			assertEquals(userPrm.getUserAttributes().get("attr1"), result.getUser().getUserAttributes().get("attr1"));
			assertEquals(userPrm.isDisabled(), result.getUser().isDisabled());
			
			sdk.updateUser(baseUserPrm);
		}
		{
			//update attributes
			var userPrm = new UserDataUpdateParameter(baseUserPrm);
			userPrm.setUserAttributes(Map.of("attr2", "user updated"));
			var sdk = _sdk();
			var result = sdk.updateUser(userPrm);
			assertNotNull(result);
			assertNotNull(result.getUser());
			assertEquals(userPrm.getUserId(), result.getUser().getUserId());
			assertEquals(userPrm.getUserName(), result.getUser().getUserName());
			assertEquals(userPrm.getDisplayName(), result.getUser().getDisplayName());
			assertNull(result.getUser().getUserAttributes().get("attr1"));
			assertEquals(userPrm.getUserAttributes().get("attr2"), result.getUser().getUserAttributes().get("attr2"));
			assertEquals(userPrm.isDisabled(), result.getUser().isDisabled());
			
			sdk.updateUser(baseUserPrm);
		}
		{
			//null attributes
			var userPrm = new UserDataUpdateParameter(baseUserPrm);
			userPrm.setUserAttributes(null);
			var sdk = _sdk();
			var result = sdk.updateUser(userPrm);
			assertNotNull(result);
			assertNotNull(result.getUser());
			assertEquals(userPrm.getUserId(), result.getUser().getUserId());
			assertEquals(userPrm.getUserName(), result.getUser().getUserName());
			assertEquals(userPrm.getDisplayName(), result.getUser().getDisplayName());
			assertNull(result.getUser().getUserAttributes());
			assertEquals(userPrm.isDisabled(), result.getUser().isDisabled());
			
			sdk.updateUser(baseUserPrm);
		}
		{
			//disabled to true/false
			var userPrm = new UserDataUpdateParameter(baseUserPrm);
			userPrm.setDisabled(true);
			var sdk = _sdk();
			var result = sdk.updateUser(userPrm);
			assertNotNull(result);
			assertNotNull(result.getUser());
			assertEquals(userPrm.getUserId(), result.getUser().getUserId());
			assertEquals(userPrm.getUserName(), result.getUser().getUserName());
			assertEquals(userPrm.getDisplayName(), result.getUser().getDisplayName());
			assertEquals(userPrm.getUserAttributes().get("attr1"), result.getUser().getUserAttributes().get("attr1"));
			assertEquals(userPrm.isDisabled(), result.getUser().isDisabled());
			
			userPrm.setDisabled(false);
			var result2 = sdk.updateUser(userPrm);
			assertNotNull(result2);
			assertNotNull(result2.getUser());
			assertEquals(userPrm.getUserId(), result2.getUser().getUserId());
			assertEquals(userPrm.getUserName(), result2.getUser().getUserName());
			assertEquals(userPrm.getDisplayName(), result2.getUser().getDisplayName());
			assertEquals(userPrm.getUserAttributes().get("attr1"), result2.getUser().getUserAttributes().get("attr1"));
			assertEquals(userPrm.isDisabled(), result2.getUser().isDisabled());
			
			sdk.updateUser(baseUserPrm);
		}
		{
			//check updated(failed)
			var sdk = _sdk();
			var userPrm = sdk.getUser(baseUserPrm.getUserId());
			var updPrm = new UserDataUpdateParameter(userPrm.getUser());
			updPrm.setUpdated(OffsetDateTime.now());
			try {
				sdk.updateUser(updPrm, true);
				fail();
			} catch (FssApiException e) {
				assertEquals(FssApiResultStatus.UPDATE_ERROR, e.getAppStatus());
			}
		}
		{
			//check updated(success)
			var sdk = _sdk();
			var userPrm = sdk.getUser(baseUserPrm.getUserId());
			var updPrm = new UserDataUpdateParameter(userPrm.getUser());
			updPrm.setDisplayName("updated");
			var result = sdk.updateUser(updPrm);
			assertEquals(updPrm.getDisplayName(), result.getUser().getDisplayName());
		}
	}
	@Test
	public void deleteUser() {
		var baseUserPrm = new UserDataUpdateParameter(
			_makeUserId((i) -> (byte)(i + 30)),
			"delete_test@example.com",
			"delete user",
			Map.of("attr1", "user delete"),
			false
		);
		{//initialize
			var sdk = _sdk();
			try {
				sdk.deleteUser(baseUserPrm.getUserId());
			} catch(Exception e){}
			sdk.registerUser(baseUserPrm);
		}
		{
			var sdk = _sdk();
			try {
				var result = sdk.deleteUser(baseUserPrm.getUserId());
				assertNotNull(result);
				assertNotNull(result.getUser());
				assertEquals(baseUserPrm.getUserId(), result.getUser().getUserId());
				assertEquals(baseUserPrm.getUserName(), result.getUser().getUserName());
				assertEquals(baseUserPrm.getDisplayName(), result.getUser().getDisplayName());
				assertEquals(baseUserPrm.getUserAttributes().get("attr1"), result.getUser().getUserAttributes().get("attr1"));
				assertEquals(baseUserPrm.isDisabled(), result.getUser().isDisabled());

				try{
					sdk.getUser(baseUserPrm.getUserId());
					fail();
				} catch (FssApiException e) {
					assertEquals(FssApiResultStatus.NOT_FOUND, e.getAppStatus());
				}
			} finally {
				sdk.registerUser(baseUserPrm);
			}
		}
		{
			//non-exists user
			var sdk = _sdk();
			try {
				sdk.deleteUser(_makeUserId((i) -> (byte)(i * 2 + 10)));
				fail();
			} catch (FssApiException e) {
				assertEquals(FssApiResultStatus.NOT_FOUND, e.getAppStatus());
			}
		}
	}
}
