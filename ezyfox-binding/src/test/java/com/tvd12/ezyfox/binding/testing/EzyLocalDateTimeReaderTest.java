package com.tvd12.ezyfox.binding.testing;

import java.time.LocalDateTime;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.binding.reader.EzyLocalDateTimeReader;
import com.tvd12.ezyfox.io.EzyDates;

public class EzyLocalDateTimeReaderTest {

    @Test
    public void test() {
        LocalDateTime value = LocalDateTime.now();
        assert EzyLocalDateTimeReader.getInstance().read(null, value) == value;
        assert EzyLocalDateTimeReader.getInstance().read(null, System.currentTimeMillis()).compareTo(value) >= 0;
        assert EzyLocalDateTimeReader.getInstance().read(null, EzyDates.format(value)).equals(value);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test1() {
        EzyLocalDateTimeReader.getInstance().read(null, getClass());
    }
}