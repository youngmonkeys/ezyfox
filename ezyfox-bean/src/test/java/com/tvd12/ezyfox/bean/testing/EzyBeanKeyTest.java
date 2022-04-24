package com.tvd12.ezyfox.bean.testing;

import com.tvd12.ezyfox.bean.impl.EzyBeanKey;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyBeanKeyTest extends BaseTest {

    @Test
    public void test() {
        // given
        EzyBeanKey sut = new EzyBeanKey("hello", getClass());

        // when
        // then
        Asserts.assertEquals(sut.getName(), "hello");
        Asserts.assertEquals(sut.getType(), getClass());
        System.out.println(sut);
    }
}
