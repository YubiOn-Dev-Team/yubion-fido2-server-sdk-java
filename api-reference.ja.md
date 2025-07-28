# YubiOn FIDO2 Server SDK for Java APIリファレンス

このドキュメントは、YubiOn FIDO2 Server SDK for Java のAPIリファレンスです。  
[English](api-reference.md) | [Japanese](api-reference.ja.md)

## `jp.co.sgk.yubion.fss.sdk.YubiOnFssSdk` クラス

FIDO2サーバーとの通信を行うためのメインクラスです。

### コンストラクタ

#### `new YubiOnFssSdk(FssSdkConfig config)`

`YubiOnFssSdk` の新しいインスタンスを初期化します。

**引数:**

*   `config` (`FssSdkConfig`): FIDO2サーバーへの接続設定。
    *   `endpoint` (`String`, 省略可能, デフォルト: `"https://fss-app.yubion.com/api/"`): APIのエンドポイントURL。
    *   `rpId` (`String`): このSDKを使用するRP（リライングパーティ）のID。
    *   `apiAuthId` (`String`): API認証に使用するID。
    *   `apiAuthType` (`FssApiAuthType`): API認証のタイプ。
    *   `secretKey` (`String`): API認証に使用するシークレットキー。
    *   `agent` (`String`, 省略可能, デフォルト: `"yubion-fido2-server-sdk-java"`): APIリクエスト時に使用されるユーザーエージェント文字列。

---

## ユーザー管理

### `GetUserResponse` `getUser` `(String userId)`
### `GetUserResponse` `getUser` `(String userId, boolean withDisabledUser)`
### `GetUserResponse` `getUser` `(String userId, boolean withDisabledUser, boolean withDisabledCredential)`

指定されたIDのユーザー情報を取得します。

**引数:**

*   `userId` (`String`): 取得するユーザーのID。
*   `withDisabledUser` (`boolean`, 省略可能, デフォルト: `false`): 無効化されたユーザーを含めるかどうか。
*   `withDisabledCredential` (`boolean`, 省略可能, デフォルト: `false`): 無効化されたクレデンシャルを含めるかどうか。

**戻り値:**

*   `GetUserResponse`: ユーザー情報とクレデンシャル情報の配列を含むオブジェクト。
    *   `user` (`UserDataWithCredentialCount`): ユーザー情報。
    *   `credentials` (`List<CredentialData>`): ユーザーに紐づくクレデンシャル情報の配列。
    *   `signalCurrentUserDetailsOptions` (`SignalCurrentUserDetailsOptions`): クライアント側でユーザー情報を更新するためのシグナル情報。`PublicKeyCredential.signalCurrentUserDetails()`を呼び出すための引数として用います。

**発生する可能性のある例外:**

*   `FssApiException`: APIリクエストに失敗した場合に発生します。

### `GetUsersResponse` `getAllUsers` `()`
### `GetUsersResponse` `getAllUsers` `(boolean withDisabledUser)`

すべてのユーザー情報を取得します。

**引数:**

*   `withDisabledUser` (`boolean`, 省略可能, デフォルト: `false`): 無効化されたユーザーを含めるかどうか。

**戻り値:**

*   `GetUsersResponse`: ユーザー情報の配列を含むオブジェクト。
    *   `users` (`List<UserDataWithCredentialCount>`): ユーザー情報の配列。

**発生する可能性のある例外:**

*   `FssApiException`: APIリクエストに失敗した場合に発生します。

### `GetUsersResponse` `getUsersByUserName` `(String userName)`
### `GetUsersResponse` `getUsersByUserName` `(String userName, boolean withDisabledUser)`

指定されたユーザー名のユーザー情報を取得します。

**引数:**

*   `userName` (`String`): 検索するユーザー名。
*   `withDisabledUser` (`boolean`, 省略可能, デフォルト: `false`): 無効化されたユーザーを含めるかどうか。

**戻り値:**

*   `GetUsersResponse`: ユーザー情報の配列を含むオブジェクト。
    *   `users` (`List<UserDataWithCredentialCount>`): ユーザー情報の配列。

**発生する可能性のある例外:**

*   `FssApiException`: APIリクエストに失敗した場合に発生します。

### `RegisterUserResponse` `registerUser` `(UserDataRegisterParameter parameter)`

新しいユーザーを登録します。

**引数:**

*   `parameter` (`UserDataRegisterParameter`): 登録するユーザー情報。
    *   `userId` (`String`): ユーザーID。
    *   `userName` (`String`): ユーザー名。
    *   `displayName` (`String`, 省略可能): 表示名。
    *   `userAttributes` (`Map<String, Object>` または `String`, 省略可能): ユーザーの追加属性。JSONオブジェクトまたはJSON文字列で指定します。
    *   `disabled` (`boolean`): ユーザーを無効として登録するかどうか。

**戻り値:**

*   `RegisterUserResponse`: 登録されたユーザー情報を含むオブジェクト。
    *   `user` (`UserDataWithCredentialCount`): 登録されたユーザー情報。

**発生する可能性のある例外:**

*   `FssApiException`: APIリクエストに失敗した場合に発生します。

### `UpdateUserResponse` `updateUser` `(UserDataUpdateParameter parameter)`
### `UpdateUserResponse` `updateUser` `(UserDataUpdateParameter parameter, boolean withUpdatedCheck)`

指定されたユーザーの情報を更新します。

**引数:**

*   `parameter` (`UserDataUpdateParameter`): 更新するユーザー情報。
    *   `userId` (`String`): ユーザーID。
    *   `userName` (`String`): ユーザー名。
    *   `displayName` (`String`, 省略可能): 表示名。
    *   `userAttributes` (`Map<String, Object>` または `String`, 省略可能): ユーザーの追加属性。JSONオブジェクトまたはJSON文字列で指定します。
    *   `disabled` (`boolean`): ユーザーを無効にするかどうか。
    *   `updated` (`OffsetDateTime`, 省略可能): 最終更新日時。`withUpdatedCheck`が`true`の場合、この値とDB上の`updated`が一致しない場合は更新に失敗します。
*   `withUpdatedCheck` (`boolean`, 省略可能, デフォルト: `false`): 最終更新日時をチェックするかどうか。

**戻り値:**

*   `UpdateUserResponse`: 更新されたユーザー情報を含むオブジェクト。
    *   `user` (`UserDataWithCredentialCount`): 更新されたユーザー情報。
    *   `signalCurrentUserDetailsOptions` (`SignalCurrentUserDetailsOptions`): クライアント側でユーザー情報を更新するためのシグナル情報。`PublicKeyCredential.signalCurrentUserDetails()`を呼び出すための引数として用います。

**発生する可能性のある例外:**

*   `FssApiException`: APIリクエストに失敗した場合に発生します。

### `DeleteUserResponse` `deleteUser` `(String userId)`

指定されたIDのユーザーを削除します。

**引数:**

*   `userId` (`String`): 削除するユーザーのID。

**戻り値:**

*   `DeleteUserResponse`: 削除されたユーザー情報と関連情報を含むオブジェクト。
    *   `user` (`UserDataWithCredentialCount`): 削除されたユーザー情報。
    *   `credentials` (`List<CredentialData>`): 削除されたユーザーに紐づいていたクレデンシャル情報の配列。
    *   `signalAllAcceptedCredentialsOptions` (`SignalAllAcceptedCredentialsOptions`): クライアント側でクレデンシャル情報を削除するためのシグナル情報。`PublicKeyCredential.signalAllAcceptedCredentials()`を呼び出すための引数として用います。

**発生する可能性のある例外:**

*   `FssApiException`: APIリクエストに失敗した場合に発生します。

---

## FIDO2/WebAuthn クレデンシャル管理

### `ApiSessionResponse<StartRegisterCredentialResponse>` `startRegisterCredential` `(Fido2StartRegisterParameter parameter) throws FssApiException`

クレデンシャル登録プロセスを開始します。

**引数:**

*   `parameter` (`Fido2StartRegisterParameter`): クレデンシャル登録開始のためのリクエストパラメータ。
    *   `creationOptionsBase` (`Fido2CreationOptionsBase`): WebAuthnの`PublicKeyCredentialCreationOptions`の基本部分。
        *   `authenticatorSelection` (`AuthenticatorSelectionCriteria`, 省略可能): 認証器の選択基準。
        *   `timeout` (`long`, 省略可能): タイムアウト時間（ミリ秒）。
        *   `hints` (`List<String>`, 省略可能): 認証方式をユーザーエージェント(ブラウザ等)に伝えるためのヒント。"security-key", "client-device", "hybrid"のいずれかの文字列の配列です。
        *   `attestation` (`AttestationConveyancePreference`, 省略可能): Attestationの要求設定。
        *   `extensions` (`Map<String, Object>`, 省略可能): 拡張機能データ。
    *   `user` (`UserDataRegisterParameter`): 登録するユーザーの情報。
        *   `userId` (`String`): ユーザーID。64バイト以内のバイナリデータをBase64URL形式(パディング無し)にエンコードして指定する必要があります。
        *   `userName` (`String`, 省略可能): ユーザー名。`options.createUserIfNotExists`と`options.updateUserIfExists`が共に`false`の場合は省略可能です。
        *   `displayName` (`String`, 省略可能): 表示名。`options.createUserIfNotExists`と`options.updateUserIfExists`が共に`false`の場合は省略可能です。
        *   `userAttributes` (`Map<String, Object>` または `String`, 省略可能): ユーザーの追加属性。`options.createUserIfNotExists`と`options.updateUserIfExists`が共に`false`の場合は省略可能です。
        *   `disabled` (`boolean`, 省略可能): ユーザーが無効かどうか。登録時は`false`に指定する事はできません。`options.createUserIfNotExists`と`options.updateUserIfExists`が共に`false`の場合は省略可能です。
    *   `options` (`Fido2RegisterOptions`, 省略可能): 登録プロセスの追加オプション。
        *   `createUserIfNotExists` (`boolean`, 省略可能): ユーザーが存在しない場合に新規作成するかどうか。
        *   `updateUserIfExists` (`boolean`, 省略可能): 既存のユーザーを更新するかどうか。
        *   `credentialName` (`Fido2CredentialNameParameter`, 省略可能): クレデンシャル名。詳細はデータ構造の`Fido2CredentialNameParameter`を参照してください。
        *   `credentialAttributes` (`Map<String, Object>` または `String`, 省略可能): クレデンシャルの追加属性。

**戻り値:**

*   `ApiSessionResponse<StartRegisterCredentialResponse>`: クレデンシャル登録のための作成オプションとユーザー情報、セッション文字列を含むオブジェクト。
    *   `response` (`StartRegisterCredentialResponse`):
        *   `creationOptions` (`PublicKeyCredentialCreationOptionsJSON`): WebAuthnの`navigator.credentials.create()`に渡すオプション。
        *   `user` (`UserDataWithCredentialCount`): ユーザー情報。
    *   `session` (`String`): 後続の`verifyRegisterCredential`や`finishRegisterCredential`で使用するセッション文字列。

**発生する可能性のある例外:**

*   `FssApiException`: APIリクエストに失敗した場合に発生します。

### `FinishRegisterCredentialResponse` `verifyRegisterCredential` `(Fido2FinishRegisterParameter parameter, String sessionCookie) throws FssApiException`

クレデンシャル登録パラメータを検証します。このメソッドは、実際の登録前に登録内容を解析し、RP（SDKを利用するプログラム）が確認できるように分かりやすい形式で提供します。RPは戻り値を確認し、問題がなければ`finishRegisterCredential`を呼び出します。

**引数:**

*   `parameter` (`Fido2FinishRegisterParameter`): クレデンシャル登録を完了するためのパラメータ。
    *   `createResponse`: 認証器からの応答。
        *   `attestationResponse` (`String` または `PublicKeyCredential`): 認証器からのAttestation応答。JSON文字列または`PublicKeyCredential`オブジェクト。`navigator.credentials.create()`の戻り値を設定します。
        *   `transports` (`String` または `List<String>`, 省略可能): 認証器がサポートするトランスポートプロトコル。トランスポートプロトコルを保存したい場合、`navigator.credentials.create()`の戻り値の`response`メンバーメソッドである`getTransports()`を呼び出し、その戻り値を`transports`メンバーに設定します。
    *   `options` (省略可能):
        *   `credentialName` (`Fido2CredentialNameParameter`, 省略可能): クレデンシャル名。詳細はデータ構造の`Fido2CredentialNameParameter`を参照してください。
*   `sessionCookie` (`String`): `startRegisterCredential`で取得したセッション文字列。

**戻り値:**

*   `FinishRegisterCredentialResponse`: 検証されたクレデンシャルデータとユーザー情報を含むオブジェクト。

**発生する可能性のある例外:**

*   `FssApiException`: APIリクエストに失敗した場合に発生します。

### `FinishRegisterCredentialResponse` `finishRegisterCredential` `(Fido2FinishRegisterParameter parameter, String sessionCookie) throws FssApiException`

クレデンシャル登録プロセスを完了し、クレデンシャルをユーザーアカウントに保存します。

**引数:**

*   `parameter` (`Fido2FinishRegisterParameter`): `verifyRegisterCredential`と同じ。
*   `sessionCookie` (`String`): `startRegisterCredential`で取得したセッション文字列。

**戻り値:**

*   `FinishRegisterCredentialResponse`: 登録されたクレデンシャルデータとユーザー情報を含むオブジェクト。

**発生する可能性のある例外:**

*   `FssApiException`: APIリクエストに失敗した場合に発生します。

### `ApiSessionResponse<StartAuthenticateResponse>` `startAuthenticate` `(Fido2StartAuthenticateParameter parameter) throws FssApiException`

認証プロセスを開始します。ユーザーが指定されていない場合は、Discoverable Credentialによる認証が行われます。

**引数:**

*   `parameter` (`Fido2StartAuthenticateParameter`): 認証開始のためのリクエストパラメータ。
    *   `requestOptionsBase` (`Fido2RequestOptionsBase`): WebAuthnの`PublicKeyCredentialRequestOptions`の基本部分。
        *   `timeout` (`long`, 省略可能): タイムアウト時間（ミリ秒）。
        *   `hints` (`List<String>`, 省略可能): 認証方式をユーザーエージェント(ブラウザ等)に伝えるためのヒント。"security-key", "client-device", "hybrid"のいずれかの文字列の配列です。
        *   `userVerification` (`UserVerificationRequirement`, 省略可能): ユーザー検証の要件。
        *   `extensions` (`Map<String, Object>`, 省略可能): 拡張機能データ。
    *   `userId` (`String`, 省略可能): ユーザーID。Discoverable Credentialを使用する場合は省略します。
    *   `options` (`Fido2AuthenticateOptions`, 省略可能): 認証プロセスの追加オプション。このオブジェクトには現在メンバーが定義されていませんが、将来の拡張のために予約されています。

**戻り値:**

*   `ApiSessionResponse<StartAuthenticateResponse>`: 認証のためのリクエストオプションとユーザー情報、セッション文字列を含むオブジェクト。
    *   `response` (`StartAuthenticateResponse`):
        *   `requestOptions` (`PublicKeyCredentialRequestOptionsJSON`): WebAuthnの`navigator.credentials.get()`に渡すオプション。
        *   `user` (`UserData`, 省略可能): ユーザー情報。`userId`が指定されていない場合は`null`。
    *   `session` (`String`): 後続の`finishAuthenticate`で使用するセッション文字列。

**発生する可能性のある例外:**

*   `FssApiException`: APIリクエストに失敗した場合に発生します。

### `FinishAuthenticateResponse` `finishAuthenticate` `(Fido2FinishAuthenticateParameter parameter, String sessionCookie) throws FssApiException`

認証プロセスを完了します。

**引数:**

*   `parameter` (`Fido2FinishAuthenticateParameter`): 認証を完了するためのパラメータ。
    *   `requestResponse`: 認証器からの応答。
        *   `attestationResponse` (`String` または `PublicKeyCredential`): 認証器からのAssertion応答。JSON文字列または`PublicKeyCredential`オブジェクト。`navigator.credentials.get()`の戻り値を設定します。
*   `sessionCookie` (`String`): `startAuthenticate`で取得したセッション文字列。

**戻り値:**

*   `FinishAuthenticateResponse`: 認証されたクレデンシャルデータとユーザー情報を含むオブジェクト。
    *   `credential` (`CredentialData`): 認証に使用されたクレデンシャル情報。
    *   `user` (`UserDataWithCredentialCount`): 認証されたユーザー情報。
    *   `signalAllAcceptedCredentialsOptions` (`SignalAllAcceptedCredentialsOptions`): クライアント側でクレデンシャル情報を更新するためのシグナル情報。`PublicKeyCredential.signalAllAcceptedCredentials()`を呼び出すための引数として用います。
    *   `signalCurrentUserDetailsOptions` (`SignalCurrentUserDetailsOptions`): クライアント側でユーザー情報を更新するためのシグナル情報。`PublicKeyCredential.signalCurrentUserDetails()`を呼び出すための引数として用います。

**発生する可能性のある例外:**

*   `FssApiException`: APIリクエストに失敗した場合に発生します。

### `GetCredentialResponse` `getCredential` `(String userId, String credentialId)`
### `GetCredentialResponse` `getCredential` `(String userId, String credentialId, boolean withDisabledUser)`
### `GetCredentialResponse` `getCredential` `(String userId, String credentialId, boolean withDisabledUser, boolean withDisabledCredential)`

指定されたユーザーIDとクレデンシャルIDに一致するクレデンシャル情報を取得します。

**引数:**

*   `userId` (`String`): ユーザーID。
*   `credentialId` (`String`): クレデンシャルID。
*   `withDisabledUser` (`boolean`, 省略可能, デフォルト: `false`): 無効化されたユーザーを含めるかどうか。
*   `withDisabledCredential` (`boolean`, 省略可能, デフォルト: `false`): 無効化されたクレデンシャルを含めるかどうか。

**戻り値:**

*   `GetCredentialResponse`: ユーザー情報とクレデンシャル情報を含むオブジェクト。

**発生する可能性のある例外:**

*   `FssApiException`: APIリクエストに失敗した場合に発生します。

### `UpdateCredentialResponse` `updateCredential` `(CredentialDataUpdateParameter parameter)`
### `UpdateCredentialResponse` `updateCredential` `(CredentialDataUpdateParameter parameter, boolean withUpdatedCheck)`

指定されたクレデンシャルの情報を更新します。

**引数:**

*   `parameter` (`CredentialDataUpdateParameter`): 更新するクレデンシャル情報。
    *   `userId` (`String`): ユーザーID。
    *   `credentialId` (`String`): クレデンシャルID。
    *   `credentialName` (`String`): クレデンシャル名。
    *   `credentialAttributes` (`Map<String, Object>` または `String`, 省略可能): クレデンシャルの追加属性。
    *   `disabled` (`boolean`): クレデンシャルを無効にするかどうか。
    *   `updated` (`OffsetDateTime`, 省略可能): 最終更新日時。`withUpdatedCheck`が`true`の場合、この値とDB上の`updated`が一致しない場合は更新に失敗します。
*   `withUpdatedCheck` (`boolean`, 省略可能, デフォルト: `false`): 最終更新日時をチェックするかどうか。

**戻り値:**

*   `UpdateCredentialResponse`: 更新されたユーザー情報とクレデンシャル情報を含むオブジェクト。

**発生する可能性のある例外:**

*   `FssApiException`: APIリクエストに失敗した場合に発生します。

### `DeleteCredentialResponse` `deleteCredential` `(String userId, String credentialId)`

指定されたユーザーIDとクレデンシャルIDに一致するクレデンシャルを削除します。

**引数:**

*   `userId` (`String`): ユーザーID。
*   `credentialId` (`String`): クレデンシャルID。

**戻り値:**

*   `DeleteCredentialResponse`: 削除されたユーザー情報とクレデンシャル情報を含むオブジェクト。
    *   `user` (`UserDataWithCredentialCount`): ユーザー情報。
    *   `credential` (`CredentialData`): 削除されたクレデンシャル情報。
    *   `signalUnknownCredentialOptions` (`SignalUnknownCredentialOptions`): クライアント側でクレデンシャル情報を削除するためのシグナル情報。`PublicKeyCredential.signalUnknownCredential()`を呼び出すための引数として用います。

**発生する可能性のある例外:**

*   `FssApiException`: APIリクエストに失敗した場合に発生します。

---

## データ構造

### `UserDataWithCredentialCount`

ユーザー情報を表すオブジェクト。

*   `rpId` (`String`): RPのID。
*   `userId` (`String`): ユーザーID。
*   `userName` (`String`): ユーザー名。
*   `displayName` (`String`): 表示名。
*   `userAttributes` (`Map<String, Object>`): ユーザーの追加属性。RPが自由に設定する事が可能です。
*   `disabled` (`boolean`): ユーザーが無効かどうか。
*   `registered` (`OffsetDateTime`): 登録日時。
*   `updated` (`OffsetDateTime`): 最終更新日時。
*   `enabledCredentialCount` (`int`): 有効なクレデンシャルの数。
*   `credentialCount` (`int`): すべてのクレデンシャルの数。

### `CredentialData`

クレデンシャル情報を表すオブジェクト。

*   `rpId` (`String`): RPのID。
*   `userId` (`String`): ユーザーID。
*   `credentialId` (`String`): クレデンシャルID。
*   `credentialName` (`String`, 省略可能): クレデンシャル名。
*   `credentialAttributes` (`Map<String, Object>`, 省略可能): クレデンシャルの追加属性。RPが自由に設定する事が可能です。
*   `format` (`String`): クレデンシャルのフォーマット。
*   `userPresence` (`boolean`): ユーザープレゼンスが確認されたかどうか。
*   `userVerification` (`boolean`): ユーザー検証が成功したかどうか。
*   `backupEligibility` (`boolean`): バックアップ対象（同期パスキー）かどうか。
*   `backupState` (`boolean`): バックアップ（同期）されているかどうか。
*   `attestedCredentialData` (`boolean`): Attested Credential Dataが含まれているかどうか。
*   `extensionData` (`boolean`): 拡張機能データが含まれているかどうか。
*   `aaguid` (`String`, 省略可能): AAGUID。
*   `aaguidModelName` (`String`, 省略可能): AAGUIDから特定されたモデル名。
*   `publicKey` (`String`): 公開鍵。
*   `transportsRaw` (`String`, 省略可能): トランスポートのJSON文字列。
*   `transportsBle` (`Boolean`, 省略可能): BLEをサポートするかどうか。
*   `transportsHybrid` (`Boolean`, 省略可能): Hybridをサポートするかどうか。
*   `transportsInternal` (`Boolean`, 省略可能): Internalをサポートするかどうか。
*   `transportsNfc` (`Boolean`, 省略可能): NFCをサポートするかどうか。
*   `transportsUsb` (`Boolean`, 省略可能): USBをサポートするかどうか。
*   `discoverableCredential` (`Boolean`, 省略可能): Discoverable Credentialかどうか。
*   `enterpriseAttestation` (`boolean`): Enterprise Attestationかどうか。
*   `vendorId` (`String`, 省略可能): ベンダーID。EnterpriseAttestationをFIDO2Server側で正しく解析出来た場合に、ベンダーを示すIDが設定されます。
*   `authenticatorId` (`String`, 省略可能): 認証器ID。EnterpriseAttestationをFIDO2Server側で正しく解析出来た場合に、キーのシリアル番号などが設定されます。
*   `attestationObject` (`String`): Attestationオブジェクト。
*   `authenticatorAttachment` (`String`, 省略可能): 認証器のアタッチメントタイプ。
*   `credentialType` (`String`): クレデンシャルのタイプ。
*   `clientDataJson` (`String`): ClientDataJSON文字列。
*   `clientDataJsonRaw` (`String`): ClientDataJSONのバイナリ値表現(Base64URL)。
*   `lastAuthenticated` (`OffsetDateTime`, 省略可能): 最終認証日時。
*   `lastSignCounter` (`Long`, 省略可能): 最終署名カウンター。
*   `disabled` (`boolean`): クレデンシャルが無効かどうか。
*   `registered` (`OffsetDateTime`): 登録日時。
*   `updated` (`OffsetDateTime`): 最終更新日時。

### `Fido2CredentialNameParameter`

クレデンシャル名を指定するためのパラメータです。以下のプロパティを持ちます。

*   `name` (`String`): 基本となるクレデンシャル名。
*   `nameIfModelNameExists` (`String`, 省略可能): 認証器のモデル名が取得できた場合に使用されるクレデンシャル名。
*   `nameIfEnterpriseAttestationExists` (`String`, 省略可能): Enterprise Attestationが利用可能な場合に使用されるクレデンシャル名。

**名前の決定ロジック:**

1.  登録時にEnterprise Attestationが検出された場合、`nameIfEnterpriseAttestationExists` が使用されます。
2.  登録時にAttestationが検出され、モデル名が取得できた場合、`nameIfModelNameExists` が使用されます。
3.  上記以外の場合、または対応するプロパティが省略されている場合は `name` が使用されます。

**プレースホルダー:**

クレデンシャル名には以下のプレースホルダーを含めることができ、登録時に実際の値に置換されます。

*   `$$`: `$`に置換されます。
*   `$modelName`: AAGUIDから導出された製品モデル名。
*   `$authenticatorId`: Enterprise Attestationの場合に証明書から導出された認証器ID（シリアル番号など）。

### `FssApiException`

APIエラーを表す例外クラス。`RuntimeException`クラスを継承しています。

*   `message` (`String`): エラーメッセージ。
*   `appStatus` (`FssApiResultStatus`): APIの処理結果ステータス。
*   `appSubStatus` (`JsonNode`): APIの処理結果サブステータス。
    *   `errorCode` (`Fido2ErrorStatus`, 省略可能): FIDO2関連のエラーコード。
    *   `errorMessage` (`String`, 省略可能): FIDO2関連のエラーメッセージ。
    *   `info` (`JsonNode`, 省略可能): 追加情報。

---

## 列挙型

### `FssApiAuthType`
API認証のタイプ。
* `NONCE_SIGN_AUTH`
* `DATETIME_SIGN_AUTH`
* `ACCESS_KEY_AUTH`

### `AttestationConveyancePreference`
Attestationの要求設定。
* `NONE`
* `INDIRECT`
* `DIRECT`
* `ENTERPRISE`

### `UserVerificationRequirement`
ユーザー検証の要件。
* `REQUIRED`
* `PREFERRED`
* `DISCOURAGED`
