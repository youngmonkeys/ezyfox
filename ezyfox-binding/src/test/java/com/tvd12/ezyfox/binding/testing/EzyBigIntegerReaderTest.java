package com.tvd12.ezyfox.binding.testing;

import java.math.BigInteger;
import java.util.Date;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.binding.reader.EzyBigIntegerReader;

public class EzyBigIntegerReaderTest {

    @Test
    public void test() {
        BigInteger value = new BigInteger("10");
        assert EzyBigIntegerReader.getInstance().read(null, value) == value;
        assert EzyBigIntegerReader.getInstance().read(null, 10).equals(value);
        assert EzyBigIntegerReader.getInstance().read(null, "10").equals(value);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test2() {
        EzyBigIntegerReader.getInstance().read(null, new Date());
    }

}
