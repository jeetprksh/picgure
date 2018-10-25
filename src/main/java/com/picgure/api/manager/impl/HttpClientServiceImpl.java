package com.picgure.api.manager.impl;

import com.picgure.api.manager.HttpClientService;
import com.picgure.api.util.Constants;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

@Component
public class HttpClientServiceImpl implements HttpClientService {

	private static Logger logger = Logger.getLogger(HttpClientServiceImpl.class.getName());

	@Override
	public InputStream getInputStreamForResource(String url) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(Constants.HTTP_CONNECTION_TIMEOUT);
		connection.setReadTimeout(Constants.HTTP_CONNECTION_READ_TIMEOUT);

		int responseCode = connection.getResponseCode();
		if (responseCode == 200) {
			return connection.getInputStream();
		} else {
			logger.severe("Status Code not OK for :: " + url);
			return null;
		}
	}
}
