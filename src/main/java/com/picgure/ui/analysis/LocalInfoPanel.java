package com.picgure.ui.analysis;

import com.picgure.command.ApplicationCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.logging.Logger;

@Component
class LocalInfoPanel extends JPanel {

    private static Logger logger = Logger.getLogger(LocalInfoPanel.class.getName());

    private JTextField redditNameField;
    private JTextField titleField;
    private JTextArea resultTextArea;

    private ApplicationCommands appCommands;

    @Autowired
    LocalInfoPanel(ApplicationCommands appCommands) {
        this.appCommands = appCommands;
        createUI();
    }

    private void createUI() {
        addTitleField();
        addRedditNameField();
        addLoadDataButton();
        addResultTextArea();
    }

    private void addRedditNameField() {
        this.redditNameField = new JTextField("Reddit Name", 20);
        this.add(redditNameField);
    }

    private void addTitleField() {
        this.titleField = new JTextField("Title", 20);
        this.add(titleField);
    }

    private void addLoadDataButton() {
        JButton loadDataButton = new JButton("Analyse");
        loadDataButton.addActionListener(event ->
            this.loadAnalysisData(
                    this.titleField.getText(), this.redditNameField.getText())
        );
        this.add(loadDataButton);
    }

    private void addResultTextArea() {
        this.resultTextArea = new JTextArea(6, 20);
        resultTextArea.setEditable(false);
        this.add(resultTextArea);
    }

    private void loadAnalysisData(String title, String reddit) {
        this.appCommands.analysis(title, reddit).forEach(imgurObject -> {
            logger.info(imgurObject.toString());
            this.resultTextArea.append(imgurObject.toString());
            this.resultTextArea.append("\\n");
        });
    }
}
