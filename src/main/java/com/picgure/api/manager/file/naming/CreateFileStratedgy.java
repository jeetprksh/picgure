package com.picgure.api.manager.file.naming;

import com.picgure.api.util.Constants;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

public abstract class CreateFileStratedgy {

    public abstract File createFile(String destFolderUrl, String fileName);

    /**
     * Returns a file object which contains a unique file name.
     * Client of this function first need to check if the file with that particular name exists.
     *
     * @param destFile
     * @return File
     */
    protected File createUniqueFile(File destFile) {
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

}
