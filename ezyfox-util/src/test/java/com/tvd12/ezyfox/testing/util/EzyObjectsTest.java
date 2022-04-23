package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.util.EzyObjects;
import com.tvd12.test.base.BaseTest;
import lombok.EqualsAndHashCode;
import org.testng.annotations.Test;

public class EzyObjectsTest extends BaseTest {

    @Override
    public Class<?> getTestClass() {
        return EzyObjects.class;
    }

    @Test
    public void test() {
        assert EzyObjects.equals(null, null);
        assert !EzyObjects.equals(null, this);
        assert !EzyObjects.equals(this, null);
        assert EzyObjects.equals(new ClassA(), new ClassA());
    }

    @EqualsAndHashCode
    public static class ClassA {
    }
}
