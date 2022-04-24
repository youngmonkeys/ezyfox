package com.tvd12.ezyfox.testing.tool;

import com.tvd12.ezyfox.tool.EzyFileListWalker;
import com.tvd12.ezyfox.tool.EzyFileMerger;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Path;

public class EzyFileMergerTest {

    @Test
    public void test() {
        EzyFileListWalker fileListWalker = EzyFileListWalker.builder()
            .addDirectory("src/test/resources", 1)
            .fileFilter(Files::isRegularFile)
            .build();
        EzyFileMerger merger = EzyFileMerger.builder()
            .fileListWalker(fileListWalker)
            .comment("//")
            .build();
        Path path = merger.merge("test-output/EzyFileMergerTest.txt");
        System.out.print("has merge all files to: " + path.toAbsolutePath());
    }
}
