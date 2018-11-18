package com.picgure.ui.settings;

import com.picgure.api.manager.SettingsService;
import com.picgure.api.manager.impl.SettingsServiceImpl;
import com.picgure.api.util.Setting;
import com.picgure.ui.factory.UiComponentFactory;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.util.logging.Logger;

/*
 * @author Jeet Prakash
 * */
class SettingsInputPanel extends JPanel {

    private static Logger logger = Logger.getLogger(SettingsInputPanel.class.getName());

    private JTextField downloadFolderTextField;
    private UiComponentFactory componentFactory;

    private SettingsService settingsService;

    SettingsInputPanel() {
        this.settingsService = new SettingsServiceImpl();
        this.componentFactory = new UiComponentFactory();
        createUI();
    }

    private void createUI() {
        createDownloadDirSetting();
        createSaveButton();
    }

    private void createDownloadDirSetting() {
        JLabel label = componentFactory.getLabel("Download Folder");
        downloadFolderTextField = componentFactory.getTextField("");

        JButton button = componentFactory.getButton("Choose");
        button.addActionListener((event) -> {
            JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int selected = fileChooser.showOpenDialog(null);
            if (selected == JFileChooser.APPROVE_OPTION)
                downloadFolderTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        });

        this.add(label);
        this.add(downloadFolderTextField);
        this.add(button);
    }

    private void createSaveButton() {
        JButton saveButton = componentFactory.getButton("Save");
        saveButton.addActionListener((event) -> {
            logger.info("Saving settings");
            settingsService.updateSetting(Setting.ImageStore.toString(), downloadFolderTextField.getText());
        });
        this.add(saveButton);
    }

    void loadSettings() {
        String value = settingsService.getSettings().get(Setting.ImageStore.toString());
        this.downloadFolderTextField.setText(value);
    }

}
