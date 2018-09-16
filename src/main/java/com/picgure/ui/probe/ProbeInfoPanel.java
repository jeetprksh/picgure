package com.picgure.ui.probe;

import com.picgure.command.ApplicationCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class ProbeInfoPanel extends JPanel {

    private JTextArea textArea;

    private ApplicationCommands applicationCommands;

    @Autowired
    ProbeInfoPanel(ApplicationCommands applicationCommands) {
        this.applicationCommands = applicationCommands;
        createUI();
    }

    private void createUI() {
        this.textArea = new JTextArea("Loading...", 6, 30);
        textArea.setEditable(false);
        this.add(textArea);
        System.out.println("Added UI");
    }

    void loadProbeData(String redditName) {
        String message = this.applicationCommands.probe(redditName);
        this.textArea.setText(message);
    }
}
