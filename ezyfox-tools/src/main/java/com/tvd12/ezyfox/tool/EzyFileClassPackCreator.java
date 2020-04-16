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
	protected Map<String, String> packageNames;
	protected Map<String, String> templatePaths;
	
	public EzyFileClassPackCreator() {
		this.projectPath = "";
		this.basePackageName = "";
		this.sourcePath = "src/main/java";
		this.packageNames = new HashMap<>();
		this.templatePaths = new HashMap<>();
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
	
	public EzyFileClassPackCreator add(String classType, String packageName, String templateFile) {
		this.packageNames.put(classType, basePackageName + "." + packageName);
		this.templatePaths.put(classType, Paths.get(templateFolderPath, templateFile).toString());
		return this;
	}
	
	public Set<String> create() {
		Set<String> classFilePaths = new HashSet<>();
		for(String classType : packageNames.keySet()) {
			String classFullName = baseClassName + classType;
			String classFilePath = new EzyFileCreator()
				.projectPath(projectPath)
				.sourcePath(sourcePath)
				.packageName(packageNames.get(classType))
				.className(classFullName)
				.templatePath(templatePaths.get(classType))
				.replace("base-class-name", baseClassName)
				.replace("base-package-name", basePackageName)
				.create();
			classFilePaths.add(classFilePath);
		}
		return classFilePaths;
	}
	
}
