package com.tvd12.ezyfox.binding.testing;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.binding.writer.EzyToStringWriter;

public class EzyToStringWriterTest {

    @Test
    public void test() {
        assert EzyToStringWriter.getInstance().write(null, 10).equals("10");
    }
}