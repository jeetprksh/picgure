package com.picgure.api.manager.impl;

import com.picgure.api.manager.FileService;
import com.picgure.api.manager.file.naming.CreateFileStratedgy;
import com.picgure.api.manager.file.naming.impl.LinuxFile;
import com.picgure.api.manager.file.naming.impl.WindowsFile;
import com.picgure.api.util.Constants;
import com.picgure.api.util.Setting;
import com.picgure.persistence.dao.PicgureSettingRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.logging.Logger;

@Component
public class FileServiceImpl implements FileService {

	private Logger logger = Logger.getLogger(FileServiceImpl.class.getName());

	@Autowired
	private final PicgureSettingRepository settingsRepo;

	private final CreateFileStratedgy createFileStratedgy;

	public FileServiceImpl(PicgureSettingRepository settingsRepo) {
		this.createFileStratedgy = getCreateFileStratedgy();
		this.settingsRepo = settingsRepo;
	}
	
	/**
	 * Function to save the Imgur Object, from its InputStream , as a file with appropriate folder structure.
	 * Returns Boolean to tell the caller whether the object is saved or not.
	 * 
	 * @param subredditName Name of the sub reddit
	 * @param fileName Name of file with which the object is supposed to get saved
	 * @param is Content of imgur object
	 * @return boolean
	 */
	@Override
	public boolean saveImgurObjectAsFile(String subredditName, String fileName, InputStream is) {
		String destFolderUrl = this.getBaseDirectory() + Constants.FILE_SEPERATOR + subredditName;
		String destFileUrl = destFolderUrl + Constants.FILE_SEPERATOR + fileName;
		FileOutputStream fileOutputStream;
		File destFolder = new File(destFolderUrl);
		File destFile = new File(destFileUrl);
		boolean isSaved;
		
		try {
			destFolder.mkdirs();
			destFile = createFileStratedgy.createFile(destFolderUrl, fileName);
			destFile.createNewFile();
			fileOutputStream = new FileOutputStream(destFile);
			IOUtils.copy(is, fileOutputStream);
			fileOutputStream.close();
			is.close();
			isSaved = true;
			logger.info("File saved " + destFile.getAbsolutePath());
		} catch (Exception e) {
		    e.printStackTrace();
			isSaved = false;
			this.logger.severe("Error occurred in saving file " + destFile.getAbsolutePath());
		}
		
		return isSaved;
	}

	/**
	 * Function to replace the illegal characters in file name for windows
	 * 
	 * @param name File Name
	 * @return String
	 */
	@Override
	public String replaceIllegalCharsInFileName(String name) {
		return name.replaceAll("[\\/:*?<>|\"]", "_");
	}

	@Override
	public File defaultImageStoreDirectory() {
		String pathToHome = System.getProperty("user.home");
		String pathToPicgureRoot = pathToHome + Constants.FILE_SEPERATOR + Constants.DEFAULT_ROOT_DIR_NAME;
		return new File(pathToPicgureRoot);
	}

	@Override
	public void createImageStoreDirectory(File imageStore) {
		if (!imageStore.exists()){
		    logger.info("Creating the image store at " + imageStore.getAbsolutePath());
		    imageStore.mkdir();
        }
	}

	private String getBaseDirectory() {
		String baseDir = settingsRepo.findByName(Setting.ImageStore.toString()).getValue();
		return baseDir == null ? Constants.DEFAULT_DOWNLOAD_DIR : baseDir;
	}

	private CreateFileStratedgy getCreateFileStratedgy() {
		String os = System.getProperty("os.name");
		return os.toLowerCase().contains("win") ? new WindowsFile() : new LinuxFile();
	}

}
