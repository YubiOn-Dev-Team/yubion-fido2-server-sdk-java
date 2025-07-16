# YubiOn FIDO2 Server SDK for Java API Reference

This document is the API reference for the YubiOn FIDO2 Server SDK for Java.  
[English](api-reference.md) | [Japanese](api-reference.ja.md)

## `jp.co.sgk.yubion.fss.sdk.YubiOnFssSdk` Class

This is the main class for communicating with the FIDO2 server.

### Constructor

#### `new YubiOnFssSdk(FssSdkConfig config)`

Initializes a new instance of the `YubiOnFssSdk`.

**Arguments:**

*   `config` (`FssSdkConfig`): Connection settings for the FIDO2 server.
    *   `endpoint` (`String`, Optional, Default: `"https://fss.yubion.com/api/"`): The API endpoint URL.
    *   `rpId` (`String`): The ID of the RP (Relying Party) using this SDK.
    *   `apiAuthId` (`String`): The ID used for API authentication.
    *   `apiAuthType` (`FssApiAuthType`): The type of API authentication.
    *   `secretKey` (`String`): The secret key used for API authentication.
    *   `agent` (`String`, Optional, Default: `"yubion-fido2-server-sdk-java"`): The user agent string used in API requests.

---

## User Management

### `GetUserResponse` `getUser` `(String userId)`
### `GetUserResponse` `getUser` `(String userId, boolean withDisabledUser)`
### `GetUserResponse` `getUser` `(String userId, boolean withDisabledUser, boolean withDisabledCredential)`

Retrieves user information for the specified ID.

**Arguments:**

*   `userId` (`String`): The ID of the user to retrieve.
*   `withDisabledUser` (`boolean`, Optional, Default: `false`): Whether to include disabled users.
*   `withDisabledCredential` (`boolean`, Optional, Default: `false`): Whether to include disabled credentials.

**Returns:**

*   `GetUserResponse`: An object containing user information and an array of credential information.
    *   `user` (`UserDataWithCredentialCount`): User information.
    *   `credentials` (`List<CredentialData>`): An array of credential information associated with the user.
    *   `signalCurrentUserDetailsOptions` (`SignalCurrentUserDetailsOptions`): Signal information for updating user information on the client side. Use as an argument for calling `PublicKeyCredential.signalCurrentUserDetails()`.

**Potential Exceptions:**

*   `FssApiException`: Thrown if the API request fails.

### `GetUsersResponse` `getAllUsers` `()`
### `GetUsersResponse` `getAllUsers` `(boolean withDisabledUser)`

Retrieves all user information.

**Arguments:**

*   `withDisabledUser` (`boolean`, Optional, Default: `false`): Whether to include disabled users.

**Returns:**

*   `GetUsersResponse`: An object containing an array of user information.
    *   `users` (`List<UserDataWithCredentialCount>`): An array of user information.

**Potential Exceptions:**

*   `FssApiException`: Thrown if the API request fails.

### `GetUsersResponse` `getUsersByUserName` `(String userName)`
### `GetUsersResponse` `getUsersByUserName` `(String userName, boolean withDisabledUser)`

Retrieves user information for the specified username.

**Arguments:**

*   `userName` (`String`): The username to search for.
*   `withDisabledUser` (`boolean`, Optional, Default: `false`): Whether to include disabled users.

**Returns:**

*   `GetUsersResponse`: An object containing an array of user information.
    *   `users` (`List<UserDataWithCredentialCount>`): An array of user information.

**Potential Exceptions:**

*   `FssApiException`: Thrown if the API request fails.

### `RegisterUserResponse` `registerUser` `(UserDataRegisterParameter parameter)`

Registers a new user.

**Arguments:**

*   `parameter` (`UserDataRegisterParameter`): The information of the user to register.
    *   `userId` (`String`): User ID.
    *   `userName` (`String`): Username.
    *   `displayName` (`String`, Optional): Display name.
    *   `userAttributes` (`Map<String, Object>` or `String`, Optional): Additional user attributes. Specify as a JSON object or JSON string.
    *   `disabled` (`boolean`): Whether to register the user as disabled.

**Returns:**

*   `RegisterUserResponse`: An object containing the registered user's information.
    *   `user` (`UserDataWithCredentialCount`): The registered user's information.

**Potential Exceptions:**

*   `FssApiException`: Thrown if the API request fails.

### `UpdateUserResponse` `updateUser` `(UserDataUpdateParameter parameter)`
### `UpdateUserResponse` `updateUser` `(UserDataUpdateParameter parameter, boolean withUpdatedCheck)`

Updates the information of the specified user.

**Arguments:**

*   `parameter` (`UserDataUpdateParameter`): The information of the user to update.
    *   `userId` (`String`): User ID.
    *   `userName` (`String`): Username.
    *   `displayName` (`String`, Optional): Display name.
    *   `userAttributes` (`Map<String, Object>` or `String`, Optional): Additional user attributes. Specify as a JSON object or JSON string.
    *   `disabled` (`boolean`): Whether to disable the user.
    *   `updated` (`OffsetDateTime`, Optional): The last updated date and time. If `withUpdatedCheck` is `true`, the update will fail if this value does not match the `updated` value in the database.
*   `withUpdatedCheck` (`boolean`, Optional, Default: `false`): Whether to check the last updated date and time.

**Returns:**

*   `UpdateUserResponse`: An object containing the updated user information.
    *   `user` (`UserDataWithCredentialCount`): The updated user information.
    *   `signalCurrentUserDetailsOptions` (`SignalCurrentUserDetailsOptions`): Signal information for updating user information on the client side. Use as an argument for calling `PublicKeyCredential.signalCurrentUserDetails()`.

**Potential Exceptions:**

*   `FssApiException`: Thrown if the API request fails.

### `DeleteUserResponse` `deleteUser` `(String userId)`

Deletes the user with the specified ID.

**Arguments:**

*   `userId` (`String`): The ID of the user to delete.

**Returns:**

*   `DeleteUserResponse`: An object containing the deleted user information and related information.
    *   `user` (`UserDataWithCredentialCount`): The deleted user information.
    *   `credentials` (`List<CredentialData>`): An array of credential information that was associated with the deleted user.
    *   `signalAllAcceptedCredentialsOptions` (`SignalAllAcceptedCredentialsOptions`): Signal information for deleting credential information on the client side. Use as an argument for calling `PublicKeyCredential.signalAllAcceptedCredentials()`.

**Potential Exceptions:**

*   `FssApiException`: Thrown if the API request fails.

---

## FIDO2/WebAuthn Credential Management

### `ApiSessionResponse<StartRegisterCredentialResponse>` `startRegisterCredential` `(Fido2StartRegisterParameter parameter) throws FssApiException`

Starts the credential registration process.

**Arguments:**

*   `parameter` (`Fido2StartRegisterParameter`): Request parameters for starting credential registration.
    *   `creationOptionsBase` (`Fido2CreationOptionsBase`): The base part of WebAuthn's `PublicKeyCredentialCreationOptions`.
        *   `authenticatorSelection` (`AuthenticatorSelectionCriteria`, Optional): Criteria for selecting an authenticator.
        *   `timeout` (`long`, Optional): Timeout period in milliseconds.
        *   `hints` (`List<String>`, Optional): An array of strings to hint to the user agent (e.g., browser) about the authentication method. Can be "security-key", "client-device", or "hybrid".
        *   `attestation` (`AttestationConveyancePreference`, Optional): Attestation conveyance preference.
        *   `extensions` (`Map<String, Object>`, Optional): Extension data.
    *   `user` (`UserDataRegisterParameter`): Information of the user to register.
        *   `userId` (`String`): User ID. Must be specified by encoding binary data of up to 64 bytes in Base64URL format (without padding).
        *   `userName` (`String`, Optional): Username. Optional if both `options.createUserIfNotExists` and `options.updateUserIfExists` are `false`.
        *   `displayName` (`String`, Optional): Display name. Optional if both `options.createUserIfNotExists` and `options.updateUserIfExists` are `false`.
        *   `userAttributes` (`Map<String, Object>` or `String`, Optional): Additional user attributes. Optional if both `options.createUserIfNotExists` and `options.updateUserIfExists` are `false`.
        *   `disabled` (`boolean`, Optional): Whether the user is disabled. Cannot be set to `false` during registration. Optional if both `options.createUserIfNotExists` and `options.updateUserIfExists` are `false`.
    *   `options` (`Fido2RegisterOptions`, Optional): Additional options for the registration process.
        *   `createUserIfNotExists` (`boolean`, Optional): Whether to create a new user if one does not exist.
        *   `updateUserIfExists` (`boolean`, Optional): Whether to update an existing user.
        *   `credentialName` (`Fido2CredentialNameParameter`, Optional): Credential name. See `Fido2CredentialNameParameter` in the data structures section for details.
        *   `credentialAttributes` (`Map<String, Object>` or `String`, Optional): Additional credential attributes.

**Returns:**

*   `ApiSessionResponse<StartRegisterCredentialResponse>`: An object containing creation options for credential registration, user information, and a session string.
    *   `response` (`StartRegisterCredentialResponse`):
        *   `creationOptions` (`PublicKeyCredentialCreationOptionsJSON`): Options to pass to WebAuthn's `navigator.credentials.create()`.
        *   `user` (`UserDataWithCredentialCount`): User information.
    *   `session` (`String`): A session string to be used in subsequent `verifyRegisterCredential` or `finishRegisterCredential` calls.

**Potential Exceptions:**

*   `FssApiException`: Thrown if the API request fails.

### `FinishRegisterCredentialResponse` `verifyRegisterCredential` `(Fido2FinishRegisterParameter parameter, String sessionCookie) throws FssApiException`

Verifies the credential registration parameters. This method analyzes the registration details before actual registration and provides them in an easy-to-understand format for the RP (the program using the SDK) to confirm. The RP should check the return value and, if there are no issues, call `finishRegisterCredential`.

**Arguments:**

*   `parameter` (`Fido2FinishRegisterParameter`): Parameters to complete the credential registration.
    *   `createResponse`: The response from the authenticator.
        *   `attestationResponse` (`String` or `PublicKeyCredential`): The attestation response from the authenticator. A JSON string or `PublicKeyCredential` object. Set the return value of `navigator.credentials.create()`.
        *   `transports` (`String` or `List<String>`, Optional): The transport protocols supported by the authenticator. If you want to save the transport protocols, call the `getTransports()` method of the `response` member of the return value of `navigator.credentials.create()` and set its return value to the `transports` member.
    *   `options` (Optional):
        *   `credentialName` (`Fido2CredentialNameParameter`, Optional): Credential name. See `Fido2CredentialNameParameter` in the data structures section for details.
*   `sessionCookie` (`String`): The session string obtained from `startRegisterCredential`.

**Returns:**

*   `FinishRegisterCredentialResponse`: An object containing the verified credential data and user information.

**Potential Exceptions:**

*   `FssApiException`: Thrown if the API request fails.

### `FinishRegisterCredentialResponse` `finishRegisterCredential` `(Fido2FinishRegisterParameter parameter, String sessionCookie) throws FssApiException`

Completes the credential registration process and saves the credential to the user's account.

**Arguments:**

*   `parameter` (`Fido2FinishRegisterParameter`): Same as `verifyRegisterCredential`.
*   `sessionCookie` (`String`): The session string obtained from `startRegisterCredential`.

**Returns:**

*   `FinishRegisterCredentialResponse`: An object containing the registered credential data and user information.

**Potential Exceptions:**

*   `FssApiException`: Thrown if the API request fails.

### `ApiSessionResponse<StartAuthenticateResponse>` `startAuthenticate` `(Fido2StartAuthenticateParameter parameter) throws FssApiException`

Starts the authentication process. If no user is specified, authentication is performed using a Discoverable Credential.

**Arguments:**

*   `parameter` (`Fido2StartAuthenticateParameter`): Request parameters for starting authentication.
    *   `requestOptionsBase` (`Fido2RequestOptionsBase`): The base part of WebAuthn's `PublicKeyCredentialRequestOptions`.
        *   `timeout` (`long`, Optional): Timeout period in milliseconds.
        *   `hints` (`List<String>`, Optional): An array of strings to hint to the user agent (e.g., browser) about the authentication method. Can be "security-key", "client-device", or "hybrid".
        *   `userVerification` (`UserVerificationRequirement`, Optional): User verification requirement.
        *   `extensions` (`Map<String, Object>`, Optional): Extension data.
    *   `userId` (`String`, Optional): User ID. Omit this if using a Discoverable Credential.
    *   `options` (`Fido2AuthenticateOptions`, Optional): Additional options for the authentication process. This object currently has no members defined but is reserved for future expansion.

**Returns:**

*   `ApiSessionResponse<StartAuthenticateResponse>`: An object containing request options for authentication, user information, and a session string.
    *   `response` (`StartAuthenticateResponse`):
        *   `requestOptions` (`PublicKeyCredentialRequestOptionsJSON`): Options to pass to WebAuthn's `navigator.credentials.get()`.
        *   `user` (`UserData`, Optional): User information. `null` if `userId` is not specified.
    *   `session` (`String`): A session string to be used in the subsequent `finishAuthenticate` call.

**Potential Exceptions:**

*   `FssApiException`: Thrown if the API request fails.

### `FinishAuthenticateResponse` `finishAuthenticate` `(Fido2FinishAuthenticateParameter parameter, String sessionCookie) throws FssApiException`

Completes the authentication process.

**Arguments:**

*   `parameter` (`Fido2FinishAuthenticateParameter`): Parameters to complete the authentication.
    *   `requestResponse`: The response from the authenticator.
        *   `attestationResponse` (`String` or `PublicKeyCredential`): The assertion response from the authenticator. A JSON string or `PublicKeyCredential` object. Set the return value of `navigator.credentials.get()`.
*   `sessionCookie` (`String`): The session string obtained from `startAuthenticate`.

**Returns:**

*   `FinishAuthenticateResponse`: An object containing the authenticated credential data and user information.
    *   `credential` (`CredentialData`): The credential information used for authentication.
    *   `user` (`UserDataWithCredentialCount`): The authenticated user information.
    *   `signalAllAcceptedCredentialsOptions` (`SignalAllAcceptedCredentialsOptions`): Signal information for updating credential information on the client side. Use as an argument for calling `PublicKeyCredential.signalAllAcceptedCredentials()`.
    *   `signalCurrentUserDetailsOptions` (`SignalCurrentUserDetailsOptions`): Signal information for updating user information on the client side. Use as an argument for calling `PublicKeyCredential.signalCurrentUserDetails()`.

**Potential Exceptions:**

*   `FssApiException`: Thrown if the API request fails.

### `GetCredentialResponse` `getCredential` `(String userId, String credentialId)`
### `GetCredentialResponse` `getCredential` `(String userId, String credentialId, boolean withDisabledUser)`
### `GetCredentialResponse` `getCredential` `(String userId, String credentialId, boolean withDisabledUser, boolean withDisabledCredential)`

Retrieves credential information that matches the specified user ID and credential ID.

**Arguments:**

*   `userId` (`String`): User ID.
*   `credentialId` (`String`): Credential ID.
*   `withDisabledUser` (`boolean`, Optional, Default: `false`): Whether to include disabled users.
*   `withDisabledCredential` (`boolean`, Optional, Default: `false`): Whether to include disabled credentials.

**Returns:**

*   `GetCredentialResponse`: An object containing user information and credential information.

**Potential Exceptions:**

*   `FssApiException`: Thrown if the API request fails.

### `UpdateCredentialResponse` `updateCredential` `(CredentialDataUpdateParameter parameter)`
### `UpdateCredentialResponse` `updateCredential` `(CredentialDataUpdateParameter parameter, boolean withUpdatedCheck)`

Updates the information of the specified credential.

**Arguments:**

*   `parameter` (`CredentialDataUpdateParameter`): The information of the credential to update.
    *   `userId` (`String`): User ID.
    *   `credentialId` (`String`): Credential ID.
    *   `credentialName` (`String`): Credential name.
    *   `credentialAttributes` (`Map<String, Object>` or `String`, Optional): Additional credential attributes.
    *   `disabled` (`boolean`): Whether to disable the credential.
    *   `updated` (`OffsetDateTime`, Optional): The last updated date and time. If `withUpdatedCheck` is `true`, the update will fail if this value does not match the `updated` value in the database.
*   `withUpdatedCheck` (`boolean`, Optional, Default: `false`): Whether to check the last updated date and time.

**Returns:**

*   `UpdateCredentialResponse`: An object containing the updated user information and credential information.

**Potential Exceptions:**

*   `FssApiException`: Thrown if the API request fails.

### `DeleteCredentialResponse` `deleteCredential` `(String userId, String credentialId)`

Deletes the credential that matches the specified user ID and credential ID.

**Arguments:**

*   `userId` (`String`): User ID.
*   `credentialId` (`String`): Credential ID.

**Returns:**

*   `DeleteCredentialResponse`: An object containing the deleted user information and credential information.
    *   `user` (`UserDataWithCredentialCount`): User information.
    *   `credential` (`CredentialData`): The deleted credential information.
    *   `signalUnknownCredentialOptions` (`SignalUnknownCredentialOptions`): Signal information for deleting credential information on the client side. Use as an argument for calling `PublicKeyCredential.signalUnknownCredential()`.

**Potential Exceptions:**

*   `FssApiException`: Thrown if the API request fails.

---

## Data Structures

### `UserDataWithCredentialCount`

An object representing user information.

*   `rpId` (`String`): RP ID.
*   `userId` (`String`): User ID.
*   `userName` (`String`): Username.
*   `displayName` (`String`): Display name.
*   `userAttributes` (`Map<String, Object>`): Additional user attributes. Can be freely set by the RP.
*   `disabled` (`boolean`): Whether the user is disabled.
*   `registered` (`OffsetDateTime`): Registration date and time.
*   `updated` (`OffsetDateTime`): Last updated date and time.
*   `enabledCredentialCount` (`int`): The number of enabled credentials.
*   `credentialCount` (`int`): The total number of credentials.

### `CredentialData`

An object representing credential information.

*   `rpId` (`String`): RP ID.
*   `userId` (`String`): User ID.
*   `credentialId` (`String`): Credential ID.
*   `credentialName` (`String`, Optional): Credential name.
*   `credentialAttributes` (`Map<String, Object>`, Optional): Additional credential attributes. Can be freely set by the RP.
*   `format` (`String`): The format of the credential.
*   `userPresence` (`boolean`): Whether user presence was confirmed.
*   `userVerification` (`boolean`): Whether user verification was successful.
*   `backupEligibility` (`boolean`): Whether it is eligible for backup (synced passkey).
*   `backupState` (`boolean`): Whether it is backed up (synced).
*   `attestedCredentialData` (`boolean`): Whether Attested Credential Data is included.
*   `extensionData` (`boolean`): Whether extension data is included.
*   `aaguid` (`String`, Optional): AAGUID.
*   `aaguidModelName` (`String`, Optional): The model name identified from the AAGUID.
*   `publicKey` (`String`): Public key.
*   `transportsRaw` (`String`, Optional): JSON string of transports.
*   `transportsBle` (`Boolean`, Optional): Whether BLE is supported.
*   `transportsHybrid` (`Boolean`, Optional): Whether Hybrid is supported.
*   `transportsInternal` (`Boolean`, Optional): Whether Internal is supported.
*   `transportsNfc` (`Boolean`, Optional): Whether NFC is supported.
*   `transportsUsb` (`Boolean`, Optional): Whether USB is supported.
*   `discoverableCredential` (`Boolean`, Optional): Whether it is a Discoverable Credential.
*   `enterpriseAttestation` (`boolean`): Whether it is an Enterprise Attestation.
*   `vendorId` (`String`, Optional): Vendor ID. Set to an ID indicating the vendor if Enterprise Attestation is correctly parsed on the FIDO2 Server side.
*   `authenticatorId` (`String`, Optional): Authenticator ID. Set to the key's serial number, etc., if Enterprise Attestation is correctly parsed on the FIDO2 Server side.
*   `attestationObject` (`String`): Attestation object.
*   `authenticatorAttachment` (`String`, Optional): The attachment type of the authenticator.
*   `credentialType` (`String`): The type of the credential.
*   `clientDataJson` (`String`): ClientDataJSON string.
*   `clientDataJsonRaw` (`String`): Binary value representation of ClientDataJSON (Base64URL).
*   `lastAuthenticated` (`OffsetDateTime`, Optional): Last authenticated date and time.
*   `lastSignCounter` (`Long`, Optional): Last sign counter.
*   `disabled` (`boolean`): Whether the credential is disabled.
*   `registered` (`OffsetDateTime`): Registration date and time.
*   `updated` (`OffsetDateTime`): Last updated date and time.

### `Fido2CredentialNameParameter`

A parameter for specifying the credential name. It has the following properties:

*   `name` (`String`): The base credential name.
*   `nameIfModelNameExists` (`String`, Optional): The credential name to be used if the authenticator's model name is available.
*   `nameIfEnterpriseAttestationExists` (`String`, Optional): The credential name to be used if Enterprise Attestation is available.

**Name Determination Logic:**

1.  If Enterprise Attestation is detected during registration, `nameIfEnterpriseAttestationExists` is used.
2.  If Attestation is detected during registration and a model name is available, `nameIfModelNameExists` is used.
3.  In all other cases, or if the corresponding property is omitted, `name` is used.

**Placeholders:**

The credential name can include the following placeholders, which will be replaced with actual values upon registration:

*   `$$`: Replaced with `$`.
*   `$modelName`: The product model name derived from the AAGUID.
*   `$authenticatorId`: The authenticator ID (e.g., serial number) derived from the certificate in the case of Enterprise Attestation.

### `FssApiException`

An exception class representing an API error. It inherits from the `RuntimeException` class.

*   `message` (`String`): The error message.
*   `appStatus` (`FssApiResultStatus`): The processing result status of the API.
*   `appSubStatus` (`JsonNode`): The processing result sub-status of the API.
    *   `errorCode` (`Fido2ErrorStatus`, Optional): FIDO2-related error code.
    *   `errorMessage` (`String`, Optional): FIDO2-related error message.
    *   `info` (`JsonNode`, Optional): Additional information.

---

## Enums

### `FssApiAuthType`
The type of API authentication.
* `NONCE_SIGN_AUTH`
* `DATETIME_SIGN_AUTH`
* `ACCESS_KEY_AUTH`

### `AttestationConveyancePreference`
Attestation conveyance preference.
* `NONE`
* `INDIRECT`
* `DIRECT`
* `ENTERPRISE`

### `UserVerificationRequirement`
User verification requirement.
* `REQUIRED`
* `PREFERRED`
* `DISCOURAGED`
