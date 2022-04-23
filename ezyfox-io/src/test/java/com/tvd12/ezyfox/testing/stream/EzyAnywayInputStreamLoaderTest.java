package com.tvd12.ezyfox.testing.stream;

import java.io.InputStream;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.stream.EzyAnywayInputStreamLoader;
import com.tvd12.test.base.BaseTest;

public class EzyAnywayInputStreamLoaderTest extends BaseTest {

    @Test
    public void test1() {
        InputStream stream = EzyAnywayInputStreamLoader.builder()
                .classLoader(getClass().getClassLoader())
                .context(getClass())
                .build()
                .load("AllTests.tng.xml");
        assert stream != null;

        stream = new EzyAnywayInputStreamLoader()
                .load("AllTests.tng.xml");
        assert stream != null;
    }

    @Test
    public void test2() {
        InputStream stream = EzyAnywayInputStreamLoader.builder()
                .classLoader(getClass().getClassLoader())
                .context(getClass())
                .build()
                .load("pom.xml");
        assert stream != null;
    }
}
