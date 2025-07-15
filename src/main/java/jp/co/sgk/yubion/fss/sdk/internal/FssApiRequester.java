package jp.co.sgk.yubion.fss.sdk.internal;

import jp.co.sgk.yubion.fss.sdk.result.FssApiException;
import java.io.IOException;

/**
 * Interface for making requests to the FSS API.
 */
public interface FssApiRequester {

    /**
     * Makes a request to the API and returns the response data.
     *
     * @param path           The API path.
     * @param param          The request parameter object.
     * @param responseType   The class of the expected response data.
     * @param <TResponse>    The type of the response data.
     * @return The response data.
     * @throws FssApiException   If the API returns an error.
     */
    <TResponse> TResponse request(
	    String path,
	    Object param,
	    Class<TResponse> responseType) throws FssApiException;

    /**
     * Makes a request to the API and returns the response data along with any cookies.
     *
     * @param path           The API path.
     * @param param          The request parameter object.
     * @param responseType   The class of the expected response data.
     * @param withAuth       Whether to include authentication headers.
     * @param cookie         The cookie string to send with the request.
     * @param <TResponse>    The type of the response data.
     * @return A wrapper object containing the response data and cookie.
     * @throws IOException   If a communication error occurs.
     * @throws FssApiException   If the API returns an error.
     */
    <TResponse> FssApiCookieResponse<TResponse> requestWithCookieResponse(
	    String path,
	    Object param,
	    Class<TResponse> responseType,
	    boolean withAuth,
	    String cookie) throws FssApiException;
}
