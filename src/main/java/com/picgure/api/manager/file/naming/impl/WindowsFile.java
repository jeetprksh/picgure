package com.picgure.api.manager.file.naming.impl;

import com.picgure.api.manager.file.naming.CreateFileStratedgy;
import com.picgure.api.util.Constants;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

/*
 * @author Jeet Prakash
 * */

public class WindowsFile extends CreateFileStratedgy {

    private final int ABSOLUTE_PATH_LENGTH_LIMIT = 260;

    public File createFile(String destFolderUrl, String fileName) {
        String destFileUrl = destFolderUrl + Constants.FILE_SEPERATOR + fileName;
        File destFile;
        if (destFileUrl.length() > ABSOLUTE_PATH_LENGTH_LIMIT) {
            int extraCharsNumber = destFileUrl.length() - ABSOLUTE_PATH_LENGTH_LIMIT;
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
