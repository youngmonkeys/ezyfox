package com.tvd12.ezyfox.binding.testing;

import com.tvd12.ezyfox.binding.reader.EzyBigDecimalReader;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;

public class EzyBigDecimalReaderTest {

    @Test
    public void test() {
        BigDecimal value = new BigDecimal("10.1");
        assert EzyBigDecimalReader.getInstance()
            .read(null, value) == value;
        assert EzyBigDecimalReader.getInstance()
            .read(null, 10.1D)
            .compareTo(new BigDecimal("10.1")) == 0;
        assert EzyBigDecimalReader.getInstance().read(null, 10.1F)
            .compareTo(BigDecimal.valueOf(10.1F)) == 0;
        assert EzyBigDecimalReader.getInstance().read(null, 10L)
            .compareTo(new BigDecimal("10.0")) == 0;
        assert EzyBigDecimalReader.getInstance().read(null, "10.1")
            .compareTo(value) == 0;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test1() {
        EzyBigDecimalReader.getInstance().read(null, new Date());
    }
}
