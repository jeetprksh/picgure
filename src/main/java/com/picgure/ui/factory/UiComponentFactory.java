package com.picgure.ui.factory;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/*
 * @author Jeet Prakash
 * */
public class UiComponentFactory {

    public JLabel getLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Dialog", Font.BOLD, 15));
        return label;
    }

    public JTextField getTextField(String defaultText) {
        JTextField textField = new JTextField(defaultText, 20);
        textField.setFont(new Font("Dialog", Font.PLAIN, 15));
        textField.setBorder(new LineBorder(Color.GRAY));
        return textField;
    }

    public JButton getButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Dialog", Font.PLAIN, 13));
        return button;
    }
}
