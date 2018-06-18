package com.jeetprksh.imgur.downloader.api.manager;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.client.ClientProtocolException;

public interface HttpClient {
	
	public InputStream getInputStreamForResource(String url) throws ClientProtocolException, IOException;

}
