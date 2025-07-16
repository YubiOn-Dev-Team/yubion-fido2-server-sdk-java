# yubion-fido2-server-sdk-java

[English](README.md) | [Japanese](README.ja.md)

## yubion-fido2-server-sdk-javaとは
yubion-fido2-server-sdk-java は、YubiOn FIDO2Server Service（以下、YubiOn FSS）をJavaから利用するためのSDKライブラリです。Javaを用いたサーバーアプリケーションからFIDO2認証（Passkey）を簡単に利用するためのAPIを提供します。

## 使い方
利用前に、既にYubiOn FSSにカスタマー登録を行い、RP設定などが完了している必要があります。詳しくはYubiOn FIDO2Server Serviceのマニュアルをご覧ください。  
  
ご利用になるJavaプロジェクトに、YubiOn FSS SDKをインストールします。  
現在、mavenなどのパッケージ管理への登録は行われておりません。お手数ですが、プロジェクトをcloneした後、以下のコマンドでjarファイルを生成し、使用するJavaプロジェクトで参照してください。jarファイルはbuild/libsディレクトリ内に生成されます。
```
gradlew jar -x test
```
また、jar内にはSDKが使用する外部ライブラリは含まれないため、それらも別途参照する必要があります。必要な外部ライブラリについてはbuild.gradleをご参照ください。  
プロジェクトの都合などでmavenなどのパッケージ管理への登録が必要な方がおられましたら、お手数ですがお問い合わせください。  
  
FIDO2認証のみでなく、登録されているユーザーやクレデンシャルの管理などのAPIも準備されています。詳しくは[APIリファレンス](api-reference.ja.md)をご参照ください。

## テストケース実行 (SDK開発者向け)
テストケースを実行するには、/src/test/resources/environment.propertiesに以下の環境プロパティを設定しておく必要があります。  
|環境プロパティ名|内容|
|---------------|---|
| FSS_TEST_ENDPOINT | テストに使用するためのAPIエンドポイント（省略可）。 |
| FSS_TEST_RP_ID | テストに使用するためのRPID。このRPIDの各種情報（ユーザー・クレデンシャル）はテストケース実施時に破壊されるため、運用しているRPIDは絶対に用いないでください。 |
| FSS_TEST_NONCE_API_AUTH_ID | Nonce認証の動作を確認するためのAPI認証ID |
| FSS_TEST_NONCE_CORRECT_API_SECRET_KEY | Nonce認証の正常動作を確認するための正しいAPIシークレットキー |
| FSS_TEST_NONCE_INCORRECT_API_SECRET_KEY | Nonce認証の失敗動作を確認するための間違ったAPIシークレットキー |
| FSS_TEST_DATETIME_API_AUTH_ID | 日時認証の動作を確認するためのAPI認証ID |
| FSS_TEST_DATETIME_CORRECT_API_SECRET_KEY | 日時認証の正常動作を確認するための正しいAPIシークレットキー |
| FSS_TEST_DATETIME_INCORRECT_API_SECRET_KEY | 日時認証の失敗動作を確認するための間違ったAPIシークレットキー |
| FSS_TEST_ACCESSKEY_API_AUTH_ID | アクセスキー認証の動作を確認するためのAPI認証ID |
| FSS_TEST_ACCESSKEY_CORRECT_API_SECRET_KEY | アクセスキー認証の正常動作を確認するための正しいAPIシークレットキー |
| FSS_TEST_ACCESSKEY_INCORRECT_API_SECRET_KEY | アクセスキー認証の失敗動作を確認するための間違ったAPIシークレットキー |
| FSS_TEST_RP_ID_FOR_LICENSE_TEST | ライセンス異常時動作の検証に使用するためのRPID。無料登録したカスタマーで作成したRPIDを割り当ててください。このRPIDの各種情報（ユーザー・クレデンシャル）はテストケース実施時に破壊されるため、運用しているRPIDは絶対に用いないでください。 |
| FSS_TEST_API_AUTH_ID_FOR_LICENSE_TEST | ライセンス異常時動作確認に用いるNonce認証のAPI認証ID |
| FSS_TEST_API_SECRET_KEY_FOR_LICENSE_TEST | ライセンス異常時動作確認に用いるNonce認証のAPIシークレットキー |
