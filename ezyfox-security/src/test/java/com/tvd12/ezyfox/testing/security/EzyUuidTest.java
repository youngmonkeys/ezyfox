package com.tvd12.ezyfox.testing.security;

import com.tvd12.ezyfox.security.EzyUuid;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyUuidTest extends BaseTest {

    @Override
    public Class<?> getTestClass() {
        return EzyUuid.class;
    }

    @Test
    public void test() {
        assert !EzyUuid.random().equals(EzyUuid.random());
    }
}
