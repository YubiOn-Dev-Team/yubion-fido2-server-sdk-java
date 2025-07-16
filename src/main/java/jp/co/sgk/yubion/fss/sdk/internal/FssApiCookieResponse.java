package jp.co.sgk.yubion.fss.sdk.internal;

/**
 * A wrapper class that holds the API response and any associated cookies.
 * @param <T> The type of the response data.
 */
public class FssApiCookieResponse<T> {

	private final String cookie;
	private final T response;

	public FssApiCookieResponse(String cookie, T response) {
		this.cookie = cookie;
		this.response = response;
	}

	public String getCookie() {
		return cookie;
	}

	public T getResponse() {
		return response;
	}
}
