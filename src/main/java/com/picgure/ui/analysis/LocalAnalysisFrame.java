package com.picgure.ui.analysis;

import javax.swing.*;
import java.util.logging.Logger;

/*
 * @author Jeet Prakash
 * */

public class LocalAnalysisFrame extends JFrame {

    private static Logger logger = Logger.getLogger(LocalAnalysisFrame.class.getName());

    private LocalAnalysisPanel localAnalysisPanel;

    public LocalAnalysisFrame() {
        this.localAnalysisPanel = new LocalAnalysisPanel();
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
