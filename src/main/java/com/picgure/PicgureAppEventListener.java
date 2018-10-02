package com.picgure;

import com.picgure.api.manager.FileService;
import com.picgure.api.manager.SettingsService;
import com.picgure.api.util.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.logging.Logger;

@Component
public class PicgureAppEventListener {

    private static Logger logger = Logger.getLogger(PicgureAppEventListener.class.getName());

    @Autowired
    private SettingsService settingsService;

    @Autowired
    private FileService fileService;

    @EventListener(ContextRefreshedEvent.class)
    public void contextStartedEvent() {
        settingsService.saveDefaultSettings();
        String imageStorePath = settingsService.getSettings().get(Setting.ImageStore.toString());
        fileService.createImageStoreDirectory(new File(imageStorePath));
    }
}
