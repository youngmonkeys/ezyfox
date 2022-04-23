package com.tvd12.ezyfox.testing.file;

import com.tvd12.ezyfox.file.EzyFileReader;
import com.tvd12.ezyfox.file.EzyFileWriter;
import com.tvd12.ezyfox.file.EzySimpleFileReader;
import com.tvd12.ezyfox.file.EzySimpleFileWriter;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.io.File;

public class EzySimpleFileReaderTest extends BaseTest {

    private final File directory = new File("test");
    private final EzyFileWriter writer = EzySimpleFileWriter.builder().build();
    private final EzyFileReader reader = EzySimpleFileReader.builder().build();

    @SuppressWarnings("ALL")
    public EzySimpleFileReaderTest() {
        super();
        directory.mkdirs();
    }

    @Test
    public void test() {
        File file = new File(directory.getAbsolutePath() + File.separator + "EzySimpleFileWriterTest.txt");
        writer.write(file, new byte[]{'a', 'b', 'c'});
        reader.readBytes(file);
        assert new EzySimpleFileReader().readLines(file, "UTF-8").size() > 0;
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void test1() {
        reader.readBytes(new File("\\/xfav"));
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void test2() {
        reader.readLines(new File("\\/xfav"), "i dont know");
    }
}
