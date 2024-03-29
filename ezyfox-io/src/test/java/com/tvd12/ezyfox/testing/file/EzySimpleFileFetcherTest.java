package com.tvd12.ezyfox.testing.file;

import com.tvd12.ezyfox.exception.EzyFileNotFoundException;
import com.tvd12.ezyfox.file.EzySimpleFileFetcher;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzySimpleFileFetcherTest extends BaseTest {

    @Test
    public void test() {
        assert new EzySimpleFileFetcher().get("pom.xml") != null;
    }

    @Test(expectedExceptions = {EzyFileNotFoundException.class})
    public void test1() {
        EzySimpleFileFetcher.builder().build().get("fsadfsaf.csdf");
    }
}
