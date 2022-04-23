package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.reflections.Reflections;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class ReflectionsTest extends BaseTest {

    @Test
    public void test() {
        new Reflections("non-exists").getTypesAnnotatedWith(EzyAutoImpl.class);
    }
}
