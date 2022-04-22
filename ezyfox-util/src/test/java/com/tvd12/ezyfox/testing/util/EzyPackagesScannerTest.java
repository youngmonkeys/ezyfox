package com.tvd12.ezyfox.testing.util;

import java.util.Arrays;
import java.util.Set;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.util.EzyPackagesScanner;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;

public class EzyPackagesScannerTest extends BaseTest {

    @Test
    public void test() {
        // given
        ExPackagesScanner sut = new ExPackagesScanner()
                .scan("a")
                .scan("b", "c")
                .scan(Arrays.asList("d", "e"));
        Asserts.assertEquals(Sets.newHashSet("a", "b", "c", "d", "e"), sut.getPackagesToScan());
    }

    private static class ExPackagesScanner extends EzyPackagesScanner<ExPackagesScanner> {
        public Set<String> getPackagesToScan() {
            return packagesToScan;
        }
    }
}