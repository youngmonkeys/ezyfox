package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.util.EzyUriValidator;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyUriValidatorTest extends BaseTest {

    @Override
    public Class<?> getTestClass() {
        return EzyUriValidator.class;
    }

    @Test
    public void test() {
        assert EzyUriValidator.validateUri("http://127.0.0.1:8080");
        assert !EzyUriValidator.validateUri("::::::\\127.0.0.01.");
    }
}
