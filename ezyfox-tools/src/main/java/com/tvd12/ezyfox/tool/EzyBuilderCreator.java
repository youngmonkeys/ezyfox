package com.tvd12.ezyfox.tool;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.tvd12.ezyfox.reflect.EzyField;

public class EzyBuilderCreator {

    protected boolean buildBySetter;

    public EzyBuilderCreator buildBySetter(boolean buildBySetter) {
        this.buildBySetter = buildBySetter;
        return this;
    }

    public String create(Class<?> clazz) throws Exception {
        return create(clazz, "Builder");
    }

    public String create(
            Class<?> clazz, String builderClassName) throws Exception {
        StringBuilder classScript = new StringBuilder();
        StringBuilder builderScript = new StringBuilder();
        classScript.append("public class ")
            .append(clazz.getSimpleName())
            .append(" {\n\n");
        builderScript.append("public static class " + builderClassName + " implements EzyBuilder<")
                .append(clazz.getSimpleName())
            .append("> {\n\n");
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
            .filter(f -> !Modifier.isStatic(f.getModifiers()))
            .collect(Collectors.toList());
        for(Field field : fields) {
            classScript.append("\t");
            int modifier = field.getModifiers();
            if(Modifier.isPrivate(modifier))
                classScript.append("private");
            else if(Modifier.isProtected(modifier))
                classScript.append("protected");
            else
                classScript.append("public");
            classScript
                .append(" final ")
                .append(field.getType().getSimpleName())
                .append(" ")
                .append(field.getName())
                .append(";\n");
        }
        classScript.append("\n\tprotected ").append(clazz.getSimpleName())
            .append("(Builder builder) {\n");
        for(Field field : fields) {
            classScript.append("\t\tthis.").append(field.getName())
                .append(" = builder.").append(field.getName())
                .append(";\n");
        }
        classScript.append("\t}");
        for(Field field : fields) {
            builderScript.append("\tprotected ")
                .append(field.getType().getSimpleName())
                .append(" ")
                .append(field.getName())
                .append(";\n");
        }
        builderScript.append("\n");
        for(Field field : fields) {
            builderScript.append("\tpublic " + builderClassName + " ")
                .append(field.getName())
                .append("(")
                    .append(field.getType().getSimpleName())
                    .append(" ").append(field.getName())
                .append(") {\n")
                    .append("\t\tthis.").append(field.getName()).append(" = ").append(field.getName())
                    .append(";\n")
                    .append("\t\treturn this;")
                .append("\n\t}\n\n");
        }
        builderScript.append("\t@Override\n");
        builderScript.append("\tpublic ").append(clazz.getSimpleName())
            .append(" build() {\n");
        if(buildBySetter) {
            builderScript.append("\t\t").append(clazz.getSimpleName())
                .append(" p = new ")
                .append(clazz.getSimpleName()).append("();\n");
            for(Field field : fields) {
                EzyField f = new EzyField(field);
                builderScript.append("\t\tp.").append(f.getSetterMethod())
                    .append("(").append(f.getName()).append(");")
                    .append("\n");
            }
            builderScript.append("\t\treturn p;\n");
        }
        else {
            builderScript.append("\t\treturn new ").append(clazz.getSimpleName()).append("(this);\n");
        }
        builderScript.append("\t}");
        builderScript.append("\n\n}");
        return classScript
                .append("\n\n")
                .append("\tpublic static " + builderClassName + " builder() {\n")
                .append("\t\treturn new " + builderClassName + "();\n")
                .append("\t}\n\n")
                .append(EzyStringTool.tabAll(builderScript.toString(), 1))
                .append("\n\n")
                .append("}").toString();
    }
}
