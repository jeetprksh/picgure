package com.picgure;

import com.picgure.api.manager.FileService;
import com.picgure.api.manager.SettingsService;
import com.picgure.api.util.Setting;

import java.io.File;
import java.util.logging.Logger;

public class PicgureAppEventListener {

    private static Logger logger = Logger.getLogger(PicgureAppEventListener.class.getName());

    private SettingsService settingsService;
    private FileService fileService;

    /*
    * TODO
    * Method to check and save new settings and create the ImageStore directory.
    *
    * */
    public void contextStartedEvent() {
        settingsService.saveDefaultSettings();
        String imageStorePath = settingsService.getSettings().get(Setting.ImageStore.toString());
        fileService.createImageStoreDirectory(new File(imageStorePath));
    }
}
