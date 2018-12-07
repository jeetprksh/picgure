package com.picgure.api.manager.impl;

import com.picgure.api.manager.HttpClientService;
import com.picgure.logging.PicgureLogger;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

/*
 * @author Jeet Prakash
 * */
public class HttpClientServiceImpl implements HttpClientService {

	private static Logger logger = PicgureLogger.getLogger(HttpClientServiceImpl.class);
	private static final int HTTP_CONNECTION_TIMEOUT = 60_000;
	private static final int HTTP_CONNECTION_READ_TIMEOUT = 60_000;

	@Override
	public InputStream getInputStreamForUrl(String url) throws Exception {
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(HTTP_CONNECTION_TIMEOUT);
		connection.setReadTimeout(HTTP_CONNECTION_READ_TIMEOUT);

		logger.info("Downloading object " + url);
		int responseCode = connection.getResponseCode();
		if (responseCode == 200) {
			return connection.getInputStream();
		} else {
			throw new Exception("Status Code not OK for " + url);
		}
	}
}
