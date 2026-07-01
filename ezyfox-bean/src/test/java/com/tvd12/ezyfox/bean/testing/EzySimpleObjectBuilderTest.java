package com.tvd12.ezyfox.bean.testing;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.impl.EzyBeanMetadataCache;
import com.tvd12.ezyfox.bean.impl.EzySimpleObjectBuilder;
import com.tvd12.ezyfox.reflect.EzyClass;
import org.testng.annotations.Test;

import java.lang.reflect.Constructor;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class EzySimpleObjectBuilderTest {

    @Test
    public void getConstructorReturnAutoBindConstructor() {
        // given
        Builder builder = new Builder(AutoBindConstructorClass.class);

        // when
        Constructor<?> constructor = builder.getConstructor();

        // then
        assertTrue(constructor.isAnnotationPresent(EzyAutoBind.class));
        assertEquals(constructor.getParameterCount(), 1);
    }

    @Test
    public void getConstructorReturnNoArgsConstructor() {
        // given
        Builder builder = new Builder(NoArgsConstructorClass.class);

        // when
        Constructor<?> constructor = builder.getConstructor();

        // then
        assertEquals(constructor.getParameterCount(), 0);
    }

    @Test
    public void getConstructorReturnMaxArgsConstructor() {
        // given
        Builder builder = new Builder(MaxArgsConstructorClass.class);

        // when
        Constructor<?> constructor = builder.getConstructor();

        // then
        assertEquals(constructor.getParameterCount(), 2);
    }

    @Test
    public void getConstructorArgumentNamesWithoutAnnotation() {
        // given
        Builder builder = new Builder(
            ConstructorArgumentNamesClass.class,
            String.class,
            int.class
        );

        // when
        String[] names = builder.getArgumentNames((EzyAutoBind) null);

        // then
        assertEquals(names, new String[]{"string", "int"});
    }

    @Test
    public void getConstructorArgumentNamesWithAnnotation() throws Exception {
        // given
        Builder builder = new Builder(
            ConstructorArgumentNamesClass.class,
            String.class,
            int.class
        );
        Constructor<?> constructor = ConstructorArgumentNamesClass.class
            .getDeclaredConstructor(String.class, int.class);
        EzyAutoBind annotation = constructor.getAnnotation(EzyAutoBind.class);

        // when
        String[] names = builder.getArgumentNames(annotation);

        // then
        assertEquals(names, new String[]{"name", "count"});
    }

    @Test
    public void getConstructorArgumentNamesByConstructor() throws Exception {
        // given
        Builder builder = new Builder(
            ConstructorArgumentNamesClass.class,
            String.class,
            int.class
        );
        Constructor<?> constructor = ConstructorArgumentNamesClass.class
            .getDeclaredConstructor(String.class, int.class);

        // when
        String[] names = builder.getArgumentNames(constructor);

        // then
        assertEquals(names, new String[]{"name", "count"});
    }

    public static class AutoBindConstructorClass {

        public AutoBindConstructorClass() {}

        @EzyAutoBind
        public AutoBindConstructorClass(String value) {}
    }

    public static class NoArgsConstructorClass {

        public NoArgsConstructorClass() {}

        public NoArgsConstructorClass(String value) {}
    }

    public static class MaxArgsConstructorClass {

        public MaxArgsConstructorClass(String value) {}

        public MaxArgsConstructorClass(String value, int count) {}
    }

    public static class ConstructorArgumentNamesClass {

        @EzyAutoBind({"name", "count", "redundant"})
        public ConstructorArgumentNamesClass(String value, int count) {}
    }

    private static class Builder extends EzySimpleObjectBuilder {

        private final Class<?>[] constructorParameterTypes;

        Builder(Class<?> type) {
            this(type, new Class[0]);
        }

        Builder(Class<?> type, Class<?>... constructorParameterTypes) {
            super(
                "builder",
                new EzyClass(type),
                new EzyBeanMetadataCache()
            );
            this.constructorParameterTypes = constructorParameterTypes;
        }

        @Override
        protected Class<?>[] getConstructorParameterTypes() {
            return constructorParameterTypes;
        }

        Constructor<?> getConstructor() {
            return super.getConstructor(clazz);
        }

        String[] getArgumentNames(EzyAutoBind annotation) {
            return super.getConstructorArgumentNames(annotation);
        }

        String[] getArgumentNames(Constructor<?> constructor) {
            return super.getConstructorArgumentNames(constructor);
        }
    }
}
