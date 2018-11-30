package com.picgure.ui.probe;

import com.picgure.logging.PicgureLogger;

import javax.swing.*;
import java.util.logging.Logger;

/*
 * @author Jeet Prakash
 * */
public class ProbeFrame extends JFrame {

    private static Logger logger = PicgureLogger.getLogger(ProbeFrame.class);

    private ProbeInfoPanel probeInfoPanel;

    public ProbeFrame() {
        this.probeInfoPanel = new ProbeInfoPanel();
        createFrame();
    }

    private void createFrame() {
        this.setTitle("Reddit Probe");
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(400, 300);
        this.setResizable(false);
        this.add(this.probeInfoPanel);
    }

    public void loadProbeData(String redditName) {
        try {
            this.probeInfoPanel.loadProbeData(redditName);
        } catch (Exception ex) {
            // TODO one more
        }

    }
}
