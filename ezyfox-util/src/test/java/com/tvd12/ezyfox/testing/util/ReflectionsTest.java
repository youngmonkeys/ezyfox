package com.tvd12.ezyfox.testing.util;

import com.tvd12.reflections.Reflections;
import org.testng.annotations.Test;

import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.test.base.BaseTest;

public class ReflectionsTest extends BaseTest {

    @Test
    public void test() {
        new Reflections("non-exists").getTypesAnnotatedWith(EzyAutoImpl.class);
    }

}
