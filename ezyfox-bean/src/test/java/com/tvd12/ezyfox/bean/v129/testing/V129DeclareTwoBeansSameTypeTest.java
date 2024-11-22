package com.tvd12.ezyfox.bean.v129.testing;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

public class V129DeclareTwoBeansSameTypeTest {

    @Test
    public void test() {
        // given
        EzyBeanContext beanContext = EzyBeanContext.builder()
            .scan("com.tvd12.ezyfox.bean.v129.testing.config")
            .build();

        // when
        // then
        Asserts.assertEquals(
            beanContext.getBean("hello", String.class),
            "hello"
        );
        Asserts.assertEquals(
            beanContext.getBean("world", String.class),
            "world"
        );
        Asserts.assertEquals(
            beanContext.getBean("foo", String.class),
            "foo"
        );
        Asserts.assertEquals(
            beanContext.getBean("bar", String.class),
            "bar"
        );
    }
}
