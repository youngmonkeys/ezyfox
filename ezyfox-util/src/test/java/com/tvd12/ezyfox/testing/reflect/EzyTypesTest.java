package com.tvd12.ezyfox.testing.reflect;

import com.tvd12.ezyfox.reflect.EzyTypes;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyTypesTest extends BaseTest {

    @Test
    public void test() {
        System.out.println(EzyTypes.BOOLEAN_TYPES);
    }

    @Override
    public Class<?> getTestClass() {
        return EzyTypes.class;
    }
}
