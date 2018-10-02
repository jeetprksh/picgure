package com.picgure.ui.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.logging.Logger;

@Component
public class SettingsFrame extends JFrame {

    private static Logger logger = Logger.getLogger(SettingsFrame.class.getName());

    private SettingsInputPanel settingsInputPanel;

    @Autowired
    public SettingsFrame(SettingsInputPanel settingsInputPanel) {
        this.settingsInputPanel = settingsInputPanel;
        createFrame();
    }

    private void createFrame() {
        this.setTitle("Settings");
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(400, 300);
        this.setResizable(false);
        this.add(this.settingsInputPanel);
    }

    public void loadSettings() {
        this.settingsInputPanel.loadSettings();
    }

}
