package com.picgure.ui.analysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.logging.Logger;

@Component
public class LocalAnalysisFrame extends JFrame {

    private static Logger logger = Logger.getLogger(LocalAnalysisFrame.class.getName());

    private LocalAnalysisPanel localAnalysisPanel;

    @Autowired
    LocalAnalysisFrame(LocalAnalysisPanel localAnalysisPanel) {
        this.localAnalysisPanel = localAnalysisPanel;
        createFrame();
    }

    private void createFrame() {
        this.setTitle("Local Analysis");
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(550, 300);
        this.setResizable(false);
        this.add(this.localAnalysisPanel);
    }

}
