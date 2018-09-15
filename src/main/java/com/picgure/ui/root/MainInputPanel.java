package com.picgure.ui.root;

import com.picgure.ui.analysis.AnalysisFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Logger;

@Component
public class MainInputPanel extends JPanel {

    private static Logger logger = Logger.getLogger(MainInputPanel.class.getName());

    private AnalysisFrame analysisFrame;

    @Autowired
    public MainInputPanel(AnalysisFrame analysisFrame) {
        this.analysisFrame = analysisFrame;
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        createPanel();
    }

    private void createPanel() {
        this.add(createRedditLabel());
        this.add(createRedditNameField());
        this.add(createDownloadButton());
        this.add(createAnalysisButton());
    }

    private JLabel createRedditLabel() {
        return new JLabel("Reddit Name");
    }

    private JTextField createRedditNameField() {
        return new JTextField(20);
    }

    private JButton createDownloadButton() {
        JButton downloadButton = new JButton("Download");
        return downloadButton;
    }

    private JButton createAnalysisButton() {
        JButton analyseButton = new JButton("Analyse");
        analyseButton.addActionListener((ActionEvent event) -> {
            logger.info("Opening Analysis");
            this.analysisFrame.setVisible(true);
        });
        return analyseButton;
    }
}
