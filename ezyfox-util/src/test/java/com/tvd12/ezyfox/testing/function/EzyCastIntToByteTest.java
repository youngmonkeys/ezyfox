package com.tvd12.ezyfox.testing.function;

import com.tvd12.ezyfox.io.EzyCastToByte;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyCastIntToByteTest extends BaseTest {

    @Test
    public void test() {
        EzyCastToByte cast = new EzyCastToByte() {};
        assert cast.cast(100) == (byte) 100;
        assert cast.cast(100L) == (byte) 100;
    }
}
