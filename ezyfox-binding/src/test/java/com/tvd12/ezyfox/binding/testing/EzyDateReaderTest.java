package com.tvd12.ezyfox.binding.testing;

import com.tvd12.ezyfox.binding.reader.EzyDateReader;
import com.tvd12.ezyfox.io.EzyDates;
import org.testng.annotations.Test;

import java.util.Date;

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
