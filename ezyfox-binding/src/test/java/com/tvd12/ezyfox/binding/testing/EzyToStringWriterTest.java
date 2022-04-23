package com.tvd12.ezyfox.binding.testing;

import com.tvd12.ezyfox.binding.writer.EzyToStringWriter;
import org.testng.annotations.Test;

public class EzyToStringWriterTest {

    @Test
    public void test() {
        assert EzyToStringWriter.getInstance().write(null, 10).equals("10");
    }
}
