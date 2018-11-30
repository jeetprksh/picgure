package com.picgure.ui.analysis;

import com.picgure.command.ApplicationCommands;
import com.picgure.logging.PicgureLogger;
import com.picgure.ui.factory.UiComponentFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Logger;

/*
 * @author Jeet Prakash
 * */
class LocalAnalysisPanel extends JPanel {

    private static Logger logger = PicgureLogger.getLogger(LocalAnalysisPanel.class);

    private JTextField redditNameField;
    private JTextField titleField;
    private DefaultTableModel tableModel;
    private UiComponentFactory componentFactory;

    private ApplicationCommands appCommands;

    LocalAnalysisPanel() {
        this.appCommands = new ApplicationCommands();
        this.componentFactory = new UiComponentFactory();
        createUI();
    }

    private void createUI() {
        addTitleField();
        addRedditNameField();
        addLoadDataButton();
        addLocalAnalysisTable();
    }

    private void addRedditNameField() {
        this.redditNameField = componentFactory.getTextField("Reddit Name");
        this.add(redditNameField);
    }

    private void addTitleField() {
        this.titleField = componentFactory.getTextField("Title");
        this.add(titleField);
    }

    private void addLoadDataButton() {
        JButton loadDataButton = componentFactory.getButton("Analysis");
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
                                   imgurObject.getCreateDatetime()});
        });
    }
}
