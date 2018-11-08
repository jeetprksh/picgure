package com.picgure.ui.root;

import com.picgure.ui.analysis.LocalAnalysisFrame;
import com.picgure.ui.settings.SettingsFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

public class RootFrame extends JFrame {

    private static Logger logger = Logger.getLogger(RootFrame.class.getName());

    private SettingsFrame settingsFrame;
    private LocalAnalysisFrame localAnalysisFrame;
    private MainInputPanel mainInputPanel;

    public RootFrame() {
        this.settingsFrame = new SettingsFrame();
        this.localAnalysisFrame = new LocalAnalysisFrame();
        this.mainInputPanel = new MainInputPanel();
        createFrame();
        createMenuBar();
    }

    private void createFrame() {
        this.setSize(500, 300);
        this.setTitle("Picgure");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.add(mainInputPanel);
        this.setResizable(false);
    }

    private void createMenuBar() {
        JMenuBar menubar = new JMenuBar();
        JMenu options = createOptionsMenu();

        options.add(createSettingsMenuItem());
        options.add(createLocalAnalysisMenuItem());

        menubar.add(options);
        this.setJMenuBar(menubar);
    }

    private JMenu createOptionsMenu() {
        JMenu options = new JMenu("Options");
        options.setMnemonic(KeyEvent.VK_O);
        return options;
    }

    private JMenuItem createLocalAnalysisMenuItem() {
        JMenuItem settingsMenuItem = new JMenuItem("Local Analysis");
        settingsMenuItem.setMnemonic(KeyEvent.VK_L);
        settingsMenuItem.addActionListener((ActionEvent event) -> {
            logger.info("Opening Local Analysis");
            this.localAnalysisFrame.setVisible(true);
        });
        return settingsMenuItem;
    }

    private JMenuItem createSettingsMenuItem() {
        JMenuItem settingsMenuItem = new JMenuItem("Settings");
        settingsMenuItem.setMnemonic(KeyEvent.VK_S);
        settingsMenuItem.addActionListener((ActionEvent event) -> {
            logger.info("Opening Settings");
            this.settingsFrame.setVisible(true);
            this.settingsFrame.loadSettings();
        });
        return settingsMenuItem;
    }
}
