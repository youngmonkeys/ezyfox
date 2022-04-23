package com.tvd12.ezyfox.binding.testing;

import com.tvd12.ezyfox.binding.reader.EzyLocalDateTimeReader;
import com.tvd12.ezyfox.io.EzyDates;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class EzyLocalDateTimeReaderTest {

    @Test
    public void test() {
        LocalDateTime value = LocalDateTime.now();
        assert EzyLocalDateTimeReader.getInstance()
            .read(null, value) == value;
        long currentMillis = System.currentTimeMillis();

        assert EzyLocalDateTimeReader.getInstance()
            .read(null, currentMillis)
            .toInstant(ZoneOffset.UTC)
            .toEpochMilli() >= value.toInstant(ZoneOffset.UTC).toEpochMilli();

        Asserts.assertEquals(
            EzyLocalDateTimeReader.getInstance().read(null, EzyDates.format(value))
                .toInstant(ZoneOffset.UTC).toEpochMilli(),
            value.toInstant(ZoneOffset.UTC).toEpochMilli()
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test1() {
        EzyLocalDateTimeReader.getInstance().read(null, getClass());
    }
}
