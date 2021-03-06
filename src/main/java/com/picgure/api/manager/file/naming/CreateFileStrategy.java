package com.picgure.api.manager.file.naming;

import com.picgure.logging.PicgureLogger;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.logging.Logger;

/*
 * @author Jeet Prakash
 * */
public abstract class CreateFileStrategy {

    private static Logger logger = PicgureLogger.getLogger(CreateFileStrategy.class);
    protected static final String FILE_SEPARATOR = "/";

    public abstract File createFile(String destFolderUrl, String fileName);

    /**
     * Returns a file object which contains a unique file name.
     * Client of this function first need to check if the file with that particular name exists.
     *
     * @param file
     * @return File
     */
    protected File createUniqueFile(File file) {
        String fileParentDir = file.getParent();
        String fileExt = FilenameUtils.getExtension(file.getName());
        String fileName = FilenameUtils.removeExtension(file.getName());

        File uniqueFile;
        int i = 1;

        do {
            String uniqueFileName = fileName + "[" + i + "]";
            uniqueFile = new File(fileParentDir + FILE_SEPARATOR + uniqueFileName + "." + fileExt);
            i++;
        } while (uniqueFile.exists());

        logger.info("Created a unique File " + uniqueFile.getAbsolutePath());
        return uniqueFile;
    }

}
