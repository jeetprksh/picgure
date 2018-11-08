package com.picgure.ui.analysis;

import com.picgure.command.ApplicationCommands;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Logger;

class LocalAnalysisPanel extends JPanel {

    private static Logger logger = Logger.getLogger(LocalAnalysisPanel.class.getName());

    private JTextField redditNameField;
    private JTextField titleField;
    private DefaultTableModel tableModel;

    private ApplicationCommands appCommands;

    LocalAnalysisPanel() {
        this.appCommands = new ApplicationCommands();
        createUI();
    }

    private void createUI() {
        addTitleField();
        addRedditNameField();
        addLoadDataButton();
        addLocalAnalysisTable();
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

    private void addLocalAnalysisTable() {
        JTable localInfoTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(localInfoTable);

        this.tableModel = new DefaultTableModel(0, 0);
        String header[] = new String[] {
                "Title", "Sub Reddit", "GIF", "Author", "Size", "Created On", "Downloaded On" };
        this.tableModel.setColumnIdentifiers(header);
        localInfoTable.setModel(this.tableModel);

        this.add(scrollPane);
    }

    private void loadAnalysisData(String title, String reddit) {
        this.appCommands.analysis(title, reddit).forEach(imgurObject -> {
            logger.info(imgurObject.toString());
            this.tableModel.addRow(
                    new Object[] { imgurObject.getTitle(),
                                   imgurObject.getSubreddit(),
                                   imgurObject.getAnimated(),
                                   imgurObject.getAuthor(),
                                   imgurObject.getSize(),
                                   imgurObject.getCreateDatetime()}); // TODO both dates are not working
        });
    }
}
