package com.picgure.api.manager;

import java.io.IOException;
import java.io.InputStream;

public interface HttpClientService {
	
	public InputStream getInputStreamForResource(String url) throws IOException;

}
