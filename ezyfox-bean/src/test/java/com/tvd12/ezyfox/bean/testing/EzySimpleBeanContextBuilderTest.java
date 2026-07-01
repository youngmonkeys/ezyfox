package com.tvd12.ezyfox.bean.testing;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyBeanConfig;
import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfox.bean.EzyPrototypeFactory;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.reflect.FieldUtil;
import com.tvd12.test.reflect.MethodUtil;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EzySimpleBeanContextBuilderTest {

    @Test
    public void activeProfilesTest() {
        // given
        String activeProfiles = "foo,bar";

        EzyBeanContextBuilder sut = EzyBeanContext.builder()
            .scan((String) null)
            .addProperty(EzyBeanContext.ACTIVE_PROFILES_KEY, "hello,big,world")
            .activeProfiles(activeProfiles);

        // when
        String actual = MethodUtil.invokeMethod("getActiveProfiles", sut);

        // then
        Asserts.assertEquals("hello,big,world,bar,foo", actual);
    }

    @Test
    public void activeProfilesDefaultNullTest() {
        // given
        String activeProfiles = "foo,bar";

        EzyBeanContextBuilder sut = EzyBeanContext.builder()
            .activeProfiles(null)
            .scan((String) null);

        Properties properties = FieldUtil.getFieldValue(sut, "properties");
        properties.clear();

        sut.activeProfiles(activeProfiles);

        // when
        String actual = MethodUtil.invokeMethod("getActiveProfiles", sut);

        // then
        Asserts.assertEquals("bar,foo", actual);
    }

    @Test
    public void createAndLoadPrototypeSupplierCatchThrowable() throws Exception {
        // given
        Class<?> brokenClass = new MissingDependencyClassLoader().loadClass(
            MISSING_DEPENDENCY_PROTOTYPE_CLASS
        );

        // when
        EzyBeanContext context = EzyBeanContext.builder()
            .scan((String) null)
            .addPrototypeClass(brokenClass)
            .addPrototypeClass(SuccessPrototype.class)
            .build();

        // then
        EzyPrototypeFactory prototypeFactory = context.getPrototypeFactory();
        Asserts.assertNull(prototypeFactory.getSupplier(brokenClass));
        Asserts.assertNotNull(prototypeFactory.getSupplier(SuccessPrototype.class));
    }

    @Test
    public void loadConfigurationClassCatchNewSingletonException() {
        // when
        EzyBeanContext context = EzyBeanContext.builder()
            .scan((String) null)
            .addConfigurationClass(MissingBeanDependencyConfiguration.class)
            .addConfigurationClass(SuccessConfiguration.class)
            .build();

        // then
        Asserts.assertNull(
            context.getSingletonFactory()
                .getSingleton(MissingBeanDependencyConfiguration.class)
        );
        Asserts.assertNotNull(
            context.getSingletonFactory()
                .getSingleton(SuccessConfiguration.class)
        );
    }

    @Test
    public void loadConfigurationClassCatchLinkageError() throws Exception {
        // given
        Class<?> brokenClass = new MissingDependencyClassLoader().loadClass(
            MISSING_DEPENDENCY_CONFIGURATION_CLASS
        );

        // when
        EzyBeanContext context = EzyBeanContext.builder()
            .scan((String) null)
            .addConfigurationClass(brokenClass)
            .addConfigurationClass(SuccessConfiguration.class)
            .build();

        // then
        Asserts.assertNull(
            context.getSingletonFactory().getSingleton(brokenClass)
        );
        Asserts.assertNotNull(
            context.getSingletonFactory()
                .getSingleton(SuccessConfiguration.class)
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void loadConfigurationClassCatchOtherException() {
        // when
        EzyBeanContext.builder()
            .scan((String) null)
            .addConfigurationClass(ErrorConfiguration.class)
            .build();
    }

    private static final String MISSING_DEPENDENCY_PROTOTYPE_CLASS =
        "com.tvd12.ezyfox.bean.testing.missing." +
            "MissingDependencyPrototype";

    private static final String MISSING_DEPENDENCY_CONFIGURATION_CLASS =
        "com.tvd12.ezyfox.bean.testing.missing." +
            "MissingDependencyConfiguration";

    private static final String MISSING_PROTOTYPE_DEPENDENCY =
        "com.tvd12.ezyfox.bean.testing.missing." +
            "MissingPrototypeDependency";

    private static final String MISSING_CONFIGURATION_DEPENDENCY =
        "com.tvd12.ezyfox.bean.testing.missing." +
            "MissingConfigurationDependency";

    public static class SuccessPrototype {}

    public static class SuccessConfiguration {}

    public static class MissingBeanDependencyConfiguration {

        public MissingBeanDependencyConfiguration(
            MissingBeanDependency dependency
        ) {}
    }

    public static class MissingBeanDependency {}

    public static class ErrorConfiguration implements EzyBeanConfig {

        @Override
        public void config() {
            throw new IllegalArgumentException("config error");
        }
    }

    private static class MissingDependencyClassLoader extends ClassLoader {

        MissingDependencyClassLoader() {
            super(EzySimpleBeanContextBuilderTest.class.getClassLoader());
        }

        @Override
        protected Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
            if (name.equals(MISSING_PROTOTYPE_DEPENDENCY)) {
                throw new ClassNotFoundException(name);
            }
            if (name.equals(MISSING_CONFIGURATION_DEPENDENCY)) {
                throw new ClassNotFoundException(name);
            }
            if (
                name.equals(MISSING_DEPENDENCY_PROTOTYPE_CLASS) ||
                    name.equals(MISSING_DEPENDENCY_CONFIGURATION_CLASS)
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
