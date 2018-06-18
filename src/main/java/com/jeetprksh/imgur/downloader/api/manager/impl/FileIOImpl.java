package com.jeetprksh.imgur.downloader.api.manager.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.logging.Logger;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import com.jeetprksh.imgur.downloader.api.manager.FileIO;
import com.jeetprksh.imgur.downloader.api.util.Constants;

@Component
public class FileIOImpl implements FileIO {
	
	Logger logger = Logger.getLogger(HttpClientImpl.class.getName());
	
	/**
	 * Function to save the Imgur Object, from its InputStream , as a file with appropriate folder structure.
	 * Returns Boolean to tell the caller whether the object is saved or not.
	 * 
	 * @param subredditFolderName
	 * @param fileName
	 * @param is
	 * @return
	 */
	@Override
	public boolean saveImgurObjectAsFile(String subredditFolderName, String fileName, InputStream is) {
		
		String destFolderUrl = Constants.DEFAULT_DOWNLOAD_DIR + Constants.FILE_SEPERATOR + subredditFolderName;
		String destFileUrl = destFolderUrl + Constants.FILE_SEPERATOR + fileName;
		FileOutputStream fileOutputStream;
		File destFolder = new File(destFolderUrl);
		File destFile = new File(destFileUrl);
		boolean isSaved = false;
		
		try {
			// Only create directory if it does not exists.
			if (!destFolder.exists()) {
				destFolder.mkdirs();
			}
			
			// If destination file already exists then give it a unique name
			if (destFile.exists()) {
				destFile = createUniqueFile(destFile);
			}
			destFile.createNewFile();
			fileOutputStream = new FileOutputStream(destFile);
			IOUtils.copy(is, fileOutputStream);
			fileOutputStream.close();
			is.close();
			isSaved = true;
		} catch (Exception e) {
			isSaved = false;
			this.logger.severe("Error occured in saving file ::  " + destFileUrl);
		}
		
		return isSaved;
	}

	/**
	 * Function to replace the illegal characters in file name for windows
	 * 
	 * @param name
	 * @return
	 */
	@Override
	public String replaceIllegalCharsInFileName(String name) {
		return name.replaceAll("[\\/:*?<>|\"]", "_");
	}
	
	/**
	 * Returns a file object which contains a unique file name.
	 * Client of this function first need to check if the file with that particular name exists.
	 * 
	 * @param destFile
	 * @return File
	 */
	public File createUniqueFile(File destFile) {
		String destFileNameWithExt = destFile.getName();
		String destFileParentDir = destFile.getParent();
		String destFileExt = FilenameUtils.getExtension(destFileNameWithExt);
		String destFileName = FilenameUtils.removeExtension(destFileNameWithExt);
		
		File uniqueDestFile;
		
		do {
			int i = 1;
			String uniqueDestFileName = destFileName + "[" + i + "]";
			uniqueDestFile = new File(destFileParentDir + 
					Constants.FILE_SEPERATOR + uniqueDestFileName + "." + destFileExt);
			i++;
		} while (uniqueDestFile.exists());
		
		return uniqueDestFile;
	}

}
