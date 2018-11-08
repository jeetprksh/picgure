package com.picgure.ui.settings;

import com.picgure.api.manager.SettingsService;
import com.picgure.api.manager.impl.SettingServiceImpl;
import com.picgure.api.util.Setting;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.util.logging.Logger;

class SettingsInputPanel extends JPanel {

    private static Logger logger = Logger.getLogger(SettingsInputPanel.class.getName());

    private JTextField downloadFolderTextField;

    private SettingsService settingsService;

    SettingsInputPanel() {
        this.settingsService = new SettingServiceImpl();
        createUI();
    }

    private void createUI() {
        createDownloadDirSetting();
        createSaveButton();
    }

    private void createDownloadDirSetting() {
        JLabel label = new JLabel("Download Folder");
        downloadFolderTextField = new JTextField(20);

        JButton button = new JButton("Choose");
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
        JButton saveButton = new JButton("Save");
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
