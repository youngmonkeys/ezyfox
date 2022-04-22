package com.tvd12.ezyfox.tool;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    protected boolean overrideExists;
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
        if(inputStream == null)
            throw new IllegalArgumentException("not found template folder: " + path);
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

    public EzyFileCreator replace(Map<String, Object> values) {
        for(String variable : values.keySet())
            replace(variable, values.get(variable));
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

    public EzyFileCreator overrideExists(boolean overrideExists) {
        this.overrideExists = overrideExists;
        return this;
    }

    public String create() {
        String content = createContent();
        if(filePath == null) {
            String packagePath = packageName.replace('.', '/');
            filePath = Paths.get(projectPath, sourcePath, packagePath, className + ".java").toString();
        }
        File file = new File(filePath);
        if(file.exists() && !overrideExists)
            return file.getAbsolutePath();
        if(file.getParentFile() != null && !file.getParentFile().exists())
            file.getParentFile().mkdirs();
        fileWriter.write(file, content, "UTF-8");
        return file.getAbsolutePath();
    }

    public String createContent() {
        if(className != null)
            replace("class-name", className);
        if(packageName != null)
            replace("package-name", packageName);
        String content = template;
        Set<String> variables = new HashSet<>();
        variables.addAll(variableValues.keySet());
        variables.addAll(getVariablesInTemplate());
        for(String variable : variables) {
            String value = variableValues.get(variable);
            if(variable.contains("#or#")) {
                String[] vars = variable.split("#or#");
                for(String vari : vars) {
                    String vali = variableValues.get(vari);
                    if(vali != null) {
                        value = vali;
                        break;
                    }
                }
            }
            if(value != null)
                content = content.replace("${" + variable + "}", value);
        }
        return content;
    }

    private Set<String> getVariablesInTemplate() {
        Set<String> variables = new HashSet<>();
        char[] chars = template.toCharArray();
        for(int i = 0 ; i < chars.length ; ++i) {
            if(chars[i] != '$')
                continue;
            if((i + 1) >= chars.length)
                break;
            if(chars[i + 1] != '{')
                continue;
            ++i;
            int startVar = i + 1;
            int varLength = 0;
            while(true) {
                if((++i) >= chars.length)
                    break;
                if(chars[i] == '}')
                    break;
                ++ varLength;
            }
            if(varLength == 0)
                continue;
            char[] varChars = new char[varLength];
            for(int k = 0 ; k < varChars.length ; ++k)
                varChars[k] = chars[startVar + k];
            variables.add(new String(varChars));
        }
        return variables;
    }
}