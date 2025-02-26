package com.nemo.springhelper.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "MySettingsState", storages = @Storage("myPluginSettings.xml"))
public class MySettingsState implements PersistentStateComponent<MySettingsState> {
    // Three text inputs
    public String appMainClass = "";
    public String textInput2 = "";
    public String textInput3 = "";

    // Dropdown selection
    public String dropdownSelection = "Option 1";

    // File path selection
    public String filePath = "";

    // Checkbox settings
    public boolean checkboxA = false;
    public boolean checkboxB = false;

    // Radio button setting (true means "Option On", false means "Option Off")
    public boolean radioOptionOn = true;

    @Nullable
    @Override
    public MySettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull MySettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public static MySettingsState getInstance(Project project) {
        return ServiceManager.getService(project, MySettingsState.class);
    }
}

//import com.intellij.openapi.components.PersistentStateComponent;
//import com.intellij.openapi.components.ServiceManager;
//import com.intellij.openapi.components.State;
//import com.intellij.openapi.components.Storage;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//import com.intellij.util.xmlb.XmlSerializerUtil;
//
//@State(name = "MySettingsState", storages = @Storage("myPluginSettings.xml"))
//public class MySettingsState implements PersistentStateComponent<MySettingsState> {
//    // Example setting
//    public String myOption = "default";
//
//    public static MySettingsState getInstance() {
//        return ServiceManager.getService(MySettingsState.class);
//    }
//
//    @Nullable
//    @Override
//    public MySettingsState getState() {
//        return this;
//    }
//
//    @Override
//    public void loadState(@NotNull MySettingsState state) {
//        XmlSerializerUtil.copyBean(state, this);
//    }
//}