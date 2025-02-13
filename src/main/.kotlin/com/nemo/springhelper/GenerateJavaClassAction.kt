package com.nemo.springhelper

import com.intellij.lang.Language
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.fileTypes.FileTypes
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.psi.impl.file.PsiDirectoryFactory
import com.jetbrains.rd.generator.nova.Lang

class GenerateJavaClassAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        // Get the current project
        val project = e.project ?: return

        // Prompt the user to enter a username
        val username = Messages.showInputDialog(
            project,
            "Enter your username:",
            "Generate Java Class",
            Messages.getQuestionIcon()
        ) ?: return

        // Create a new Java class with the specified username in the comment
        createJavaClass(project, username)
    }

    private fun createJavaClass(project: Project, username: String) {
        // Define the class name (you can customize this)
        val className = "MyClass"

        // Define the package name (you can customize this)
        val packageName = "com.example"

        // Create the class content with the username comment
        val classContent = """
            // Created by: $username

            package $packageName;

            public class $className {
                // Class implementation goes here
            }
        """.trimIndent()

        // Get the directory where the new file will be created
        val baseDir = project.baseDir
        val directory = PsiDirectoryFactory.getInstance(project).createDirectory(baseDir)

        // Create the Java file
        val javaFile = PsiFileFactory.getInstance(project)
            .createFileFromText("$className", FileTypes.PLAIN_TEXT, "Created by: $username") as com.intellij.psi.PsiFile

        // Add the file to the directory
        directory.add(javaFile)

        // Reformat the file according to the project's code style
//        CodeStyleManager.getInstance(project).optimizeImports(javaFile)
        CodeStyleManager.getInstance(project).reformat(javaFile)

        // Notify the user that the file has been created
        Messages.showInfoMessage(
            project,
            "Java class '$className' created successfully!",
            "Success"
        )
    }
}