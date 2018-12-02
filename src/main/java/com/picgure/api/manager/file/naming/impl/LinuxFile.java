package com.picgure.api.manager.file.naming.impl;

import com.picgure.api.manager.file.naming.CreateFileStrategy;
import com.picgure.api.util.Constants;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

/*
* @author Jeet Prakash
* */
public class LinuxFile extends CreateFileStrategy {

    private final int FILENAME_LIMIT = 255;

    public File createFile(String destFolderUrl, String fileName) {
        String destFileUrl = destFolderUrl + Constants.FILE_SEPERATOR + fileName;
        File destFile;

        if (fileName.length() > FILENAME_LIMIT) {
            int extraCharsNumber = fileName.length() - FILENAME_LIMIT;
            String justFileName = FilenameUtils.removeExtension(fileName);
            String shortenedFileName = justFileName.substring(0, fileName.length() - extraCharsNumber);
            String shortenedFilePath = destFolderUrl + Constants.FILE_SEPERATOR +
                    shortenedFileName + "." + FilenameUtils.getExtension(fileName);
            destFile = new File(shortenedFilePath);
        } else {
            destFile = new File(destFileUrl);
        }

        return destFile.exists() ? createUniqueFile(destFile) : destFile;
    }

}
