package com.tvd12.ezyfox.binding.testing;

import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.EzyWriter;
import com.tvd12.ezyfox.binding.annotation.EzyArrayBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.binding.testing.template.HelloWriter;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.reflect.FieldUtil;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class EzyBindingContextBuilderTest {

    @Test
    @SuppressWarnings("rawtypes")
    public void test() {
        // given
        EzyBindingContext bindingContext = EzyBindingContext.builder()
            .addTemplate(HelloWriter.class, new HelloWriter())
            .build();

        // when
        Map<Class, EzyWriter> writersByObjectType = FieldUtil.getFieldValue(
            bindingContext,
            "writersByObjectType"
        );

        // then
        Asserts.assertTrue(writersByObjectType.containsKey(HelloWriter.class));
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void parseObjectBindingClassesCatchThrowable() throws Exception {
        // given
        Class<?> brokenClass = new MissingDependencyClassLoader().loadClass(
            MISSING_DEPENDENCY_OBJECT_BINDING_CLASS
        );
        EzyBindingContext bindingContext = EzyBindingContext.builder()
            .addObjectBindingClass(brokenClass)
            .addObjectBindingClass(SuccessObjectBindingClass.class)
            .build();

        // when
        Map<Class, EzyWriter> writersByObjectType = FieldUtil.getFieldValue(
            bindingContext,
            "writersByObjectType"
        );

        // then
        Asserts.assertFalse(writersByObjectType.containsKey(brokenClass));
        Asserts.assertTrue(
            writersByObjectType.containsKey(SuccessObjectBindingClass.class)
        );
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void parseArrayBindingClassesCatchThrowable() throws Exception {
        // given
        Class<?> brokenClass = new MissingDependencyClassLoader().loadClass(
            MISSING_DEPENDENCY_ARRAY_BINDING_CLASS
        );
        EzyBindingContext bindingContext = EzyBindingContext.builder()
            .addArrayBindingClass(brokenClass)
            .addArrayBindingClass(SuccessArrayBindingClass.class)
            .build();

        // when
        Map<Class, EzyWriter> writersByObjectType = FieldUtil.getFieldValue(
            bindingContext,
            "writersByObjectType"
        );

        // then
        Asserts.assertFalse(writersByObjectType.containsKey(brokenClass));
        Asserts.assertTrue(
            writersByObjectType.containsKey(SuccessArrayBindingClass.class)
        );
    }

    private static final String MISSING_DEPENDENCY_OBJECT_BINDING_CLASS =
        "com.tvd12.ezyfox.binding.testing.missing." +
            "MissingDependencyObjectBindingClass";

    private static final String MISSING_DEPENDENCY_ARRAY_BINDING_CLASS =
        "com.tvd12.ezyfox.binding.testing.missing." +
            "MissingDependencyArrayBindingClass";

    private static final String MISSING_OBJECT_BINDING_DEPENDENCY =
        "com.tvd12.ezyfox.binding.testing.missing." +
            "MissingObjectBindingDependency";

    @EzyObjectBinding
    public static class SuccessObjectBindingClass {
        public String name;
    }

    @EzyArrayBinding
    public static class SuccessArrayBindingClass {
        public String name;
    }

    private static class MissingDependencyClassLoader extends ClassLoader {

        MissingDependencyClassLoader() {
            super(EzyBindingContextBuilderTest.class.getClassLoader());
        }

        @Override
        protected Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
            if (name.equals(MISSING_OBJECT_BINDING_DEPENDENCY)) {
                throw new ClassNotFoundException(name);
            }
            if (
                name.equals(MISSING_DEPENDENCY_OBJECT_BINDING_CLASS) ||
                    name.equals(MISSING_DEPENDENCY_ARRAY_BINDING_CLASS)
            ) {
                Class<?> clazz = findLoadedClass(name);
                if (clazz == null) {
                    byte[] classBytes = getClassBytes(name);
                    clazz = defineClass(name, classBytes, 0, classBytes.length);
                }
                if (resolve) {
                    resolveClass(clazz);
                }
                return clazz;
            }
            return super.loadClass(name, resolve);
        }

        private byte[] getClassBytes(String className)
            throws ClassNotFoundException {
            String resource = className.replace('.', '/') + ".class";
            try (InputStream inputStream = getParent().getResourceAsStream(resource)) {
                if (inputStream == null) {
                    throw new ClassNotFoundException(className);
                }
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int read;
                while ((read = inputStream.read(buffer)) >= 0) {
                    outputStream.write(buffer, 0, read);
                }
                return outputStream.toByteArray();
            } catch (IOException e) {
                throw new ClassNotFoundException(className, e);
            }
        }
    }
}
