package com.example.wwo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Request {
	private Map<String, String> requestParams;
	private String baseUrl;
	private String hostUrl;
	private String scheme;

	public Request(Map<String, String> requestParams, String baseUrl,
			String hostUrl, String scheme) {
		this.requestParams = requestParams;
		this.baseUrl = baseUrl;
		this.hostUrl = hostUrl;
		this.scheme = scheme;
	}

	public InputStream run() throws ClientProtocolException, IOException {

		RequestBuilder requestBuilder = RequestBuilder.get().setUri(baseUrl);
		for (String key : requestParams.keySet()) {
			requestBuilder.addParameter(key, requestParams.get(key).toString());
		}
		HttpRequest request = requestBuilder.build();
		HttpHost host = new HttpHost(hostUrl, -1, scheme);
		CloseableHttpClient client = HttpClients.custom()
				.disableCookieManagement().build();

		CloseableHttpResponse httpResponse = client.execute(host, request);
		return httpResponse.getEntity().getContent();
	}

}
