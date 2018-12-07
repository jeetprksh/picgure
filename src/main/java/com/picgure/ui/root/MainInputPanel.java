package com.picgure.ui.root;

import com.picgure.api.thread.DownloadProgress;
import com.picgure.api.thread.DownloadResult;
import com.picgure.command.ApplicationCommands;
import com.picgure.logging.PicgureLogger;
import com.picgure.ui.factory.UiComponentFactory;
import com.picgure.ui.probe.ProbeFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

/*
 * @author Jeet Prakash
 * */
class MainInputPanel extends JPanel implements Observer {

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
        downloadButton.addActionListener(event -> {
            try {
                DownloadProgress progress = applicationCommands.download(this.redditNameField.getText(), "new");
                progress.addObserver(this);
            } catch (Exception ex) {
                String message = "Error in downloading. Cause: " + ex.getMessage();
                JOptionPane.showMessageDialog(null, message, "Download Error", JOptionPane.ERROR_MESSAGE);
            }
        });
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

    @Override
    public void update(Observable o, Object arg) {
        DownloadProgress progress = (DownloadProgress) o;
        DownloadResult result = (DownloadResult) arg;
        if (!Objects.isNull(result.getException())) {
            String message = "Error in downloading " + result.getImgurObject().getTitle()
                    + ". Cause: " + result.getException().getMessage();
            JOptionPane.showMessageDialog(null, message, "Download Error", JOptionPane.ERROR_MESSAGE);
        }
        if (progress.getResults().size() == progress.getObjectCount()) {
            JOptionPane.showMessageDialog(null, "Download finished.", "Done", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
