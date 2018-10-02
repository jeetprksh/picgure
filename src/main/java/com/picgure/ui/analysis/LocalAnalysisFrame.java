package com.picgure.ui.analysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.logging.Logger;

@Component
public class LocalAnalysisFrame extends JFrame {

    private static Logger logger = Logger.getLogger(LocalAnalysisFrame.class.getName());

    private LocalInfoPanel localInfoPanel;

    @Autowired
    LocalAnalysisFrame(LocalInfoPanel localInfoPanel) {
        this.localInfoPanel = localInfoPanel;
        createFrame();
    }

    private void createFrame() {
        this.setTitle("Reddit Probe");
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(400, 300);
        this.setResizable(false);
        this.add(this.localInfoPanel);
    }

}
