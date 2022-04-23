package com.tvd12.ezyfox.binding.testing;

import java.util.Date;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.binding.reader.EzyDateReader;
import com.tvd12.ezyfox.io.EzyDates;

public class EzyDateReaderTest {

    @Test
    public void test() {
        Date value = new Date();
        assert EzyDateReader.getInstance().read(null, value) == value;
        assert EzyDateReader.getInstance().read(null, value.getTime()).equals(value);
        assert EzyDateReader.getInstance().read(null, EzyDates.format(value)).equals(value);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test1() {
        EzyDateReader.getInstance().read(null, getClass());
    }
}
