package com.jeetprksh.imgur.downloader.api.manager.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import com.jeetprksh.imgur.downloader.api.manager.HttpClient;

@Component
public class HttpClientImpl implements HttpClient{

	@Override
	// TODO add the time out facility into HTTPClient
	public InputStream getInputStreamForResource(String url) throws ClientProtocolException, IOException {
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = httpClient.execute(httpGet);

		if (response.getStatusLine().getStatusCode() == 200){
			return response.getEntity().getContent();
		}else{
			System.out.println("Status Code not OK for :: " + url);
			return null;
		}
		
	}
}

class test{
	/**
	 * @param qw
	 */
	public static void main(String qw[]){
		/* Code to test the HttpClient
		  
		HttpClientMgmtImpl cm = new HttpClientMgmtImpl();
		
		try {
			cm.getInputStreamForResource("kzskjdfhvc");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		/* Code to test unique file creation
		FileMgmtImpl fm = new FileMgmtImpl();
		File f = new File("C:/Users/PSQ/downloader/test/test.txt");
		try{
			if (f.exists()){
				File uniqueFile = fm.createUniqueFileName(f);
				uniqueFile.createNewFile();
			}else{
				f.createNewFile();
			}
		}catch (Exception e){
			System.out.println("oops!!");
		}*/
		
		/*ImgurObjectAttrs obj1 = new ImgurObjectAttrs();
		ImgurObjectAttrs obj2 = new ImgurObjectAttrs();
		
		obj1.setHash("abc");
		obj1.setTitle("wrng");
		obj1.setSize(123);
		
		obj2.setHash("abcd");
		obj2.setTitle("wrng");
		obj2.setSize(121);
		
		System.out.println(obj1.equals(obj2));*/
		
	}
}