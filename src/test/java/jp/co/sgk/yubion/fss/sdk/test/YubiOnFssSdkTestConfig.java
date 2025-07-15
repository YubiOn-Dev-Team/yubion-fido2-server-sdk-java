package jp.co.sgk.yubion.fss.sdk.test;

import java.io.InputStream;
import java.util.Properties;

import jp.co.sgk.yubion.fss.sdk.config.FssApiAuthType;
import jp.co.sgk.yubion.fss.sdk.config.FssSdkConfig;

public class YubiOnFssSdkTestConfig {
	static final boolean ALLOW_BAD_SSL = true;
	//ユーザーがSDKを利用する場合はサーバー側がアップデートしてメンバが増える可能性があるのでfalse(デフォルト)にしておく。
	//テストケースを動作させる際は、リリース時点のサーバー側実装と形を合わせたいため、見逃しが発生しないようにtrueにする。
	static final boolean DENY_UNKNOWN_RESPONSE_MEMBER = true;

	static String RP_ID;
	static String ENDPOINT;
	
	// Nonce Auth Constants
	static String NONCE_AUTH_ID;
	static String NONCE_SECRET_KEY_OK;
	static String NONCE_SECRET_KEY_NG;

	// Datetime Auth Constants
	static String DATETIME_AUTH_ID;
	static String DATETIME_SECRET_KEY_OK;
	static String DATETIME_SECRET_KEY_NG;

	// AccessKey Auth Constants
	static String ACCESSKEY_AUTH_ID;
	static String ACCESSKEY_SECRET_KEY_OK;
	static String ACCESSKEY_SECRET_KEY_NG;
	
	static String RP_ID_FOR_LICENSE_TEST;
	static String API_AUTH_ID_FOR_LICENSE_TEST;
	static String API_SECRET_KEY_FOR_LICENSE_TEST;
	
	private static final String PROPERTIES_FILE = "environment.properties";
	public static void init() {
		boolean loaded = false;
		Properties properties = new Properties();
		try (InputStream input = YubiOnFssSdkTestConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			if (input == null) {
				System.out.println("Sorry, unable to find " + PROPERTIES_FILE);
			} else {
				properties.load(input);

				// 読み込んだプロパティをシステムプロパティに設定
				properties.forEach((key, value) -> System.setProperty(key.toString(), value.toString()));

				System.out.println("Loaded properties from " + PROPERTIES_FILE + ": " + properties);
				loaded = true;

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if(loaded){
			RP_ID = System.getProperty("FSS_TEST_RP_ID");
			ENDPOINT = System.getProperty("FSS_TEST_ENDPOINT");

			// Nonce Auth Constants
			NONCE_AUTH_ID = System.getProperty("FSS_TEST_NONCE_API_AUTH_ID");
			NONCE_SECRET_KEY_OK = System.getProperty("FSS_TEST_NONCE_CORRECT_API_SECRET_KEY");
			NONCE_SECRET_KEY_NG = System.getProperty("FSS_TEST_NONCE_INCORRECT_API_SECRET_KEY");

			// Datetime Auth Constants
			DATETIME_AUTH_ID = System.getProperty("FSS_TEST_DATETIME_API_AUTH_ID");
			DATETIME_SECRET_KEY_OK = System.getProperty("FSS_TEST_DATETIME_CORRECT_API_SECRET_KEY");
			DATETIME_SECRET_KEY_NG = System.getProperty("FSS_TEST_DATETIME_INCORRECT_API_SECRET_KEY");

			// AccessKey Auth Constants
			ACCESSKEY_AUTH_ID = System.getProperty("FSS_TEST_ACCESSKEY_API_AUTH_ID");
			ACCESSKEY_SECRET_KEY_OK = System.getProperty("FSS_TEST_ACCESSKEY_CORRECT_API_SECRET_KEY");
			ACCESSKEY_SECRET_KEY_NG = System.getProperty("FSS_TEST_ACCESSKEY_INCORRECT_API_SECRET_KEY");
			
			RP_ID_FOR_LICENSE_TEST = System.getProperty("FSS_TEST_RP_ID_FOR_LICENSE_TEST");
			API_AUTH_ID_FOR_LICENSE_TEST = System.getProperty("FSS_TEST_API_AUTH_ID_FOR_LICENSE_TEST");
			API_SECRET_KEY_FOR_LICENSE_TEST = System.getProperty("FSS_TEST_API_SECRET_KEY_FOR_LICENSE_TEST");
			
		}
	}
	static FssSdkConfig createNonceConfig(boolean correctKey) {
		String secretKey = correctKey ? NONCE_SECRET_KEY_OK : NONCE_SECRET_KEY_NG;
		FssSdkConfig config = new FssSdkConfig(RP_ID, NONCE_AUTH_ID, FssApiAuthType.NonceSignAuth, secretKey);
		if(ENDPOINT != null){
			config.setEndpoint(ENDPOINT);
		}
		return config;
	}

	static FssSdkConfig createDatetimeConfig(boolean correctKey) {
		String secretKey = correctKey ? DATETIME_SECRET_KEY_OK : DATETIME_SECRET_KEY_NG;
		FssSdkConfig config = new FssSdkConfig(RP_ID, DATETIME_AUTH_ID, FssApiAuthType.DatetimeSignAuth, secretKey);
		if(ENDPOINT != null){
			config.setEndpoint(ENDPOINT);
		}
		return config;
	}

	static FssSdkConfig createAccesskeyConfig(boolean correctKey) {
		String secretKey = correctKey ? ACCESSKEY_SECRET_KEY_OK : ACCESSKEY_SECRET_KEY_NG;
		FssSdkConfig config = new FssSdkConfig(RP_ID, ACCESSKEY_AUTH_ID, FssApiAuthType.AccessKeyAuth, secretKey);
		if(ENDPOINT != null){
			config.setEndpoint(ENDPOINT);
		}
		return config;
	}
}
