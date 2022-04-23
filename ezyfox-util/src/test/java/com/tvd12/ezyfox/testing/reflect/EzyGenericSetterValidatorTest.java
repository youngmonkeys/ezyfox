package com.tvd12.ezyfox.testing.reflect;

import com.tvd12.ezyfox.reflect.EzyGenericSetterValidator;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.lang.reflect.Type;

public class EzyGenericSetterValidatorTest extends BaseTest {

    @Test
    public void test() {
        new EzyGenericSetterValidator().validate(new MyType());
    }

    public static class MyType implements Type {}
}
