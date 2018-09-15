package com.picgure.ui.analysis;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.logging.Logger;

@Component
public class AnalysisFrame extends JFrame {

    private static Logger logger = Logger.getLogger(AnalysisFrame.class.getName());

    public AnalysisFrame() {
        createFrame();
    }

    private void createFrame() {
        this.setTitle("Reddit Analysis");
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(400, 300);
        this.setResizable(false);
    }
}
