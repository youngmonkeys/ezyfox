package com.tvd12.ezyfox.testing.sercurity;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.sercurity.EzyBase64;
import com.tvd12.test.base.BaseTest;
import static org.testng.Assert.*;

public class EzyBase64Test extends BaseTest {

    @Override
    public Class<?> getTestClass() {
        return EzyBase64.class;
    }

    @Test
    public void test() {
        byte[] encode = EzyBase64.encode("dungtv");
        assertEquals(EzyBase64.decode(encode), EzyStrings.getUtfBytes("dungtv"));
        assertEquals(EzyBase64.decode2utf(encode), "dungtv");

        String encodeUtf = EzyBase64.encodeUtf("dungtv");
        assertEquals(EzyBase64.decodeUtf(encodeUtf), "dungtv");

        double[] doubleArray = new double[] {1, 2, 3};
        String doubleArrayEncode = EzyBase64.encode(doubleArray);
        assertEquals(EzyBase64.decode2doubles(doubleArrayEncode), doubleArray);
    }

}
