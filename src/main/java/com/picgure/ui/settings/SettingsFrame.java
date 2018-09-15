package com.picgure.ui.settings;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.logging.Logger;

@Component
public class SettingsFrame extends JFrame {

    private static Logger logger = Logger.getLogger(SettingsFrame.class.getName());

    public SettingsFrame() {
        createFrame();
    }

    private void createFrame() {
        this.setTitle("Settings");
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(400, 300);
        this.setResizable(false);
    }
}
