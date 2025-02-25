package com.nemo.springhelper.settings;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MyPluginConfigurable implements SearchableConfigurable {
    private MySettingsPanel settingsPanel;
    private Project project;

    public MyPluginConfigurable() {
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "My Plugin Settings";
    }

    @NotNull
    @Override
    public String getId() {
        return "com.nemo.springhelper.settings.code";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (project == null) {
            Project[] openProjects = ProjectManager.getInstance().getOpenProjects();
            if (openProjects.length > 0) {
                project = openProjects[0]; // Choose a default or active project.
            }
        }
        settingsPanel = new MySettingsPanel();
        return settingsPanel;
    }

    @Override
    public boolean isModified() {
        MySettingsState settings = MySettingsState.getInstance(project);
        boolean modified = false;
        modified |= !settingsPanel.getTextInput1().equals(settings.textInput1);
        modified |= !settingsPanel.getTextInput2().equals(settings.textInput2);
        modified |= !settingsPanel.getTextInput3().equals(settings.textInput3);
        modified |= !settingsPanel.getSelectedDropdown().equals(settings.dropdownSelection);
        modified |= !settingsPanel.getFilePath().equals(settings.filePath);
        modified |= (settingsPanel.isCheckboxASelected() != settings.checkboxA);
        modified |= (settingsPanel.isCheckboxBSelected() != settings.checkboxB);
        modified |= (settingsPanel.isRadioOptionOnSelected() != settings.radioOptionOn);

        return modified;
    }

    @Override
    public void apply() throws ConfigurationException {
        MySettingsState settings = MySettingsState.getInstance(project);
        System.out.println(settingsPanel.getTextInput1());
        settings.textInput1 = settingsPanel.getTextInput1();
        settings.textInput2 = settingsPanel.getTextInput2();
        settings.textInput3 = settingsPanel.getTextInput3();
        settings.dropdownSelection = settingsPanel.getSelectedDropdown();
        settings.filePath = settingsPanel.getFilePath();
        settings.checkboxA = settingsPanel.isCheckboxASelected();
        settings.checkboxB = settingsPanel.isCheckboxBSelected();
        settings.radioOptionOn = settingsPanel.isRadioOptionOnSelected();
    }

    @Override
    public void reset() {
        MySettingsState settings = MySettingsState.getInstance(project);
        System.out.println(settingsPanel.getTextInput1());
        settingsPanel.setTextInput1(settings.textInput1);
        settingsPanel.setTextInput2(settings.textInput2);
        settingsPanel.setTextInput3(settings.textInput3);
        settingsPanel.setSelectedDropdown(settings.dropdownSelection);
        settingsPanel.setFilePath(settings.filePath);
        settingsPanel.setCheckboxASelected(settings.checkboxA);
        settingsPanel.setCheckboxBSelected(settings.checkboxB);
        settingsPanel.setRadioOptionOnSelected(settings.radioOptionOn);
    }

    @Override
    public void disposeUIResources() {
        settingsPanel = null;
    }
}


//import com.intellij.openapi.options.ConfigurationException;
//import com.intellij.openapi.options.SearchableConfigurable;
//import org.jetbrains.annotations.Nls;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//import javax.swing.*;
//
//public class MyPluginConfigurable implements SearchableConfigurable {
//    private MySettingsPanel mySettingsPanel;
//
//    @Nls
//    @Override
//    public String getDisplayName() {
//        return "Spring Code Helper Settings";
//    }
//
//    @NotNull
//    @Override
//    public String getId() {
//        return "com.nemo.springhelper.settings";
//    }
//
//    @Nullable
//    @Override
//    public JComponent createComponent() {
//        mySettingsPanel = new MySettingsPanel();
//        return mySettingsPanel.getPanel();
//    }
//
//    @Override
//    public boolean isModified() {
//        MySettingsState settings = MySettingsState.getInstance();
//        return !mySettingsPanel.getMyOption().equals(settings.myOption);
//    }
//
//    @Override
//    public void apply() throws ConfigurationException {
//        MySettingsState settings = MySettingsState.getInstance();
//        settings.myOption = mySettingsPanel.getMyOption();
//    }
//
//    @Override
//    public void reset() {
//        MySettingsState settings = MySettingsState.getInstance();
//        mySettingsPanel.setMyOption(settings.myOption);
//    }
//}