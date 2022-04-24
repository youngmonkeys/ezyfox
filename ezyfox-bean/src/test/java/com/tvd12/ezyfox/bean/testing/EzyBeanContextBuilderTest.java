package com.tvd12.ezyfox.bean.testing;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.util.RandomUtil;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.Set;

public class EzyBeanContextBuilderTest {

    @Test
    public void excludePackageTest() {
        // given
        String package1 = RandomUtil.randomShortAlphabetString();
        String package2 = RandomUtil.randomShortAlphabetString();
        EzyBeanContext beanContext = EzyBeanContext.builder()
            .scan(package1, package2)
            .excludePackage(package1)
            .build();

        // when
        Set<String> actual = beanContext.getPackagesToScan();

        // then
        Asserts.assertEquals(actual, Collections.singleton(package2), false);
    }
}
