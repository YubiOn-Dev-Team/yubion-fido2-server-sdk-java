# yubion-fido2-server-sdk-java

[English](README.md) | [Japanese](README.ja.md)

## What is yubion-fido2-server-sdk-java?
yubion-fido2-server-sdk-java is an SDK library for using YubiOn FIDO2Server Service (hereinafter referred to as YubiOn FSS) from Java. It provides APIs for easily using FIDO2 authentication (Passkey) from server applications using Java.

## How to use?
Before use, you must already be registered as a customer with YubiOn FSS and have completed RP settings. For details, please refer to the YubiOn FIDO2Server Service manual.  
  
Install the YubiOn FSS SDK in the Java project you will be using.  
Currently, registration with package management systems such as Maven has not been performed. After cloning the project, please generate a jar file using the following command and reference it in the Java project you are using. The jar file will be generated in the build/libs directory.
```
gradlew jar -x test
```
Also, the jar does not contain external libraries used by the SDK, so you will need to reference those separately. Please refer to build.gradle for the necessary external libraries.  
If you need to register with a package management system such as Maven for project reasons, please contact us.  
  
In addition to FIDO2 authentication, APIs for managing registered users and credentials are also available. For details, please refer to the [API Reference](api-reference.md).

## Test Case Execution (for SDK developer)
To run the test case, you must set the following environment properties to /src/test/resources/environment.properties:  
|Environment property name |Contents |
|-----|---|
| FSS_TEST_ENDPOINT | API endpoints (optional) for use in testing. |
| FSS_TEST_RP_ID | RPID to use for testing. The various information (users/credentials) of this RPID will be destroyed when the test case is executed, so please do not use the RPID you are using. |
| FSS_TEST_NONCE_API_AUTH_ID | API authentication ID to check the behavior of Nonce authentication |
| FSS_TEST_NONCE_CORRECT_API_SECRET_KEY | Correct API Secret Key to check for proper operation of Nonce authentication |
| FSS_TEST_NONCE_INCORRECT_API_SECRET_KEY | Wrong API Secret Key to check for Nonce authentication failure behavior |
| FSS_TEST_DATETIME_API_AUTH_ID | API authentication ID to check the operation of date and time authentication |
| FSS_TEST_DATETIME_CORRECT_API_SECRET_KEY | Correct API Secret Key to check the normal operation of date and time authentication |
| FSS_TEST_DATETIME_INCORRECT_API_SECRET_KEY | Incorrect API Secret Key to check for failed date and time authentication behavior |
| FSS_TEST_ACCESSKEY_API_AUTH_ID | API authentication ID to check the operation of access key authentication |
| FSS_TEST_ACCESSKEY_CORRECT_API_SECRET_KEY | Correct API Secret Key to check the normal operation of access key authentication |
| FSS_TEST_ACCESSKEY_INCORRECT_API_SECRET_KEY | Wrong API Secret Key to check for failing access key authentication behavior |
| FSS_TEST_RP_ID_FOR_LICENSE_TEST | RPID to use to verify operation in case of license abnormality. Please assign the RPID created by the free registered customer. The various information (users/credentials) of this RPID will be destroyed when the test case is executed, so please do not use the RPID you are using. |
| FSS_TEST_API_AUTH_ID_FOR_LICENSE_TEST | The API authentication ID for Nonce authentication to be used to check operation when license abnormalities |
| FSS_TEST_API_SECRET_KEY_FOR_LICENSE_TEST | The API secret key for Nonce authentication to be used to check operation when license abnormalities |