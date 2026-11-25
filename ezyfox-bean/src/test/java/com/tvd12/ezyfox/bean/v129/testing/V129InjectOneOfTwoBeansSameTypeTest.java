package com.tvd12.ezyfox.bean.v129.testing;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.v129.testing.sametype.V129Holder;
import com.tvd12.ezyfox.bean.v129.testing.sametype.V129Value;
import com.tvd12.ezyfox.bean.v129.testing.sametype.V129Wrapper;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

public class V129InjectOneOfTwoBeansSameTypeTest {

    @Test
    public void test() {
        // given
        EzyBeanContext beanContext = EzyBeanContext.builder()
            .scan("com.tvd12.ezyfox.bean.v129.testing.sametype")
            .build();

        // when
        V129Holder holder = beanContext.getBeanCast(V129Holder.class);
        V129Value first = (V129Value) beanContext.getBean("first", V129Value.class);
        V129Value second = (V129Value) beanContext.getBean("second", V129Value.class);
        V129Wrapper wrapper = beanContext.getBeanCast(V129Wrapper.class);

        // then
        Asserts.assertEquals(first.getValue(), "first");
        Asserts.assertEquals(second.getValue(), "second");
        Asserts.assertEquals(holder.first, first);
        Asserts.assertEquals(holder.second, second);
        Asserts.assertTrue(
            wrapper.getValue() == first || wrapper.getValue() == second
        );
    }
}
