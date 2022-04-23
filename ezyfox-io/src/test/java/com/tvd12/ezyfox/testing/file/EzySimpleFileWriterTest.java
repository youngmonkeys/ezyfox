package com.tvd12.ezyfox.testing.file;

import com.tvd12.ezyfox.file.EzyFileWriter;
import com.tvd12.ezyfox.file.EzySimpleFileWriter;
import com.tvd12.test.base.BaseTest;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class EzySimpleFileWriterTest extends BaseTest {

    private final File directory = new File("test");
    private final EzyFileWriter writer = EzySimpleFileWriter.builder().build();

    @SuppressWarnings("ALL")
    public EzySimpleFileWriterTest() {
        super();
        directory.mkdirs();
    }

    @Test
    public void test() {
        File file = new File(directory.getAbsolutePath() + File.separator + "EzySimpleFileWriterTest.txt");
        writer.write(file, new byte[]{'a', 'b', 'c'});
        new EzySimpleFileWriter().write(file, new byte[]{'a', 'b', 'c'});
    }

    @SuppressWarnings("ALL")
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void test1() throws IOException {
        File file = new File("test/cant_write.txt");
        file.createNewFile();
        file.setWritable(false);
        writer.write(file, new byte[]{'a', 'b', 'c'});
    }

    @Test
    public void test2() {
        File file = new File(
            directory.getAbsolutePath() +
                File.separator + "EzySimpleFileWriterTest.txt"
        );
        writer.write(file, new InputStream() {
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
    public void test3() {
        File file = new File(directory.getAbsolutePath() + File.separator + "EzySimpleFileWriterTest.txt");
        writer.write(file, "abc", "UTF-8");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test4() {
        File file = new File("");
        FileInputStream inputStream = Mockito.mock(FileInputStream.class);
        writer.write(file, inputStream);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test5() {
        File file = new File("");
        writer.write(file, "", StandardCharsets.UTF_8);
    }
}
