package com.tvd12.ezyfox.tool;

import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyClasses;
import com.tvd12.ezyfox.reflect.EzyField;

import java.util.List;

public class EzySameObjectScriptCreator {

    protected Class<?> originClass;
    protected Class<?> targetClass;
    protected String originObjectName;
    protected String targetObjectName;
    protected boolean includeAllFields;

    public EzySameObjectScriptCreator originClass(Class<?> originClass) {
        this.originClass = originClass;
        if (originObjectName == null) {
            originObjectName = EzyClasses.getVariableName(originClass);
        }
        return this;
    }

    public EzySameObjectScriptCreator targetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
        if (targetObjectName == null) {
            targetObjectName = EzyClasses.getVariableName(targetClass);
        }
        return this;
    }

    public EzySameObjectScriptCreator originObjectName(String originObjectName) {
        this.originObjectName = originObjectName;
        return this;
    }

    public EzySameObjectScriptCreator targetObjectName(String targetObjectName) {
        this.targetObjectName = targetObjectName;
        return this;
    }

    public EzySameObjectScriptCreator includeAllFields(boolean includeAllFields) {
        this.includeAllFields = includeAllFields;
        return this;
    }

    public String generateFuncScript() {
        return "protected " +
            targetClass.getSimpleName() +
            " new" + targetClass.getSimpleName() +
            "(" +
            originClass.getSimpleName() +
            " " + originObjectName +
            ") {\n" +
            EzyStringTool.tabAll(generateScript(), 1) +
            "\n\treturn " + targetObjectName + ";" +
            "\n}";
    }

    public String generateScript() {
        EzyClass originClazz = new EzyClass(originClass);
        List<EzyField> originFields = includeAllFields
            ? originClazz.getFields()
            : originClazz.getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        builder.append(targetClass.getSimpleName())
            .append(" ")
            .append(targetObjectName)
            .append(" = ")
            .append("new ")
            .append(targetClass.getSimpleName())
            .append("();\n");
        int index = 0;
        for (EzyField field : originFields) {
            builder.append(targetObjectName)
                .append(".")
                .append(field.getSetterMethod())
                .append("(")
                .append(originObjectName)
                .append(".")
                .append(field.getGetterMethod()).append("()")
                .append(")")
                .append(";");
            if ((index++) < (originFields.size() - 1)) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public String generateBuildFuncScript() {
        return "public " +
            targetClass.getSimpleName() +
            " build" +
            "(" +
            originClass.getSimpleName() +
            " " + originObjectName +
            ") {\n" +
            EzyStringTool.tabAll(generateBuildScript(), 1) +
            "\n}";
    }

    public String generateBuildScript() {
        EzyClass originClazz = new EzyClass(originClass);
        List<EzyField> originFields = includeAllFields
            ? originClazz.getFields()
            : originClazz.getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        builder.append("return")
            .append(" ")
            .append(targetClass.getSimpleName())
            .append(".builder()\n");
        for (EzyField field : originFields) {
            builder
                .append("\t.")
                .append(field.getName())
                .append("(")
                .append(originObjectName)
                .append(".")
                .append(field.getGetterMethod()).append("()")
                .append(")\n");
        }
        return builder.append("\t.build();").toString();
    }
}
