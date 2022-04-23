package com.tvd12.ezyfox.testing.stream;

import com.tvd12.ezyfox.stream.EzyInputStreams;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class EzyInputStreamsTest extends BaseTest {

    @Override
    public Class<?> getTestClass() {
        return EzyInputStreams.class;
    }

    @Test
    public void toLinesTest1() throws IOException {
        String initialString = "text\nhello";
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        List<String> lines = EzyInputStreams.toLines(targetStream);
        assert lines.size() == 2;
        targetStream = new ByteArrayInputStream(initialString.getBytes());
        String utf8 = EzyInputStreams.toStringUtf8(targetStream);
        assert utf8.equals(initialString);
    }
}
