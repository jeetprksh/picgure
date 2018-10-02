package com.picgure.ui.probe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.logging.Logger;

@Component
public class ProbeFrame extends JFrame {

    private static Logger logger = Logger.getLogger(ProbeFrame.class.getName());

    private ProbeInfoPanel probeInfoPanel;

    @Autowired
    public ProbeFrame(ProbeInfoPanel probeInfoPanel) {
        this.probeInfoPanel = probeInfoPanel;
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
        this.probeInfoPanel.loadProbeData(redditName);
    }
}
