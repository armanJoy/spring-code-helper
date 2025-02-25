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
        String packageStruct = GenerateJavaClassAction.basePackageStructure + ".model." + modelName.toLowerCase();

        code.append("package " + packageStruct + ";\n\n");
        code.append(getBaseEntityImport());
        code.append(getDataTypeClassImports(String.join(",", fieldTypes)));
        code.append("\npublic class ").append(modelName).append(" extends BaseEntity {\n");

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

        String directoryStruct = GenerateJavaClassAction.dirPath + packageStruct.replace('.', '/') + "/";
        writeToFile(directoryStruct, capitalize(modelName) +
                ".java", code.toString());
    }

    private static String getDataTypeClassImports(String fieldTypes) {
        StringBuilder code = new StringBuilder();
        if (fieldTypes.contains("Instant")) {
            code.append(instantClassImport + "\n");
        }
        if (fieldTypes.contains("LocalDateTime")) {
            code.append(localDateTimeImport + "\n");
        }
        if (fieldTypes.contains("LocalDate")) {
            code.append(localDateImport + "\n");
        }
        if (fieldTypes.contains("List")) {
            code.append(listImport + "\n");
        }
        if (fieldTypes.contains("Set")) {
            code.append(setImport + "\n");
        }
        if (fieldTypes.contains("Map")) {
            code.append(mapImport + "\n");
        }
        return code.toString();
    }

    public static void generateRequestDTO(String modelName, String[] fieldNames, String[] fieldTypes) {
        StringBuilder code = new StringBuilder();
        String packageStruct = GenerateJavaClassAction.basePackageStructure + ".dto." + modelName.toLowerCase() + ".request";

        code.append("package " + packageStruct + ";\n\n");
        code.append(getDataTypeClassImports(String.join(",", fieldTypes)));
        code.append("\npublic class ").append(modelName).append("RequestDTO {\n");

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

        String directoryStruct = GenerateJavaClassAction.dirPath + packageStruct.replace('.', '/') + "/";
        writeToFile(directoryStruct,
                capitalize(modelName) + "RequestDTO.java", code.toString());
    }

    public static void generateResponseDTO(String modelName, String[] fieldNames, String[] fieldTypes) {
        StringBuilder code = new StringBuilder();
        String packageStruct = GenerateJavaClassAction.basePackageStructure + ".dto." + modelName.toLowerCase() + ".response";

        code.append("package " + packageStruct + ";\n\n");
        code.append(getDataTypeClassImports(String.join(",", fieldTypes)));
        code.append("\npublic class ").append(modelName).append("ResponseDTO {\n");

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

        String directoryStruct = GenerateJavaClassAction.dirPath + packageStruct.replace('.', '/') + "/";
        writeToFile(directoryStruct,
                capitalize(modelName) + "ResponseDTO" + ".java", code.toString());
    }

    public static void generateRepositoryInterface(String modelName) {
        StringBuilder code = new StringBuilder();
        String packageStruct = GenerateJavaClassAction.basePackageStructure + ".repo." + modelName.toLowerCase();

        code.append("package " + packageStruct + ";\n\n");

        code.append(
                jpaRepositoryImport +
                        repositoryAnnotationImport +
                        getModelImport(modelName) +
                        "\n@Repository\n" +
                        "public interface " + modelName + "Repo extends JpaRepository<" + capitalize(modelName) + ", Long>{\n" +
                        "}\n");

        String directoryStruct = GenerateJavaClassAction.dirPath + packageStruct.replace('.', '/') + "/";
        writeToFile(directoryStruct, capitalize(modelName) + "Repo.java", code.toString());
    }

    public static void generateServiceInterface(String modelName) {
        StringBuilder code = new StringBuilder();
        String packageStruct = GenerateJavaClassAction.basePackageStructure + ".service." + modelName.toLowerCase();

        code.append("package " + packageStruct + ";\n\n");
        code.append(
                repositoryAnnotationImport +
                        listImport +
                        getModelImport(modelName) +
                        getRequestDtoImport(modelName) +
                        getResponseDtoImport(modelName) +
                        "\npublic interface " + modelName + "Service {\n" +
                        "    " + modelName + "ResponseDTO save(" + modelName + "RequestDTO requestDTO);\n" +
                        "    " + modelName + "ResponseDTO update(Long id, " + modelName + "RequestDTO requestDTO);\n" +
                        "    void delete(Long id);\n" +
                        "    " + modelName + "ResponseDTO getById(Long id);\n" +
                        "    List<" + modelName + "ResponseDTO> getAll();\n" +
                        "    PageData search(String keyword);\n" +
                        "}\n");

        String directoryStruct = GenerateJavaClassAction.dirPath + packageStruct.replace('.', '/') + "/";
        writeToFile(directoryStruct, capitalize(modelName) + "Service.java", code.toString());
    }

    public static void generateServiceImpl(String modelName, String[] fieldNames) {
        StringBuilder code = new StringBuilder();
        String packageStruct = GenerateJavaClassAction.basePackageStructure + ".service." + modelName.toLowerCase();

        code.append("package " + packageStruct + ";\n\n");
        code.append(autoWiredImport +
                serviceAnnotationImport +
                transactionalAnnotationImport +
                listImport +
                collectorsImport +
                getModelImport(modelName) +
                getRepositoryImport(modelName) +
                getRequestDtoImport(modelName) +
                getResponseDtoImport(modelName) +
                "\n@Service\n" +
                "public class " + modelName + "ServiceImpl implements " + modelName + "Service {\n\n" +
                "    @Autowired\n" +
                "    private " + modelName + "Repo " + modelName.toLowerCase() + "Repo;\n\n" +
                "    @Override\n" +
                "    public " + modelName + "ResponseDTO save(" + modelName + "RequestDTO requestDTO) {\n" +
                "        " + modelName + " entity = convertToEntity(requestDTO);\n" +
                "        return convertToResponseDTO(" + modelName.toLowerCase() + "Repo.save(entity));\n" +
                "    }\n\n" +
                "    @Override\n" +
                "    public " + modelName + "ResponseDTO update(Long id, " + modelName + "RequestDTO requestDTO) {\n" +
                "        " + modelName + " entity = " + modelName.toLowerCase() + "Repo.findById(id).orElseThrow(() -> new RuntimeException(\"Data not found\"));\n" +
                "        entity = convertToEntity(requestDTO);\n" +
                "        return convertToResponseDTO(" + modelName.toLowerCase() + "Repo.save(entity));\n" +
                "    }\n\n" +
                "    @Override\n" +
                "    @Transactional\n" +
                "    public void delete(Long id) {\n" +
                "        " + modelName.toLowerCase() + "Repo.deleteById(id);\n" +
                "    }\n\n" +
                "    @Override\n" +
                "    public " + modelName + "ResponseDTO getById(Long id) {\n" +
                "        return convertToResponseDTO(" + modelName.toLowerCase() + "Repo.findById(id).orElseThrow(() -> new RuntimeException(\"Entity not found\")));\n" +
                "    }\n\n" +
                "    @Override\n" +
                "    public List<" + modelName + "ResponseDTO> getAll() {\n" +
                "        return " + modelName.toLowerCase() + "Repo.findAll().stream().map(this::convertToResponseDTO).collect(Collectors.toList());\n" +
                "    }\n\n" +
                "    @Override\n" +
                "    public PageData search(String keyword) {\n" +
                "        // Implement search logic\n" +
                "        return null;\n" +
                "    }\n\n" +
                "    private " + modelName + " convertToEntity(" + modelName + "RequestDTO requestDto) {\n" +
                "        " + modelName + " entity = new " + modelName + "();\n" +
                "        // Map fields from requestDto to entity\n" +
                getFieldMappingForEntity(fieldNames) +
                "        return entity;\n" +
                "    }\n" +
                "    private " + modelName + "ResponseDTO convertToResponseDTO(" + modelName + " entity) {\n" +
                "        " + modelName + "ResponseDTO responseDTO = new " + modelName + "ResponseDTO();\n" +
                "        // Map fields from entity to responseDTO\n" +
                getFieldMappingForResponseDto(fieldNames) +
                "        return responseDTO;\n" +
                "    }\n" +
                "}\n");

        String directoryStruct = GenerateJavaClassAction.dirPath + packageStruct.replace('.', '/') + "/";
        writeToFile(directoryStruct, capitalize(modelName) + "ServiceImpl.java", code.toString());
    }

    private static String getFieldMappingForEntity(String[] fieldNames){
        StringBuilder mappingCode = new StringBuilder();
        for (int i = 0; i < fieldNames.length; i++) {
            mappingCode.append("    entity.set" + capitalize(fieldNames[i]) + "(requestDto.get" + capitalize(fieldNames[i]) + "());\n");
        }
        return mappingCode.toString();
    }
    private static String getFieldMappingForResponseDto(String[] fieldNames){
        StringBuilder mappingCode = new StringBuilder();
        for (int i = 0; i < fieldNames.length; i++) {
            mappingCode.append("    responseDTO.set" + capitalize(fieldNames[i]) + "(entity.get" + capitalize(fieldNames[i]) + "());\n");
        }
        return mappingCode.toString();
    }

    public static void generateRestController(String modelName) {
        StringBuilder code = new StringBuilder();
        String packageStruct = GenerateJavaClassAction.basePackageStructure + ".controller";

        code.append("package " + packageStruct + ";\n\n");
        code.append(
                autoWiredImport +
                        annotationImport +
                        validatedAnnotationImport +
                        listImport +
                        getServiceImport(modelName) +
                        getRequestDtoImport(modelName) +
                        getResponseDtoImport(modelName) +
                        "\n@RestController\n" +
                        "\n@RestController\n" +
                        "\n@RestController\n" +
                        "@RequestMapping(\"/api/" + modelName.toLowerCase() + "\")\n" +
                        "public class " + modelName + "Controller {\n\n" +
                        "    @Autowired\n" +
                        "    private " + modelName + "Service " + modelName.toLowerCase() + "Service;\n\n" +
                        "    @PostMapping\n" +
                        "    public " + modelName + "ResponseDTO save(@Validated @RequestBody " + modelName +
                        "RequestDTO requestDTO) {\n" +
                        "        return " + modelName.toLowerCase() + "Service.save(requestDTO);\n" +
                        "    }\n\n" +
                        "    @PutMapping(\"/{id}\")\n" +
                        "    public " + modelName + "ResponseDTO update(@PathVariable Long id, @Validated @RequestBody " + modelName + "RequestDTO requestDTO) {\n" +
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
                        "    public PageData search(@RequestParam String " +
                        "keyword) {\n" +
                        "        return " + modelName.toLowerCase() + "Service.search(keyword);\n" +
                        "    }\n" +
                        "}\n");

        String directoryStruct = GenerateJavaClassAction.dirPath + packageStruct.replace('.', '/') + "/";
        writeToFile(directoryStruct, capitalize(modelName) + "Controller.java", code.toString());
    }

    public static void generateBaseEntityIfNotExits(){
        String packageStruct = GenerateJavaClassAction.basePackageStructure + ".model.common";
        String baseEntityClass = """
                package %s;
                
                import com.technonext.transport.common.Utils.Helper;
                import com.technonext.transport.model.user.User;
                import jakarta.persistence.*;
                import lombok.Getter;
                import lombok.Setter;
                import org.hibernate.annotations.CreationTimestamp;
                import org.hibernate.annotations.UpdateTimestamp;
                import org.springframework.data.annotation.CreatedBy;
                import org.springframework.data.annotation.LastModifiedBy;
                
                import java.io.Serializable;
                import java.time.Instant;
                
                @MappedSuperclass
                @Setter
                @Getter
                public class BaseEntity implements Serializable {
                
                    @Id
                    @GeneratedValue(strategy = GenerationType.IDENTITY)
                    private Long id;
                
                    @CreationTimestamp
                    @Column(updatable = false, nullable = false)
                    private Instant createdTime;
                
                    @UpdateTimestamp
                    private Instant updatedTime;
                
                    @ManyToOne(fetch = FetchType.LAZY)
                    @CreatedBy
                    private User createdBy;
                
                    @ManyToOne(fetch = FetchType.LAZY)
                    @LastModifiedBy
                    private User updatedBy;
                
                    private Boolean isActive = true;
                
                    @PrePersist
                    protected void onBasePersist() {
                        User user = Helper.getCurrentUser();
                        if (user != null && this.createdBy == null) {
                            this.createdBy = user;
                        }
                        this.isActive = true;
                    }
                
                
                    @PreUpdate
                    private void onBaseUpdate() {
                        User user = Helper.getCurrentUser();
                        if (user != null) {
                            this.updatedBy = user;
                        } else {
                            this.updatedBy = this.createdBy;
                        }
                    }
                
                }
                """.formatted(packageStruct);
        String directoryStruct = GenerateJavaClassAction.dirPath + packageStruct.replace('.', '/') + "/";
        writeToFile(directoryStruct, "BaseEntity.java", baseEntityClass);
    }

    public static void generatePageDataResponseDtoIfNotExits(){
        String packageStruct = GenerateJavaClassAction.basePackageStructure+".dto.common";
        String baseEntityClass = """
                package %s;
                
                import lombok.*;
                import org.springframework.data.domain.Page;
                
                import java.util.List;
                
                @Getter
                @Setter
                @Builder
                @AllArgsConstructor
                @NoArgsConstructor
                public class PageData {
                    List<?> model;
                    int totalPages;
                    int currentPage;
                    long totalElements;
                    Object objectData;
                
                    public static PageData empty() {
                        Page<Object> pagedData = Page.empty();
                        return PageData.builder()
                                .model(pagedData.getContent())
                                .totalPages(pagedData.getTotalPages())
                                .totalElements(pagedData.getTotalElements())
                                .currentPage(1)
                                .build();
                    }
                
                    public PageData(List<?> model, int totalPages, int currentPage, long totalElements) {
                        this.model = model;
                        this.totalPages = totalPages;
                        this.currentPage = currentPage;
                        this.totalElements = totalElements;
                    }
                }
                """.formatted(packageStruct);
        String directoryStruct = GenerateJavaClassAction.dirPath + packageStruct.replace('.', '/') + "/";
        writeToFile(directoryStruct, "PageData.java", baseEntityClass);
    }

    private static void writeToFile(String dir, String fileName, String content) {
        try {
            if (Files.notExists(Path.of(dir))) {
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
                    Messages.showInfoMessage("Java class '" + fileName + "' created successfully at " + javaFile.getAbsolutePath(), "Success");
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

    private static final String listImport = "import java.util.List;\n";

    private static final String setImport = "import java.util.Set;\n";

    private static final String mapImport = "import java.util.Map;\n";

    private static final String collectorsImport = "import java.util.stream.Collectors;\n";

    private static final String annotationImport = "import org.springframework.web.bind.annotation.*;\n";
    private static final String validatedAnnotationImport = "import org.springframework.validation.annotation.Validated;";
    private static final String validAnnotationImport = "import jakarta.validation.Valid;";
    private static final String autoWiredImport = "import org.springframework.beans.factory.annotation.Autowired;\n";

    private static final String serviceAnnotationImport = "import org.springframework.stereotype.Service;\n";
    private static final String transactionalAnnotationImport = "import org.springframework.transaction.annotation.Transactional;";

    private static final String jpaRepositoryImport = "import org.springframework.data.jpa.repository.JpaRepository;\n";

    private static final String repositoryAnnotationImport = "import org.springframework.stereotype.Repository;\n";

    private static final String localDateImport = "import java.time.LocalDate;\n";

    private static final String localDateTimeImport = "import java.time.LocalDateTime;\n";

    private static final String instantClassImport = "import java.time.Instant;\n";

    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private static String getRequestDtoImport(String modelName) {
        return "import %s.dto.%s.request.*;\n".formatted(GenerateJavaClassAction.basePackageStructure,
                modelName.toLowerCase());
    }

    private static String getResponseDtoImport(String modelName) {
        return "import %s.dto.%s.response.*;\n".formatted(GenerateJavaClassAction.basePackageStructure,
                modelName.toLowerCase());
    }

    private static String getBaseEntityImport() {
        return "import %s.model.common.BaseEntity;\n".formatted(GenerateJavaClassAction.basePackageStructure);
    }

    private static String getPageDataClassImport() {
        return "import %s.dto.common.PageData;\n".formatted(GenerateJavaClassAction.basePackageStructure);
    }

    private static String getModelImport(String modelName) {
        return "import %s.model.%s.%s;\n".formatted(GenerateJavaClassAction.basePackageStructure,
                modelName.toLowerCase(), capitalize(modelName));
    }

    private static String getServiceImport(String modelName) {
        return "import %s.service.%s.*;\n".formatted(GenerateJavaClassAction.basePackageStructure,
                modelName.toLowerCase());
    }

    private static String getRepositoryImport(String modelName) {
        return "import %s.repo.%s.*;\n".formatted(GenerateJavaClassAction.basePackageStructure, modelName.toLowerCase());
    }


}