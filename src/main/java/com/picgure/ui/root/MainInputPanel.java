package com.picgure.ui.root;

import com.picgure.command.ApplicationCommands;
import com.picgure.ui.probe.ProbeFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.logging.Logger;

class MainInputPanel extends JPanel {

    private static Logger logger = Logger.getLogger(MainInputPanel.class.getName());

    private ProbeFrame probeFrame;
    private ApplicationCommands applicationCommands;

    private JTextField redditNameField;

    MainInputPanel() {
        this.probeFrame = new ProbeFrame();
        this.applicationCommands = new ApplicationCommands();
        createPanel();
    }

    private void createPanel() {
        createRedditNameField();
        this.add(createRedditLabel());
        this.add(this.redditNameField);
        this.add(createDownloadButton());
        this.add(createProbeButton());
    }

    private JLabel createRedditLabel() {
        return new JLabel("Reddit Name");
    }

    private void createRedditNameField() {
        this.redditNameField = new JTextField(20);
    }

    private JButton createDownloadButton() {
        JButton downloadButton = new JButton("Download");
        downloadButton.addActionListener(event ->
            this.applicationCommands.download(this.redditNameField.getText(), "new")
        );
        return downloadButton;
    }

    private JButton createProbeButton() {
        JButton probeButton = new JButton("Probe");
        probeButton.addActionListener((ActionEvent event) -> {
            logger.info("Opening Probe");
            this.probeFrame.setVisible(true);
            this.probeFrame.loadProbeData(this.redditNameField.getText());
        });
        return probeButton;
    }

}
