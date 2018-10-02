package com.picgure.api.manager.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.logging.Logger;

import com.picgure.api.util.Setting;
import com.picgure.persistence.dao.PicgureSettingRepository;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.picgure.api.manager.FileService;
import com.picgure.api.util.Constants;

@Component
public class FileServiceImpl implements FileService {

	private Logger logger = Logger.getLogger(FileServiceImpl.class.getName());

	@Autowired
	private PicgureSettingRepository settingsRepo;
	
	/**
	 * Function to save the Imgur Object, from its InputStream , as a file with appropriate folder structure.
	 * Returns Boolean to tell the caller whether the object is saved or not.
	 * 
	 * @param subredditName
	 * @param fileName
	 * @param is
	 * @return
	 */
	@Override
	public boolean saveImgurObjectAsFile(String subredditName, String fileName, InputStream is) {

		String destFolderUrl = this.getBaseDirectory() + Constants.FILE_SEPERATOR + subredditName;
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
			logger.info("File saved " + destFileUrl);
		} catch (Exception e) {
			isSaved = false;
			this.logger.severe("Error occurred in saving file " + destFileUrl);
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

	@Override
	public File defaultImageStoreDirectory() {
		String pathToHome = System.getProperty("user.home");
		String pathToPicgureRoot = pathToHome + Constants.FILE_SEPERATOR + Constants.DEFAULT_ROOT_DIR_NAME;
		File imageStore = new File(pathToPicgureRoot);
		return imageStore;
	}

	@Override
	public void createImageStoreDirectory(File imageStore) {
		if (!imageStore.exists()){
		    logger.info("Creating the image store at " + imageStore.getAbsolutePath());
		    imageStore.mkdir();
        }
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
		int i = 1;
		
		do {
			String uniqueDestFileName = destFileName + "[" + i + "]";
			uniqueDestFile = new File(destFileParentDir + 
					Constants.FILE_SEPERATOR + uniqueDestFileName + "." + destFileExt);
			i++;
		} while (uniqueDestFile.exists());
		
		return uniqueDestFile;
	}

	private String getBaseDirectory() {
		String baseDir = settingsRepo.findByName(Setting.ImageStore.toString()).getValue();
		return baseDir == null ? Constants.DEFAULT_DOWNLOAD_DIR : baseDir;
	}

}
