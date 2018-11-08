package com.picgure.ui.probe;

import com.picgure.command.ApplicationCommands;

import javax.swing.*;

class ProbeInfoPanel extends JPanel {

    private JTextArea textArea;

    private ApplicationCommands applicationCommands;

    ProbeInfoPanel() {
        this.applicationCommands = new ApplicationCommands();
        createUI();
    }

    private void createUI() {
        this.textArea = new JTextArea("Loading...", 6, 30);
        textArea.setEditable(false);
        this.add(textArea);
    }

    void loadProbeData(String redditName) {
        String message = this.applicationCommands.probe(redditName);
        this.textArea.setText(message);
    }
}
