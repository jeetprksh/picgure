package com.picgure.api.manager;

import java.io.File;
import java.io.InputStream;

/*
 * @author Jeet Prakash
 * */

public interface FileService {
	
	public boolean saveImgurObjectAsFile(String subredditFolderName, String fileName, InputStream is);
	
	/**
	 * Function to replace the illegal characters in file name for windows
	 * 
	 * @param name
	 * @return
	 */
	public String replaceIllegalCharsInFileName(String name);

	public File defaultImageStoreDirectory();

	public void createImageStoreDirectory(File imageStore);

}
