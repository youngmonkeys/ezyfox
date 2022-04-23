package com.tvd12.ezyfox.tool;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EzyFileClassPackCreator {

    protected String projectPath;
    protected String sourcePath;
    protected String basePackageName;
    protected String baseClassName;
    protected String templateFolderPath;
    protected boolean overrideExists;
    protected Map<String, String> packageNames;
    protected Map<String, String> templatePaths;
    protected Map<String, String> variableValues;

    public EzyFileClassPackCreator() {
        this.projectPath = "";
        this.basePackageName = "";
        this.sourcePath = "src/main/java";
        this.packageNames = new HashMap<>();
        this.templatePaths = new HashMap<>();
        this.variableValues = new HashMap<>();
    }

    public EzyFileClassPackCreator projectPath(String projectPath) {
        this.projectPath = projectPath;
        return this;
    }

    public EzyFileClassPackCreator sourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
        return this;
    }

    public EzyFileClassPackCreator basePackageName(String packageName) {
        this.basePackageName = packageName;
        return this;
    }

    public EzyFileClassPackCreator baseClassName(String baseClassName) {
        this.baseClassName = baseClassName;
        return this;
    }

    public EzyFileClassPackCreator templateFolderPath(String templateFolderPath) {
        this.templateFolderPath = templateFolderPath;
        return this;
    }

    public EzyFileClassPackCreator overrideExists(boolean overrideExists) {
        this.overrideExists = overrideExists;
        return this;
    }

    public EzyFileClassPackCreator replace(String variable, Object value) {
        this.variableValues.put(variable, String.valueOf(value));
        return this;
    }

    public EzyFileClassPackCreator add(String classType, String packageName, String templateFile) {
        this.packageNames.put(classType, basePackageName + "." + packageName);
        this.templatePaths.put(classType, Paths.get(templateFolderPath, templateFile).toString());
        return this;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Set<String> create() {
        Set<String> classFilePaths = new HashSet<>();
        for(String classType : packageNames.keySet()) {
            String classFullName = baseClassName + classType;
            String classFilePath = new EzyFileCreator()
                .projectPath(projectPath)
                .sourcePath(sourcePath)
                .packageName(packageNames.get(classType))
                .className(classFullName)
                .overrideExists(overrideExists)
                .templatePath(templatePaths.get(classType))
                .replace((Map)variableValues)
                .replace("base-class-name", baseClassName)
                .replace("base-package-name", basePackageName)
                .create();
            classFilePaths.add(classFilePath);
        }
        return classFilePaths;
    }
}
