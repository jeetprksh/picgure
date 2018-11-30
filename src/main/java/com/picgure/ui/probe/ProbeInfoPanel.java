package com.picgure.ui.probe;

import com.picgure.command.ApplicationCommands;
import com.picgure.entity.ImgurObjectAttrs;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/*
 * @author Jeet Prakash
 * */
class ProbeInfoPanel extends JPanel {

    private JTextArea textArea;

    private ApplicationCommands applicationCommands;

    ProbeInfoPanel() {
        this.applicationCommands = new ApplicationCommands();
        createUI();
    }

    private void createUI() {
        this.textArea = new JTextArea("Loading...", 10, 25);
        textArea.setEditable(false);
        textArea.setFont(new Font("Dialog", Font.PLAIN, 15));

        JScrollPane scrollPane = new JScrollPane(textArea);
        this.add(scrollPane);
    }

    void loadProbeData(String redditName) throws Exception {
        List<ImgurObjectAttrs> imgurObjects = this.applicationCommands.probe(redditName);

        int size = imgurObjects.stream().mapToInt(obj -> obj.getSize()).sum();
        size = size/1024;

        StringBuilder message = new StringBuilder();
        message.append("Number of objects: " + imgurObjects.size() + "\n\n");
        imgurObjects.stream().forEach(obj -> message.append("  " + obj.getTitle() + ": " + obj.getSize()/1024 + " KB \n"));
        message.append("\n").append("Overall size: " + size + " KB");

        this.textArea.setText(message.toString());
    }
}
