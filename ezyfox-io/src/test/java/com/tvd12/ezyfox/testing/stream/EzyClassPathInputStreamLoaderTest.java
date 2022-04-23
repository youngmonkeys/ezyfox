package com.tvd12.ezyfox.testing.stream;

import com.tvd12.ezyfox.stream.EzyClassPathInputStreamLoader;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyClassPathInputStreamLoaderTest extends BaseTest {

    @Test
    public void test1() {
        assert EzyClassPathInputStreamLoader.builder()
            .context(getClass())
            .build()
            .load("fasfasdf.afdsf") == null;

        assert new EzyClassPathInputStreamLoader()
            .load("fasfasdf.afdsf") == null;
    }
}
