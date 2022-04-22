package com.tvd12.ezyfox.testing.reflect;

import java.util.Set;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.reflect.EzyReflection;
import com.tvd12.ezyfox.reflect.EzyReflectionProxy;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;

public class EzyReflectionProxyTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test() {
        new EzyReflectionProxy("com.tvd12.ezyfox.testing.reflect");
        new EzyReflectionProxy(Sets.newHashSet("com.tvd12.ezyfox.testing.reflect"));
        new EzyReflectionProxy(Lists.newArrayList("com.tvd12.ezyfox.testing.reflect"));
        new EzyReflectionProxy("com.tvd12.ezyfox.testing.reflect", getClass().getClassLoader());
        new EzyReflectionProxy(Sets.newHashSet("com.tvd12.ezyfox.testing.reflect"), getClass().getClassLoader());
        new EzyReflectionProxy(Lists.newArrayList("com.tvd12.ezyfox.testing.reflect"), getClass().getClassLoader());
        EzyReflection reflection = new EzyReflectionProxy("com.tvd12.ezyfox.testing.reflect");
        Asserts.assertFalse(reflection.getExtendsClasses(BaseTest.class).isEmpty());
        reflection.getAnnotatedClasses(ExampleAnnotation.class);
        assert reflection.getAnnotatedClasses(ExampleAnnotation.class).size() == reflection.getAnnotatedClasses(Sets.newHashSet(ExampleAnnotation.class)).size();
        assert reflection.getAnnotatedClass(ExampleAnnotation.class) != null;
        assert reflection.getExtendsClass(BaseTest.class) != null;
        assert reflection.getAnnotatedClass(ExampleAnnotation2.class) == null;
        assert reflection.getExtendsClass(EzyReflectionProxyTest.class) == null;
    }

    @Test
    public void getAnnotatedExtendsClassesTest() {
        // given
        EzyReflection reflection = new EzyReflectionProxy("com.tvd12.ezyfox.testing.reflect");

        // when
        Set<Class<?>> annotatedExtendsClasses = reflection.getAnnotatedExtendsClasses(ExampleAnnotation.class, I.class);

        // then
        Asserts.assertEquals(annotatedExtendsClasses.size(), 1);
    }

    public static interface I {
    }

    @ExampleAnnotation
    public static class A implements I {
    }}