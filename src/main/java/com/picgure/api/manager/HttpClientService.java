package com.picgure.api.manager;

import java.io.InputStream;

/*
 * @author Jeet Prakash
 * */
public interface HttpClientService {

	public InputStream getInputStreamForUrl(String url) throws Exception;

}
