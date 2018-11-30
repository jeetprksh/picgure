package com.picgure.api.manager;

import java.io.InputStream;

/*
 * @author Jeet Prakash
 * */
public interface HttpClientService {
	
	public InputStream getInputStreamForResource(String url) throws Exception;

}
