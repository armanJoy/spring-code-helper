package com.nemo.springhelper;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import org.jetbrains.annotations.NotNull;

public class GenerateJavaClassAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // Get the current project
        Project project = e.getProject();
        if (project == null) return;

        // Prompt the user to enter a username
        String username = Messages.showInputDialog(project,
                "Enter your username:",
                "Generate Java Class",
                Messages.getQuestionIcon());

        if (username == null || username.isEmpty()) return;

        // Create a new Java class with the specified username in the comment
        createJavaClass(project, username);
    }

    private void createJavaClass(Project project, String username) {
        // Define the class name (you can customize this)
        String className = "MyClass";

        // Define the package name (you can customize this)
        String packageName = "com.example";

        // Create the class content with the username comment
        String classContent = String.format(
                "// Created by: %s\n\n" +
                        "package %s;\n\n" +
                        "public class %s {\n" +
                        "    // Class implementation goes here\n" +
                        "}\n",
                username, packageName, className);

        // Get the directory where the new file will be created
        VirtualFile baseDir = project.getBaseDir();
        PsiDirectory directory = PsiDirectoryFactory.getInstance(project).createDirectory(baseDir);

        // Create the Java file
        PsiFile javaFile = (PsiFile) PsiFileFactory.getInstance(project)
                .createFileFromText(className + ".java", classContent);

        // Add the file to the directory
        directory.add(javaFile);

        // Reformat the file according to the project's code style
//        CodeStyleManager.getInstance(project).optimizeImports(javaFile);
        CodeStyleManager.getInstance(project).reformat(javaFile);

        // Notify the user that the file has been created
        Messages.showInfoMessage(project,
                String.format("Java class '%s' created successfully!", className),
                "Success");
    }
}