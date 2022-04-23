package com.tvd12.ezyfox.testing.stream;

import com.tvd12.ezyfox.stream.EzyFileListInputStream;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EzyFileListInputStreamTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void emptyFilesTest() throws IOException {
        InputStream sut = new EzyFileListInputStream(Collections.emptyList());
        sut.close();
    }

    @Test
    public void listFilesTest() throws IOException {
        // given
        List<File> files = Arrays.asList(new File("pom.xml"), new File("pom.xml"));
        InputStream sut = new EzyFileListInputStream(files);

        // when
        while (true) {
            if (sut.read() <= 0) {
                break;
            }
        }

        // then
        Asserts.assertEquals(sut.read(), -1);
        sut.close();
    }

    @SuppressWarnings("ALL")
    @Test
    public void colseWhenReadTest() throws IOException {
        // given
        List<File> files = Arrays.asList(new File("pom.xml"), new File("pom.xml"));
        InputStream sut = new EzyFileListInputStream(files);

        // when
        sut.read();

        // then
        sut.close();
        Throwable e = Asserts.assertThrows(sut::read);
        Asserts.assertEquals(e.getClass(), IOException.class);
        sut.close();
    }
}
