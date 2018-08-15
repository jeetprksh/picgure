package com.picgure.api.manager;

import java.io.InputStream;

public interface FileService {
	
	public boolean saveImgurObjectAsFile(String subredditFolderName, String fileName, InputStream is);
	
	/**
	 * Function to replace the illegal characters in file name for windows
	 * 
	 * @param name
	 * @return
	 */
	public String replaceIllegalCharsInFileName(String name);

}
