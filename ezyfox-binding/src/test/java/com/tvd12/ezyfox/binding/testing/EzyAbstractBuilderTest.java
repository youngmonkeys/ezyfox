package com.tvd12.ezyfox.binding.testing;

import com.tvd12.ezyfox.binding.EzyAccessType;
import com.tvd12.ezyfox.binding.impl.EzyAbstractBuilder;
import com.tvd12.ezyfox.binding.impl.EzyElementsFetcher;
import com.tvd12.ezyfox.binding.impl.EzyObjectElementsFetcher;
import com.tvd12.ezyfox.reflect.*;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

public class EzyAbstractBuilderTest extends BaseTest {

    @Test
    public void test() throws Exception {
        Field name = A.class.getDeclaredField("name");
        EzyField field = new EzyField(name);
        EzyElementBuilder builder = new EzyElementBuilder(new EzyClass(A.class));
        assertEquals(builder.getFieldName(field), "name");
        builder.getElementType(null);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void setElementsByClassCacheWithAbstractElementsFetcher() {
        // given
        EzyElementBuilder builder = new EzyElementBuilder(new EzyClass(A.class));
        Map<Class<?>, Map<Class<?>, List<Object>>> cache = new HashMap<>();

        // when
        builder.setElementsByClassCache(cache);
        List<Object> elements = builder.getElements();

        // then
        Map<Class<?>, List<Object>> elementsByClass = cache.get(
            builder.getElementsFetcherClass()
        );
        assertTrue(elementsByClass.containsKey(A.class));
        assertSame(elementsByClass.get(A.class), elements);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void setElementsByClassCacheWithNonAbstractElementsFetcher() {
        // given
        NonAbstractElementsFetcherBuilder builder =
            new NonAbstractElementsFetcherBuilder(new EzyClass(A.class));
        Map<Class<?>, Map<Class<?>, List<Object>>> cache = new HashMap<>();

        // when
        builder.setElementsByClassCache(cache);
        List<Object> elements = builder.getElements();

        // then
        assertTrue(cache.isEmpty());
        assertTrue(elements.isEmpty());
    }

    public static class A {
        public String name;
    }

    public static class EzyElementBuilder extends EzyAbstractBuilder<EzyMethod> {

        public EzyElementBuilder(EzyClass clazz) {
            super(clazz);
        }

        @Override
        public Class getElementType(Object element) {
            return super.getElementType(element);
        }

        @Override
        protected int getAccessType(EzyClass clazz) {
            return EzyAccessType.ALL;
        }

        @Override
        protected EzyObjectElementsFetcher newElementsFetcher() {
            return new EzyObjectElementsFetcher() {

                @Override
                protected List<? extends EzyMethod> getMethodList(EzyClass clazz) {
                    return clazz.getGetterMethods();
                }

                @Override
                protected List<? extends EzyMethod> getDeclaredMethods(EzyClass clazz) {
                    return clazz.getDeclaredGetterMethods();
                }

                @Override
                protected EzyMethod newByFieldMethod(EzyMethod method) {
                    return new EzySetterMethod(method);
                }

                @Override
                protected boolean isValidAnnotatedMethod(EzyMethod method) {
                    return method.getParameterCount() == 1;
                }

            };
        }

        @Override
        public String getFieldName(EzyReflectElement element) {
            return super.getFieldName(element);
        }

        @Override
        public List<Object> getElements() {
            return super.getElements();
        }

        public Class<?> getElementsFetcherClass() {
            return elementsFetcher.getClass();
        }
    }

    public static class NonAbstractElementsFetcherBuilder
        extends EzyAbstractBuilder<EzyMethod> {

        public NonAbstractElementsFetcherBuilder(EzyClass clazz) {
            super(clazz);
        }

        @Override
        protected int getAccessType(EzyClass clazz) {
            return EzyAccessType.ALL;
        }

        @Override
        protected EzyElementsFetcher newElementsFetcher() {
            return (clazz, accessType) -> Collections.emptyList();
        }

        @Override
        public List<Object> getElements() {
            return super.getElements();
        }
    }
}
