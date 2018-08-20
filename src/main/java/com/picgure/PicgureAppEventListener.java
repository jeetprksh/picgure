package com.picgure;

import com.picgure.api.manager.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class PicgureAppEventListener {

    private static Logger logger = Logger.getLogger(PicgureAppEventListener.class.getName());

    @Autowired
    SettingsService settingsService;

    @EventListener(ContextRefreshedEvent.class)
    public void contextStartedEvent() {
        logger.info("Initializing the default settings");
        settingsService.saveDefaultSettings();
    }
}
