package com.picgure;

import com.picgure.api.manager.FileService;
import com.picgure.api.manager.SettingsService;
import com.picgure.api.manager.impl.FileServiceImpl;
import com.picgure.api.manager.impl.SettingsServiceImpl;
import com.picgure.api.util.Setting;
import com.picgure.logging.PicgureLogger;

import java.io.File;
import java.util.logging.Logger;

/*
 * @author Jeet Prakash
 * */
public class PicgureAppEventListener {

    private static Logger logger = PicgureLogger.getLogger(PicgureAppEventListener.class);

    private SettingsService settingsService;
    private FileService fileService;

    PicgureAppEventListener() {
        this.settingsService = new SettingsServiceImpl();
        this.fileService = new FileServiceImpl();
    }

    public void syncSettingsFileStore() {
        logger.info("Settings and File Store sync.");
        settingsService.saveDefaultSettings();
        String imageStorePath = settingsService.getSettings().get(Setting.ImageStore.toString());
        fileService.createImageStoreDirectory(new File(imageStorePath));
    }
}
