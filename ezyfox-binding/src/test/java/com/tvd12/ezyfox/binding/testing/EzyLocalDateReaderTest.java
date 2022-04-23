package com.tvd12.ezyfox.binding.testing;

import java.time.LocalDate;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.binding.reader.EzyLocalDateReader;
import com.tvd12.ezyfox.io.EzyDates;

public class EzyLocalDateReaderTest {

    @Test
    public void test() {
        LocalDate value = LocalDate.now();
        System.out.println(EzyDates.format(value, "yyyy-MM-dd"));
        assert EzyLocalDateReader.getInstance().read(null, value) == value;
        assert EzyLocalDateReader.getInstance().read(null, System.currentTimeMillis()).compareTo(value) >= 0;
        assert EzyLocalDateReader.getInstance().read(null, EzyDates.format(value, "yyyy-MM-dd")).equals(value);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test1() {
        EzyLocalDateReader.getInstance().read(null, getClass());
    }
}
