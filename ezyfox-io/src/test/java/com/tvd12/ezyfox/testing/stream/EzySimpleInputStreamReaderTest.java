package com.tvd12.ezyfox.testing.stream;

import com.tvd12.ezyfox.stream.EzyInputStreamReader;
import com.tvd12.ezyfox.stream.EzySimpleInputStreamReader;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EzySimpleInputStreamReaderTest extends BaseTest {

    @Test
    public void test() {
        EzyInputStreamReader reader = EzySimpleInputStreamReader.builder()
            .build();
        reader.readBytes(new InputStream() {
            int index = 0;
            private final byte[] bytes = new byte[]{1, 2, 3};

            @Override
            public int read() {
                if (index >= bytes.length) {
                    return -1;
                }
                return bytes[index++];
            }
        });
    }

    @Test
    public void test1() {
        EzyInputStreamReader reader = EzySimpleInputStreamReader.builder()
            .build();
        reader.readString(new InputStream() {
            int index = 0;
            private final byte[] bytes = new byte[]{1, 2, 3};

            @Override
            public int read() {
                if (index >= bytes.length) {
                    return -1;
                }
                return bytes[index++];
            }
        }, "UTF-8");
    }

    @Test
    public void test2() {
        EzyInputStreamReader reader = EzySimpleInputStreamReader.builder()
            .build();
        char[] chars = reader.readChars(new InputStream() {
            int index = 0;
            private final byte[] bytes = new byte[]{1, 2, 3};

            @Override
            public int read() {
                if (index >= bytes.length) {
                    return -1;
                }
                return bytes[index++];
            }
        }, "UTF-8");
        Asserts.assertEquals(chars, new char[] {1, 2, 3});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test3() throws IOException {
        EzyInputStreamReader reader = EzySimpleInputStreamReader.builder()
            .build();
        InputStream inputStream = mock(InputStream.class);
        when(inputStream.read(any(byte[].class))).thenThrow(new IOException());
        when(inputStream.read(any(byte[].class), any(int.class), any(int.class))).thenThrow(new IOException());
        reader.readBytes(inputStream);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test4() throws IOException {
        EzyInputStreamReader reader = EzySimpleInputStreamReader.builder()
            .build();
        InputStream inputStream = mock(InputStream.class);
        when(inputStream.read(any(byte[].class))).thenThrow(new IOException());
        when(inputStream.read(any(byte[].class), any(int.class), any(int.class))).thenThrow(new IOException());
        reader.readLines(inputStream, "UTF-8");
    }
}
