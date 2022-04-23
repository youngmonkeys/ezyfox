package com.tvd12.ezyfox.testing.file;

import com.tvd12.ezyfox.file.EzyAnywayFileFetcher;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.io.File;

public class EzyAnywayFileFetcherTest extends BaseTest {

    @Test
    public void test1() {
        File file = EzyAnywayFileFetcher.builder()
            .classLoader(getClass().getClassLoader())
            .context(getClass())
            .build()
            .get("AllTests.tng.xml");
        assert file != null;
        file = new EzyAnywayFileFetcher()
            .get("AllTests.tng.xml");
        assert file != null;
    }

    @Test
    public void test2() {
        File file = EzyAnywayFileFetcher.builder()
            .classLoader(getClass().getClassLoader())
            .context(getClass())
            .build()
            .get("pom.xml");
        assert file != null;
    }
}
