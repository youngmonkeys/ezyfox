package com.tvd12.ezyfox.tool;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EzyBuilderCreator {

	public String create(Class<?> clazz) throws Exception {
		StringBuilder classScript = new StringBuilder();
		StringBuilder builderScript = new StringBuilder();
		classScript.append("public class ")
			.append(clazz.getSimpleName())
			.append(" {\n\n");
		builderScript.append("public static class Builder implements EzyBuilder<")
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
			builderScript.append("\tpublic Builder ")
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
		builderScript.append("\tpublic ").append(clazz.getSimpleName())
			.append(" build() {\n")
				.append("\t\treturn new ").append(clazz.getSimpleName()).append("(this);\n")
			.append("\t}"); 
		builderScript.append("\n\n}");
		return classScript
				.append("\n\n")
				.append("\tpublic static Builder builder() {\n")
				.append("\t\treturn new Builder();\n")
				.append("\t}\n\n")
				.append(EzyStringTool.tabAll(builderScript.toString(), 1))
				.append("\n\n")
				.append("}").toString();
	}
	
}
