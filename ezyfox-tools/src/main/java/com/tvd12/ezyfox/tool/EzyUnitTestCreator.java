package com.tvd12.ezyfox.tool;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.file.EzySimpleFileWriter;
import com.tvd12.ezyfox.io.EzyDates;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.reflect.EzyMethods;
import lombok.Setter;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Setter
public class EzyUnitTestCreator {

    protected String testPackage;
    protected String projectPath;
    protected String testSourceFolder;

    protected final Class<?> clazz;
    protected final EzyObjectInstanceRandom valueRandom;

    protected static final Set<String> ALWAYS_IMPORT_PACKAGES = alwaysImportPackages();

    public EzyUnitTestCreator(Class<?> clazz) {
        this.clazz = clazz;
        this.testSourceFolder = "src/test/java";
        this.valueRandom = new EzyObjectInstanceRandom();
    }

    private static Set<String> alwaysImportPackages() {
        return Collections.unmodifiableSet(Sets.newHashSet(
            "java.lang", "java.util", "java.math"));
    }

    public String createToFile(String method) {
        List<Method> methods = EzyMethods.getMethods(clazz);
        for (Method m : methods) {
            return createToFile(m);
        }
        throw new IllegalArgumentException("not found method: " + method);
    }

    public String createToFile(String method, Class<?>... paramTypes) {
        Method m = EzyMethods.getMethod(clazz, method, paramTypes);
        if (m == null) {
            throw new IllegalStateException(
                "not found method: " + method +
                    "(" + EzyStrings.join(paramTypes, ", ") + ")"
            );
        }
        return createToFile(method);
    }

    public String createToFile(Method method) {
        if (testPackage == null) {
            throw new IllegalStateException("must set testPackage name");
        }
        if (projectPath == null) {
            throw new IllegalStateException("must set projectPath name");
        }
        String content = create(method);
        String fullContent = "// auto generated: " +
            EzyDates.format(new Date()) + "\n\n" +
            "package " + testPackage + ";\n\n" +
            content;
        String filePath = projectPath + "/" +
            testSourceFolder + "/" +
            testPackage.replace('.', '/') + "/" +
            getTestClassName(method) +
            ".java";
        filePath = filePath.replace("//", "/");
        EzySimpleFileWriter.builder().build()
            .write(new File(filePath), fullContent, StandardCharsets.UTF_8);
        return filePath;
    }

    public String create(String method) {
        List<Method> methods = EzyMethods.getMethods(clazz);
        for (Method m : methods) {
            return create(m);
        }
        throw new IllegalArgumentException("not found method: " + method);
    }

    public String create(String method, Class<?>... paramTypes) {
        Method m = EzyMethods.getMethod(clazz, method, paramTypes);
        if (m == null) {
            throw new IllegalStateException(
                "not found method: " + method +
                    "(" + EzyStrings.join(paramTypes, ", ") + ")"
            );
        }
        return create(method);
    }

    public String create(Method method) {
        StringBuilder builder = new StringBuilder();
        addImports(method, builder);
        String testClassName = getTestClassName(method);
        builder.append("public class ").append(testClassName).append(" {\n\n");
        String testMethod = createTestMethod(method);
        builder.append(EzyStringTool.tabAll(testMethod, 1));
        builder.append("\n\n");
        addNewInstance(builder);
        builder.append("\n");
        addNewParamValues(method, builder);
        builder.append("\n}");
        return builder.toString();
    }

    protected void addImports(Method method, StringBuilder builder) {
        builder
            .append("import java.util.*;\n")
            .append("import java.math.*;\n")
            .append("import org.testng.annotations.Test;\n");

        Parameter[] parameters = method.getParameters();
        for (Parameter param : parameters) {
            Class<?> type = param.getType();
            if (type.isPrimitive()) {
                continue;
            }
            boolean importable = true;
            String typeName = type.getName();
            for (String alwayImportPage : ALWAYS_IMPORT_PACKAGES) {
                if (typeName.startsWith(alwayImportPage)) {
                    importable = false;
                    break;
                }
            }
            if (importable) {
                builder.append("import ").append(typeName).append(";\n");
            }
        }
        builder.append("import static org.mockito.Mockito.*;\n");
        builder.append("\n");
    }

    protected String getTestClassName(Method method) {
        return clazz.getSimpleName() +
            "With" +
            EzyStringTool.upperFistChar(method.getName()) +
            "Test";
    }

    protected String createTestMethod(Method method) {
        StringBuilder builder = new StringBuilder("@Test\n")
            .append("public void ")
            .append(method.getName()).append("Test() throws Exception {\n");
        String body = createTestMethodBody(method);
        builder.append(EzyStringTool.tabAll(body, 1));
        return builder.append("\n}").toString();
    }

    protected String createTestMethodBody(Method method) {
        StringBuilder builder = new StringBuilder();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; ++i) {
            Class<?> type = parameters[i].getType();
            builder.append(type.getSimpleName())
                .append(" arg").append(i)
                .append(" = ");
            String valueString;
            if (isCustomerClass(type)) {
                valueString = "new" + type.getSimpleName() + "()";
            } else {
                valueString = valueRandom.randomValueScript(type);
            }
            builder.append(valueString).append(";\n");
        }
        builder.append(clazz.getSimpleName())
            .append(" instance = newInstance();\n");
        Class<?> returnType = method.getReturnType();
        if (!returnType.equals(void.class)) {
            builder.append(returnType.getSimpleName()).append(" result = ");
        }
        builder.append("instance.").append(method.getName()).append("(");
        for (int i = 0; i < parameters.length; ++i) {
            builder.append("arg").append(i);
            if (i < parameters.length - 1) {
                builder.append(", ");
            }
        }
        builder.append(");\n");
        if (!returnType.equals(void.class)) {
            builder.append("System.out.println(\"test result: \" + result);");
        }
        return builder.toString();
    }

    protected void addNewInstance(StringBuilder builder) {
        String body = createNewInstanceBody();
        String newInstance = "protected " +
            clazz.getSimpleName() +
            " newInstance() {\n" +
            EzyStringTool.tabAll(body, 1) +
            "\n}";
        builder.append(EzyStringTool.tabAll(newInstance, 1));
    }

    protected String createNewInstanceBody() {
        StringBuilder builder = new StringBuilder();
        Constructor<?> constructor = EzyReflectTool.getConstructor(clazz);
        Parameter[] parameters = constructor.getParameters();
        for (int i = 0; i < parameters.length; ++i) {
            Class<?> type = parameters[i].getType();
            builder.append(type.getSimpleName()).append(" arg").append(i)
                .append(" = ");
            String valueString;
            if (isCustomerClass(type)) {
                valueString = "new" + type.getSimpleName() + "()";
            } else {
                valueString = valueRandom.randomValueScript(type);
            }
            builder.append(valueString).append(";\n");
        }
        builder.append(clazz.getSimpleName())
            .append(" instance = new ")
            .append(clazz.getSimpleName()).append("(");
        for (int i = 0; i < parameters.length; ++i) {
            builder.append("arg").append(i);
            if (i < parameters.length - 1) {
                builder.append(", ");
            }
        }
        return builder.append(");\n")
            .append("return instance;").toString();
    }

    protected void addNewParamValues(Method method, StringBuilder builder) {
        Set<Class<?>> allCustomTypes = new HashSet<>();
        Constructor<?> constructor = EzyReflectTool.getConstructor(clazz);
        for (Parameter param : constructor.getParameters()) {
            if (isCustomerClass(param.getType())) {
                allCustomTypes.add(param.getType());
            }
        }
        for (Parameter param : method.getParameters()) {
            if (isCustomerClass(param.getType())) {
                allCustomTypes.add(param.getType());
            }
        }
        Set<Class<?>> remainCustomTypes = new HashSet<>(allCustomTypes);
        while (remainCustomTypes.size() > 0) {
            Set<Class<?>> createdCustomTypes = new HashSet<>(allCustomTypes);
            for (Class<?> type : new HashSet<>(remainCustomTypes)) {
                StringBuilder methodBuilder = new StringBuilder()
                    .append("protected ")
                    .append(type.getSimpleName())
                    .append(" new").append(type.getSimpleName()).append("() {\n");
                String script = valueRandom.randomObjectScript(type, remainCustomTypes);
                methodBuilder.append(EzyStringTool.tabAll(script, 1))
                    .append("\n}");
                builder.append("\n")
                    .append(EzyStringTool.tabAll(methodBuilder.toString(), 1))
                    .append("\n");
                createdCustomTypes.add(type);
            }
            remainCustomTypes.removeAll(createdCustomTypes);
        }
    }

    protected boolean isCustomerClass(Class<?> clazz) {
        return EzyToolTypes.isCustomerClass(clazz);
    }
}
