package com.tvd12.ezyfox.tool;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.tvd12.ezyfox.file.EzyFileWriter;
import com.tvd12.ezyfox.file.EzySimpleFileWriter;
import com.tvd12.ezyfox.stream.EzyAnywayInputStreamLoader;
import com.tvd12.ezyfox.stream.EzyInputStreamLoader;
import com.tvd12.ezyfox.stream.EzyInputStreamReader;
import com.tvd12.ezyfox.stream.EzySimpleInputStreamReader;

public class EzyFileCreator {

	protected String template;
	protected String filePath;
	protected String projectPath;
	protected String sourcePath;
	protected String packageName;
	protected String className;
	protected Map<String, String> variableValues;
	protected EzyFileWriter fileWriter;
	protected EzyInputStreamLoader inputStreamLoader;
	protected EzyInputStreamReader inputStreamReader;
	
	public EzyFileCreator() {
		this.projectPath = "";
		this.sourcePath = "src/main/java";
		this.variableValues = new HashMap<>();
		this.fileWriter = EzySimpleFileWriter.builder().build();
		this.inputStreamLoader = EzyAnywayInputStreamLoader.builder().build();
		this.inputStreamReader = EzySimpleInputStreamReader.builder().build();
	}
	
	public EzyFileCreator template(String template) {
		this.template = template;
		return this;
	}
	
	public EzyFileCreator templatePath(String path) {
		InputStream inputStream = inputStreamLoader.load(path);
		this.template = inputStreamReader.readString(inputStream, "UTF-8");
		return this;
	}
	
	public EzyFileCreator filePath(String filePath) {
		this.filePath = filePath;
		return this;
	}
	
	public EzyFileCreator projectPath(String projectPath) {
		this.projectPath = projectPath;
		return this;
	}
	
	public EzyFileCreator sourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
		return this;
	}
	
	public EzyFileCreator packageName(String packageName) {
		this.packageName = packageName;
		return this;
	}
	
	public EzyFileCreator className(String className) {
		this.className = className;
		return this;
	}
	
	public EzyFileCreator replace(String variable, Object value) {
		String v = String.valueOf(value);
		if(v.length() == 1)
			v = v.toLowerCase();
		else if(v.length() > 1)
			v = v.substring(0, 1).toLowerCase() + v.substring(1);
		this.variableValues.put(variable, String.valueOf(value));
		this.variableValues.put("lower-" + variable, v);
		return this;
	}
	
	public String create() {
		if(className != null)
			replace("class-name", className);
		if(packageName != null)
			replace("package-name", packageName);
		String content = template;
		for(String variable : variableValues.keySet()) {
			String value = variableValues.get(variable);
			content = content.replace("${" + variable + "}", value);
		}
		if(filePath == null) {
			String packagePath = packageName.replace('.', '/');
			filePath = Paths.get(projectPath, sourcePath, packagePath, className + ".java").toString();
		}
		File file = new File(filePath);
		if(file.getParentFile() != null && !file.getParentFile().exists())
			file.getParentFile().mkdirs();
		fileWriter.write(file, content, "UTF-8");
		return file.getAbsolutePath();
	}
	
}
