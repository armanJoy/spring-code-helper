package com.nemo.springhelper;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.nemo.springhelper.services.CodeGenerator;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateJavaClassAction extends AnAction {

    public static String dirPath = "";
    public static String basePackageStructure = "";
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // Get the current project
        Project project = e.getProject();
        if (project == null) return;
        String title = "Generate Java Class";
        basePackageStructure = Messages.showInputDialog(project,
                "Enter your main package:", title,
                Messages.getQuestionIcon());
        dirPath = project.getBasePath() + "/src/main/java/";
        // Prompt the user to enter a entityName
        String entityName = Messages.showInputDialog(project, "Entity name:", title, Messages.getQuestionIcon());

        String fieldsInput = Messages.showInputDialog(project,
                "Enter fields (e.g., id:Long, name:String, age:Integer): ", title,
                Messages.getQuestionIcon());
        String[] fields = fieldsInput.split(",");
        String[] fieldNames = new String[fields.length];
        String[] fieldTypes = new String[fields.length];

        for (int i = 0; i < fields.length; i++) {
            String[] parts = fields[i].trim().split(":");
            fieldNames[i] = parts[0].trim();
            fieldTypes[i] = parts[1].trim();
        }

        CodeGenerator.generateEntityClass(entityName, fieldNames, fieldTypes);
        CodeGenerator.generateRequestDTO(entityName, fieldNames, fieldTypes);
        CodeGenerator.generateResponseDTO(entityName, fieldNames, fieldTypes);
        CodeGenerator.generateRepositoryInterface(entityName);
        CodeGenerator.generateServiceInterface(entityName);
        CodeGenerator.generateServiceImpl(entityName);
        CodeGenerator.generateRestController(entityName);

        if (entityName == null || entityName.isEmpty()) return;

        // Create a new Java class with the specified entityName in the comment
//        createJavaClass(project, packageStructure, entityName);
    }

    private void createJavaClass(Project project, String packageStructure, String entityName) {
        // Create the class content with the entityName comment
        String classContent = String.format(
                        "package %s;\n\n" +
                        "// Created by: %s\n"+
                        "public class %s {\n" +
                        "    // Class implementation goes here\n" +
                        "}\n",
                packageStructure, System.getProperty("user.name"), entityName);

        // Determine the project base path
        String projectBasePath = project.getBasePath();
        if (projectBasePath == null) {
            Messages.showErrorDialog(project, "Project base path not found.", "Error");
            return;
        }

        // Convert package name to directory structure and define the desired path, e.g., projectBasePath/src/com/example
        String dirPath = projectBasePath + "/src/main/java/" + packageStructure.replace('.', '/');
        File directory = new File(dirPath);
        if (!directory.exists()) {
            boolean dirsCreated = directory.mkdirs();
            if (!dirsCreated) {
                Messages.showErrorDialog(project, "Failed to create directories: " + dirPath, "Error");
                return;
            }
        }

        // Create the Java file in the determined directory
        File javaFile = new File(directory, entityName + ".java");
        try {
            if (javaFile.createNewFile()) {
                // Write the generated class content to the file
                try (FileWriter writer = new FileWriter(javaFile)) {
                    writer.write(classContent);
                }
                Messages.showInfoMessage(project,
                        "Java class '" + entityName + "' created successfully at " + javaFile.getAbsolutePath(),
                        "Success");
            } else {
                // If file already exists, ask the user if they want to overwrite it
                int response = Messages.showYesNoDialog(project,
                        "File already exists. Do you want to overwrite it?",
                        "File Exists",
                        Messages.getQuestionIcon());
                if (response == Messages.YES) {
                    try (FileWriter writer = new FileWriter(javaFile)) {
                        writer.write(classContent);
                    }
                    Messages.showInfoMessage(project,
                            "Java class '" + entityName + "' overwritten successfully at " + javaFile.getAbsolutePath(),
                            "Success");
                }
            }
        } catch (IOException ex) {
            Messages.showErrorDialog(project, "Error creating file: " + ex.getMessage(), "Error");
        }
    }
}