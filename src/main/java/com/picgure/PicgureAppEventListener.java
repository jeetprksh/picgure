package com.picgure;

import com.picgure.api.manager.FileService;
import com.picgure.api.manager.SettingsService;
import com.picgure.api.manager.impl.FileServiceImpl;
import com.picgure.api.manager.impl.SettingsServiceImpl;
import com.picgure.api.util.Setting;

import java.io.File;
import java.util.logging.Logger;

/*
 * @author Jeet Prakash
 * */

public class PicgureAppEventListener {

    private static Logger logger = Logger.getLogger(PicgureAppEventListener.class.getName());

    private SettingsService settingsService;
    private FileService fileService;

    PicgureAppEventListener() {
        this.settingsService = new SettingsServiceImpl();
        this.fileService = new FileServiceImpl();
    }

    /*
    * TODO
    * Method to check and save new settings and create the ImageStore directory.
    * */
    public void syncSettingsFileStore() {
        logger.info("Settings and File Store sync.");
        settingsService.saveDefaultSettings();
        String imageStorePath = settingsService.getSettings().get(Setting.ImageStore.toString());
        fileService.createImageStoreDirectory(new File(imageStorePath));
    }
}
