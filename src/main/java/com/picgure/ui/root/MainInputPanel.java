package com.picgure.ui.root;

import com.picgure.command.ApplicationCommands;
import com.picgure.logging.PicgureLogger;
import com.picgure.ui.factory.UiComponentFactory;
import com.picgure.ui.probe.ProbeFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.logging.Logger;

/*
 * @author Jeet Prakash
 * */
class MainInputPanel extends JPanel {

    private static Logger logger = PicgureLogger.getLogger(MainInputPanel.class);

    private ProbeFrame probeFrame;
    private ApplicationCommands applicationCommands;
    private UiComponentFactory componentFactory;

    private JTextField redditNameField;

    MainInputPanel() {
        this.probeFrame = new ProbeFrame();
        this.applicationCommands = new ApplicationCommands();
        this.componentFactory = new UiComponentFactory();
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
        return componentFactory.getLabel("Reddit Name");
    }

    private void createRedditNameField() {
        this.redditNameField = componentFactory.getTextField("");
    }

    private JButton createDownloadButton() {
        JButton downloadButton = componentFactory.getButton("Download");
        downloadButton.addActionListener(event ->{
            try {
                this.applicationCommands.download(this.redditNameField.getText(), "new");
            } catch (Exception ex) {
                // TODO do something
            }
        }
        );
        return downloadButton;
    }

    private JButton createProbeButton() {
        JButton probeButton = componentFactory.getButton("Probe");
        probeButton.addActionListener((ActionEvent event) -> {
            logger.info("Opening Probe");
            this.probeFrame.setVisible(true);
            this.probeFrame.loadProbeData(this.redditNameField.getText());
        });
        return probeButton;
    }

}
