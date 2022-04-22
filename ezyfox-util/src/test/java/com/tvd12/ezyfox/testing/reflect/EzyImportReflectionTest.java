package com.tvd12.ezyfox.testing.reflect;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.annotation.EzyImport;
import com.tvd12.ezyfox.annotation.EzyPackagesToScan;
import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.reflect.EzyImportReflection;
import com.tvd12.ezyfox.reflect.EzyReflection;
import com.tvd12.test.assertion.Asserts;

public class EzyImportReflectionTest {

    @Test
    public void test() {
        // given
        EzyReflection reflection = new EzyImportReflection(Sets.newHashSet(Config.class));


        // when then
        Asserts.assertThat(reflection.getAnnotatedClasses(EzyAutoImpl.class).size())
            .isEqualsTo(1);
        Asserts.assertThat(reflection.getAnnotatedClasses(EzyPackagesToScan.class).size())
            .isEqualsTo(1);
        Asserts.assertThat(reflection.getAnnotatedClasses(EzyId.class).size())
            .isEqualsTo(0);
        Asserts.assertThat(reflection.getExtendsClasses(B.class).size())
            .isEqualsTo(1);
        Asserts.assertThat(reflection.getExtendsClasses(EzyImportReflectionTest.class).size())
            .isEqualsTo(0);
    }

    @Test
    public void testWithNoImport() {
        // given
        EzyReflection reflection = new EzyImportReflection(Sets.newHashSet(A.class));


        // when then
        Asserts.assertThat(reflection.getAnnotatedClasses(EzyAutoImpl.class).size())
            .isEqualsTo(0);
    }

    @EzyImport(A.class)
    private static class Config {
    }

    @EzyAutoImpl
    @EzyPackagesToScan("")
    private static class A extends B {
    }

    private static class B {
    }
}