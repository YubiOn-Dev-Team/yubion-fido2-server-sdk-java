package jp.co.sgk.yubion.fss.sdk.internal;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jp.co.sgk.yubion.fss.sdk.config.FssSdkConfig;
import jp.co.sgk.yubion.fss.sdk.result.FssApiException;
import jp.co.sgk.yubion.fss.sdk.result.FssApiResultStatus;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.logging.HttpLoggingInterceptor;

public abstract class FssApiRequesterBase implements FssApiRequester {

	protected final FssSdkConfig config;
	protected final OkHttpClient httpClient;
	protected final ObjectMapper objectMapper;

	public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

	protected FssApiRequesterBase(FssSdkConfig config, boolean denyUnknownResponseMember, boolean allowBadSslForDebug) {
		this.config = config;
		var builder = new OkHttpClient.Builder();
		builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)); // For debug
		if(allowBadSslForDebug) {
			try {
				var context = SSLContext.getInstance("SSL");
				var tm = new X509TrustManager(){
					@Override
					public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) {
					}
					@Override
					public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) {
					}
					@Override
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return new java.security.cert.X509Certificate[0];
					}
				};

				context.init(null, new TrustManager[]{ tm }, null);
				builder.hostnameVerifier((hostname, session) -> true);
				builder.sslSocketFactory(context.getSocketFactory(), tm);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		this.httpClient = builder.build();
		this.objectMapper = new ObjectMapper();
		if(!denyUnknownResponseMember){
			this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		}
		this.objectMapper.registerModule(new JavaTimeModule());
	}

	@Override
	public <TResponse> TResponse request(String path, Object param, Class<TResponse> responseType) throws FssApiException {
		return requestWithCookieResponse(path, param, responseType, true, null).getResponse();
	}

	@Override
	public <TResponse> FssApiCookieResponse<TResponse> requestWithCookieResponse(
			String path, Object param, Class<TResponse> responseType, boolean withAuth, String cookie)
			throws FssApiException {
		byte[] bodyBytes;
		try {
			bodyBytes = objectMapper.writeValueAsBytes(param);
		} catch (IOException e) {
			throw new FssApiException("Failed to serialize request body.", FssApiResultStatus.UNEXPECTED_ERROR, null, e);
		}
		RequestBody body = RequestBody.create(bodyBytes, JSON);

		Request.Builder requestBuilder = new Request.Builder()
				.url(config.getEndpoint() + path)
				.post(body)
				.header("User-Agent", config.getAgent())
				.header("Accept", "application/json");

		if (cookie != null && !cookie.isEmpty()) {
			requestBuilder.header("Cookie", cookie);
		}

		try {
			Map<String, String> additionalHeaders = withAuth ? getAdditionalHeaders(bodyBytes) : Map.of();
			additionalHeaders.forEach(requestBuilder::header);
		} catch (Exception e) {
			throw new FssApiException("Failed to generate authentication headers.", FssApiResultStatus.UNEXPECTED_ERROR, null, e);
		}

		try (Response response = httpClient.newCall(requestBuilder.build()).execute()) {
			if (!response.isSuccessful()) {
				throw new FssApiException("Unexpected HTTP code: " + response.code(), FssApiResultStatus.COMMUNICATION_FAILED, null);
			}

			ResponseBody responseBody = response.body();
			if (responseBody == null) {
				throw new FssApiException("Response body is null.", FssApiResultStatus.COMMUNICATION_FAILED, null);
			}

			JavaType jsonResponseType = objectMapper.getTypeFactory().constructParametricType(FssApiJsonResponse.class, responseType);
			FssApiJsonResponse<TResponse> apiResponse = objectMapper.readValue(responseBody.string(), jsonResponseType);

			if (apiResponse.getAppStatus() != FssApiResultStatus.OK) {
				throw new FssApiException("API returned an error: " + apiResponse.getAppStatus(),
						apiResponse.getAppStatus(), apiResponse.getAppSubStatus());
			}

			String responseCookie = response.headers("Set-Cookie").stream()
					.map(c -> c.split(";", 2)[0])
					.collect(Collectors.joining("; "));

			return new FssApiCookieResponse<>(responseCookie, apiResponse.getData());

		} catch (IOException e) {
			throw new FssApiException("Communication failed.", FssApiResultStatus.COMMUNICATION_FAILED, null, e);
		}
	}

	protected abstract Map<String, String> getAdditionalHeaders(byte[] body) throws Exception;
}
