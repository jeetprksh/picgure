package com.picgure.api.manager.impl;

import com.picgure.api.manager.FileService;
import com.picgure.api.manager.file.naming.CreateFileStrategy;
import com.picgure.api.manager.file.naming.impl.LinuxFile;
import com.picgure.api.manager.file.naming.impl.WindowsFile;
import com.picgure.api.util.Setting;
import com.picgure.logging.PicgureLogger;
import com.picgure.persistence.dao.SettingsDao;
import com.picgure.persistence.dao.impl.SettingsDaoImpl;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/*
 * @author Jeet Prakash
 * */
public class FileServiceImpl implements FileService {

	private static Logger logger = PicgureLogger.getLogger(FileServiceImpl.class);
	private static final String DEFAULT_ROOT_DIR_NAME = "picgure";
	private static final String DEFAULT_DOWNLOAD_DIR = "C:/downloader";
    private static final String FILE_SEPARATOR = "/";

	private final SettingsDao settingsDao;
	private final CreateFileStrategy createFileStrategy;

	public FileServiceImpl() {
		this.createFileStrategy = getCreateFileStrategy();
		this.settingsDao = new SettingsDaoImpl();
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
	public boolean saveImgurObjectAsFile(String subredditName, String fileName, InputStream is) throws IOException {
		File destFolder = new File(this.getBaseDirectory() + FILE_SEPARATOR + subredditName);
		destFolder.mkdirs();

		File destFile = createFileStrategy.createFile(destFolder.getAbsolutePath(), fileName);
		boolean isSaved = destFile.createNewFile();

		FileOutputStream fileOutputStream = new FileOutputStream(destFile);
		IOUtils.copy(is, fileOutputStream);

		fileOutputStream.close();
		is.close();

		logger.info("File saved " + destFile.getAbsolutePath());
		return isSaved;
	}

	/**
	 * Function to replace the illegal characters in file name
	 *
	 * @param name File Name
	 * @return String
	 */
	@Override
	public String replaceIllegalUrlChars(String name) {
		return name.replaceAll("[\\/:*?<>|\"]", "_");
	}

	@Override
	public File defaultImageStoreDirectory() {
		String pathToHome = System.getProperty("user.home");
		String pathToPicgureRoot = pathToHome + FILE_SEPARATOR + DEFAULT_ROOT_DIR_NAME;
		return new File(pathToPicgureRoot);
	}

	@Override
	public void createImageStoreDirectory(File imageStore) {
		if (!imageStore.exists()) {
		    logger.info("Creating the image store at " + imageStore.getAbsolutePath());
		    imageStore.mkdir();
        }
	}

	private String getBaseDirectory() {
		String baseDir = settingsDao.findByName(Setting.ImageStore.toString()).getValue();
		return baseDir == null ? DEFAULT_DOWNLOAD_DIR : baseDir;
	}

	private CreateFileStrategy getCreateFileStrategy() {
		String os = System.getProperty("os.name");
		return os.toLowerCase().contains("win") ? new WindowsFile() : new LinuxFile();
	}

}
