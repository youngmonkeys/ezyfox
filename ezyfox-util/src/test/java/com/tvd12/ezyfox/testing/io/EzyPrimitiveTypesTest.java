package com.tvd12.ezyfox.testing.io;

import com.tvd12.ezyfox.io.EzyPrimitiveTypes;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyPrimitiveTypesTest extends BaseTest {

    @Test
    public void test() {
        EzyPrimitiveTypes.toList((boolean[]) null);
        EzyPrimitiveTypes.toList((byte[]) null);
        EzyPrimitiveTypes.toList((char[]) null);
        EzyPrimitiveTypes.toList((double[]) null);
        EzyPrimitiveTypes.toList((float[]) null);
        EzyPrimitiveTypes.toList((int[]) null);
        EzyPrimitiveTypes.toList((long[]) null);
        EzyPrimitiveTypes.toList((short[]) null);
    }

    @Override
    public Class<?> getTestClass() {
        return EzyPrimitiveTypes.class;
    }
}
