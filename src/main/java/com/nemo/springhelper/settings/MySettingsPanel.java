package com.nemo.springhelper.settings;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserFactory;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileSystem;
import com.intellij.openapi.vfs.newvfs.impl.VirtualDirectoryImpl;

import javax.swing.*;
import java.awt.*;

public class MySettingsPanel extends JPanel {
//    private final Project project;
    // UI Components for 3 text inputs
    private TextFieldWithBrowseButton textInput1;
    private JTextField textInput2;
    private JTextField textInput3;

    // Dropdown (combo box)
    private JComboBox<String> dropdown;

    // File chooser field
    private TextFieldWithBrowseButton fileChooserField;

    // Checkboxes for additional settings
    private JCheckBox checkboxA;
    private JCheckBox checkboxB;

    // Radio buttons for a radio setting (one radio button setting is illustrated as a two-option group)
    private JRadioButton radioOptionOn;
    private JRadioButton radioOptionOff;

    public MySettingsPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.EAST;

        // Row 0 - Text Input 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Base directory:"), gbc);

        gbc.gridx = 1;
        textInput1 = new TextFieldWithBrowseButton(new JTextField(40));
        // Configure the file chooser action
        textInput1.addActionListener(e -> {
            FileChooserDescriptor descriptor = new FileChooserDescriptor(false, true, false, false, false,false);
            descriptor.setTitle("Select File");
            VirtualFile[] files = FileChooserFactory.getInstance()
                    .createFileChooser(descriptor, null, null)
                    .choose(null);
            if (files.length > 0) {
                textInput1.setText(files[0].getPath());
            }
        });
//        textInput1 = new JTextField(20);
        add(textInput1, gbc);

        // Row 1 - Text Input 2
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Text Input 2:"), gbc);

        gbc.gridx = 1;
        textInput2 = new JTextField(20);
        add(textInput2, gbc);

        // Row 2 - Text Input 3
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Text Input 3:"), gbc);

        gbc.gridx = 1;
        textInput3 = new JTextField(20);
        add(textInput3, gbc);

        // Row 3 - Dropdown Input
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Dropdown:"), gbc);

        gbc.gridx = 1;
        dropdown = new ComboBox<>(new String[]{"Option 1", "Option 2", "Option 3"});
        add(dropdown, gbc);

        // Row 4 - File path chooser using TextFieldWithBrowseButton
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("File Path:"), gbc);

        gbc.gridx = 1;
        fileChooserField = new TextFieldWithBrowseButton();
        // Configure the file chooser action
        fileChooserField.addActionListener(e -> {
            FileChooserDescriptor descriptor = new FileChooserDescriptor(false, true, false, false, false,false);
            descriptor.setTitle("Select File");
            VirtualFile[] files = FileChooserFactory.getInstance()
                    .createFileChooser(descriptor, null, null)
                    .choose(null);
            if (files.length > 0) {
                fileChooserField.setText(files[0].getPath());
            }
        });
        add(fileChooserField, gbc);

        // Row 5 - Checkbox settings
        gbc.gridx = 0;
        gbc.gridy++;
        checkboxA = new JCheckBox("Enable Feature A");
        add(checkboxA, gbc);

        gbc.gridx = 1;
        checkboxB = new JCheckBox("Enable Feature B");
        add(checkboxB, gbc);

        // Row 6 - Radio button setting
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Radio Setting:"), gbc);

        gbc.gridx = 1;
        radioOptionOn = new JRadioButton("Option On");
        radioOptionOff = new JRadioButton("Option Off");
        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(radioOptionOn);
        radioGroup.add(radioOptionOff);
        // Default selection for radio setting
        radioOptionOn.setSelected(true);
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        radioPanel.add(radioOptionOn);
        radioPanel.add(radioOptionOff);
        add(radioPanel, gbc);
    }

    //--- Getters and Setters for retrieving and updating values ---

    public String getTextInput1() {
        return textInput1.getText();
    }

    public void setTextInput1(String text) {
        textInput1.setText(text);
    }

    public String getTextInput2() {
        return textInput2.getText();
    }

    public void setTextInput2(String text) {
        textInput2.setText(text);
    }

    public String getTextInput3() {
        return textInput3.getText();
    }

    public void setTextInput3(String text) {
        textInput3.setText(text);
    }

    public String getSelectedDropdown() {
        return (String) dropdown.getSelectedItem();
    }

    public void setSelectedDropdown(String selection) {
        dropdown.setSelectedItem(selection);
    }

    public String getFilePath() {
        return fileChooserField.getText();
    }

    public void setFilePath(String path) {
        fileChooserField.setText(path);
    }

    public boolean isCheckboxASelected() {
        return checkboxA.isSelected();
    }

    public void setCheckboxASelected(boolean selected) {
        checkboxA.setSelected(selected);
    }

    public boolean isCheckboxBSelected() {
        return checkboxB.isSelected();
    }

    public void setCheckboxBSelected(boolean selected) {
        checkboxB.setSelected(selected);
    }

    public boolean isRadioOptionOnSelected() {
        return radioOptionOn.isSelected();
    }

    public void setRadioOptionOnSelected(boolean selected) {
        radioOptionOn.setSelected(selected);
        radioOptionOff.setSelected(!selected);
    }
}