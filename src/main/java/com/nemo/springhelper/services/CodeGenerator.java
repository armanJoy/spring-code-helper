package com.nemo.springhelper.services;

import com.intellij.openapi.ui.Messages;
import com.nemo.springhelper.GenerateJavaClassAction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CodeGenerator {

    public static void generateEntityClass(String modelName, String[] fieldNames, String[] fieldTypes) {
        StringBuilder code = new StringBuilder();
        String packageStruct = GenerateJavaClassAction.packageStructure + ".model." + modelName.toLowerCase();

        code.append("package " + packageStruct + ";\n\n");
        code.append("public class ").append(modelName).append(" {\n");

        for (int i = 0; i < fieldNames.length; i++) {
            code.append("    private ").append(fieldTypes[i]).append(" ").append(fieldNames[i]).append(";\n");
        }

        code.append("\n    // Getters and Setters\n");
        for (int i = 0; i < fieldNames.length; i++) {
            code.append("    public ").append(fieldTypes[i]).append(" get").append(capitalize(fieldNames[i])).append("() {\n");
            code.append("        return ").append(fieldNames[i]).append(";\n    }\n\n");
            code.append("    public void set").append(capitalize(fieldNames[i])).append("(").append(fieldTypes[i]).append(" ").append(fieldNames[i]).append(") {\n");
            code.append("        this.").append(fieldNames[i]).append(" = ").append(fieldNames[i]).append(";\n    }\n\n");
        }

        code.append("}\n");

        String directoryStruct = GenerateJavaClassAction.dirPath + packageStruct.replace('.','/') + "/";
        writeToFile(directoryStruct, capitalize(modelName) +
                ".java", code.toString());
    }

    public static void generateRequestDTO(String modelName, String[] fieldNames, String[] fieldTypes) {
        StringBuilder code = new StringBuilder();
        String packageStruct = GenerateJavaClassAction.packageStructure + ".dto." + modelName.toLowerCase() + ".request";

        code.append("package " + packageStruct + ";\n\n");
        code.append("public class ").append(modelName).append("RequestDTO {\n");

        for (int i = 0; i < fieldNames.length; i++) {
            code.append("    private ").append(fieldTypes[i]).append(" ").append(fieldNames[i]).append(";\n");
        }

        code.append("\n    // Getters and Setters\n");
        for (int i = 0; i < fieldNames.length; i++) {
            code.append("    public ").append(fieldTypes[i]).append(" get").append(capitalize(fieldNames[i])).append("() {\n");
            code.append("        return ").append(fieldNames[i]).append(";\n    }\n\n");
            code.append("    public void set").append(capitalize(fieldNames[i])).append("(").append(fieldTypes[i]).append(" ").append(fieldNames[i]).append(") {\n");
            code.append("        this.").append(fieldNames[i]).append(" = ").append(fieldNames[i]).append(";\n    }\n\n");
        }

        code.append("}\n");

        String directoryStruct = GenerateJavaClassAction.dirPath + packageStruct.replace('.','/') + "/";
        writeToFile(directoryStruct,
                capitalize(modelName) + "RequestDTO.java", code.toString());
    }

    public static void generateResponseDTO(String modelName, String[] fieldNames, String[] fieldTypes) {
        StringBuilder code = new StringBuilder();
        String packageStruct =
                GenerateJavaClassAction.packageStructure + ".model." + modelName.toLowerCase() + ".response";

        code.append("package " + packageStruct + ";\n\n");
        code.append("public class ").append(modelName).append("ResponseDTO {\n");

        for (int i = 0; i < fieldNames.length; i++) {
            code.append("    private ").append(fieldTypes[i]).append(" ").append(fieldNames[i]).append(";\n");
        }

        code.append("\n    // Getters and Setters\n");
        for (int i = 0; i < fieldNames.length; i++) {
            code.append("    public ").append(fieldTypes[i]).append(" get").append(capitalize(fieldNames[i])).append("() {\n");
            code.append("        return ").append(fieldNames[i]).append(";\n    }\n\n");
            code.append("    public void set").append(capitalize(fieldNames[i])).append("(").append(fieldTypes[i]).append(" ").append(fieldNames[i]).append(") {\n");
            code.append("        this.").append(fieldNames[i]).append(" = ").append(fieldNames[i]).append(";\n    }\n\n");
        }

        code.append("}\n");

        String directoryStruct = GenerateJavaClassAction.dirPath + packageStruct.replace('.','/') + "/";
        writeToFile(directoryStruct,
                capitalize(modelName) + "ResponseDTO" + ".java", code.toString());
    }

    public static void generateRepositoryInterface(String modelName) {
        StringBuilder code = new StringBuilder();
        String packageStruct = GenerateJavaClassAction.packageStructure + ".repo." + modelName.toLowerCase();

        code.append("package " + packageStruct + ";\n\n");
        code.append("import org.springframework.data.jpa.repository.JpaRepository;\n\n" +
                "public interface " + modelName + "Repository extends JpaRepository<" + modelName + ", Long> {\n" +
                "}\n");

        String directoryStruct = GenerateJavaClassAction.dirPath + packageStruct.replace('.','/') + "/";
        writeToFile(directoryStruct, capitalize(modelName) + "Repo.java", code.toString());
    }

    public static void generateServiceInterface(String modelName) {
        StringBuilder code = new StringBuilder();
        String packageStruct = GenerateJavaClassAction.packageStructure + ".service." + modelName.toLowerCase();

        code.append("package " + packageStruct + ";\n\n");
        code.append("import java.util.List;\n\n" +
                "public interface " + modelName + "Service {\n" +
                "    " + modelName + "ResponseDTO save(" + modelName + "RequestDTO requestDTO);\n" +
                "    " + modelName + "ResponseDTO update(Long id, " + modelName + "RequestDTO requestDTO);\n" +
                "    void delete(Long id);\n" +
                "    " + modelName + "ResponseDTO getById(Long id);\n" +
                "    List<" + modelName + "ResponseDTO> getAll();\n" +
                "    List<" + modelName + "ResponseDTO> search(String keyword);\n" +
                "}\n");

        String directoryStruct = GenerateJavaClassAction.dirPath + packageStruct.replace('.','/') + "/";
        writeToFile(directoryStruct, capitalize(modelName) + "Service.java", code.toString());
    }

    public static void generateServiceImpl(String modelName) {
        StringBuilder code = new StringBuilder();
        String packageStruct = GenerateJavaClassAction.packageStructure + ".service." + modelName.toLowerCase();

        code.append("package " + packageStruct + ";\n\n");
        code.append("import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.stereotype.Service;\n" +
                "import java.util.List;\n" +
                "import java.util.stream.Collectors;\n\n" +
                "@Service\n" +
                "public class " + modelName + "ServiceImpl implements " + modelName + "Service {\n\n" +
                "    @Autowired\n" +
                "    private " + modelName + "Repository " + modelName.toLowerCase() + "Repository;\n\n" +
                "    @Override\n" +
                "    public " + modelName + "ResponseDTO save(" + modelName + "RequestDTO requestDTO) {\n" +
                "        " + modelName + " entity = new " + modelName + "();\n" +
                "        // Map fields from requestDTO to entity\n" +
                "        return convertToResponseDTO(" + modelName.toLowerCase() + "Repository.save(entity));\n" +
                "    }\n\n" +
                "    @Override\n" +
                "    public " + modelName + "ResponseDTO update(Long id, " + modelName + "RequestDTO requestDTO) {\n" +
                "        " + modelName + " entity = " + modelName.toLowerCase() + "Repository.findById(id).orElseThrow(() -> new RuntimeException(\"Entity not found\"));\n" +
                "        // Map fields from requestDTO to entity\n" +
                "        return convertToResponseDTO(" + modelName.toLowerCase() + "Repository.save(entity));\n" +
                "    }\n\n" +
                "    @Override\n" +
                "    public void delete(Long id) {\n" +
                "        " + modelName.toLowerCase() + "Repository.deleteById(id);\n" +
                "    }\n\n" +
                "    @Override\n" +
                "    public " + modelName + "ResponseDTO getById(Long id) {\n" +
                "        return convertToResponseDTO(" + modelName.toLowerCase() + "Repository.findById(id).orElseThrow(() -> new RuntimeException(\"Entity not found\")));\n" +
                "    }\n\n" +
                "    @Override\n" +
                "    public List<" + modelName + "ResponseDTO> getAll() {\n" +
                "        return " + modelName.toLowerCase() + "Repository.findAll().stream().map(this::convertToResponseDTO).collect(Collectors.toList());\n" +
                "    }\n\n" +
                "    @Override\n" +
                "    public List<" + modelName + "ResponseDTO> search(String keyword) {\n" +
                "        // Implement search logic\n" +
                "        return null;\n" +
                "    }\n\n" +
                "    private " + modelName + "ResponseDTO convertToResponseDTO(" + modelName + " entity) {\n" +
                "        " + modelName + "ResponseDTO responseDTO = new " + modelName + "ResponseDTO();\n" +
                "        // Map fields from entity to responseDTO\n" +
                "        return responseDTO;\n" +
                "    }\n" +
                "}\n");

        String directoryStruct = GenerateJavaClassAction.dirPath + packageStruct.replace('.','/') + "/";
        writeToFile(directoryStruct, capitalize(modelName) + "ServiceImpl.java", code.toString());
    }

    public static void generateRestController(String modelName) {
        StringBuilder code = new StringBuilder();
        String packageStruct = GenerateJavaClassAction.packageStructure + ".controller." + modelName.toLowerCase();

        code.append("package " + packageStruct + ";\n\n");
        code.append("import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.web.bind.annotation.*;\n" +
                "import java.util.List;\n\n" +
                "@RestController\n" +
                "@RequestMapping(\"/api/" + modelName.toLowerCase() + "\")\n" +
                "public class " + modelName + "Controller {\n\n" +
                "    @Autowired\n" +
                "    private " + modelName + "Service " + modelName.toLowerCase() + "Service;\n\n" +
                "    @PostMapping\n" +
                "    public " + modelName + "ResponseDTO save(@RequestBody " + modelName + "RequestDTO requestDTO) {\n" +
                "        return " + modelName.toLowerCase() + "Service.save(requestDTO);\n" +
                "    }\n\n" +
                "    @PutMapping(\"/{id}\")\n" +
                "    public " + modelName + "ResponseDTO update(@PathVariable Long id, @RequestBody " + modelName + "RequestDTO requestDTO) {\n" +
                "        return " + modelName.toLowerCase() + "Service.update(id, requestDTO);\n" +
                "    }\n\n" +
                "    @DeleteMapping(\"/{id}\")\n" +
                "    public void delete(@PathVariable Long id) {\n" +
                "        " + modelName.toLowerCase() + "Service.delete(id);\n" +
                "    }\n\n" +
                "    @GetMapping(\"/{id}\")\n" +
                "    public " + modelName + "ResponseDTO getById(@PathVariable Long id) {\n" +
                "        return " + modelName.toLowerCase() + "Service.getById(id);\n" +
                "    }\n\n" +
                "    @GetMapping\n" +
                "    public List<" + modelName + "ResponseDTO> getAll() {\n" +
                "        return " + modelName.toLowerCase() + "Service.getAll();\n" +
                "    }\n\n" +
                "    @GetMapping(\"/search\")\n" +
                "    public List<" + modelName + "ResponseDTO> search(@RequestParam String keyword) {\n" +
                "        return " + modelName.toLowerCase() + "Service.search(keyword);\n" +
                "    }\n" +
                "}\n");

        String directoryStruct = GenerateJavaClassAction.dirPath + packageStruct.replace('.','/') + "/";
        writeToFile(directoryStruct, capitalize(modelName) + "Controller.java", code.toString());
    }

    private static void writeToFile(String dir, String fileName, String content) {
        try {
            if(Files.notExists(Path.of(dir))){
                Files.createDirectories(Path.of(dir));
            }
            File directory = new File(dir);
            File javaFile = new File(directory, fileName);
            try {
                if (javaFile.createNewFile()) {
                    // Write the generated class content to the file
                    try (FileWriter writer = new FileWriter(javaFile)) {
                        writer.write(content);
                    }
                    Messages.showInfoMessage( "Java class '" + fileName + "' created successfully at " + javaFile.getAbsolutePath(), "Success");
                } else {
                    // If file already exists, ask the user if they want to overwrite it
                    int response = Messages.showYesNoDialog("File already exists. Do you want to overwrite it?", "File Exists", Messages.getQuestionIcon());
                    if (response == Messages.YES) {
                        try (FileWriter writer = new FileWriter(javaFile)) {
                            writer.write(content);
                        }
                        Messages.showInfoMessage("Java class '" + fileName + "' overwritten successfully at " + javaFile.getAbsolutePath(), "Success");
                    }
                }
            } catch (IOException ex) {
                Messages.showErrorDialog("Error creating file: " + ex.getMessage(), "Error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}