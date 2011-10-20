package com.jesper.music.controller;

import org.apache.http.*;
import com.force.sdk.oauth.context.*;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.IOException;
import org.apache.http.protocol.HttpContext;

/**
 * 
 * This class instantiates a singleton HTTP client, which we'll use to perform the REST actions. 
 * It grabs the instance URL as well as the access token from the SecurityContext, where the OAuth library
 * puts them after a successful authentication.
 * 
 * @author Jon Mountjoy
 */
public class HTTPClientFactory {

	private DefaultHttpClient httpClient = null;

	// Returns the instance URL to which all REST requests should be directed
	public String getInstanceURL() {
		SecurityContext sc = ForceSecurityContextHolder.get();
		return sc.getEndPoint().substring(0, sc.getEndPoint().indexOf("/services"));
	}

	// Returns an HTTP client, pre-configured with the OAuth access token
	public DefaultHttpClient getHttpClient() {
		if (httpClient == null) {
			final SecurityContext sc = ForceSecurityContextHolder.get();
			httpClient = new DefaultHttpClient();
			httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
				public void process(HttpRequest request, HttpContext context)
						throws HttpException, IOException {
					request.setHeader("Authorization",
							"OAuth " + sc.getSessionId());
					request.setHeader("Content-Type", "application/json");
				}
			});
		}
		return httpClient;
	}

}
