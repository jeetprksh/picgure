package com.picgure.api.manager.impl;

import com.picgure.api.manager.HttpClientService;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

@Component
public class HttpClientServiceImpl implements HttpClientService {

	private static Logger logger = Logger.getLogger(HttpClientServiceImpl.class.getName());

	@Override
	// TODO add the time out facility into HTTPClient
	public InputStream getInputStreamForResource(String url) throws ClientProtocolException, IOException {
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = httpClient.execute(httpGet);

		if (response.getStatusLine().getStatusCode() == 200) {
			return response.getEntity().getContent();
		} else {
			logger.severe("Status Code not OK for :: " + url);
			return null;
		}
		
	}
}
