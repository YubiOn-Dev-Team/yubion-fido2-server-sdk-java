package jp.co.sgk.yubion.fss.sdk.result;

/**
 * A wrapper that holds the result of an API call that initiates a session,
 * including the response data and the session cookie.
 *
 * @param <T> The type of the response data.
 */
public class ApiSessionResponse<T> {

	private final T data;
	private final String sessionCookie;

	public ApiSessionResponse(T data, String sessionCookie) {
		this.data = data;
		this.sessionCookie = sessionCookie;
	}

	/**
	 * Gets the data returned from the API.
	 * @return The response data.
	 */
	public T getData() {
		return data;
	}

	/**
	 * Gets the session cookie returned from the server.
	 * This should be passed to the subsequent 'finish' method.
	 * @return The session cookie string.
	 */
	public String getSessionCookie() {
		return sessionCookie;
	}
}
