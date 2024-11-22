package com.tvd12.ezyfox.bean.testing;

import com.tvd12.ezyfox.bean.impl.EzyBeanKey;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import com.tvd12.test.performance.Performance;
import org.testng.annotations.Test;

public class EzyBeanKeyTest extends BaseTest {

    public static void main(String[] args) {
        EzyBeanKey hello = new EzyBeanKey("hello", String.class);
        EzyBeanKey world = new EzyBeanKey("world", String.class);
        long equalsTime = Performance.create()
            .test(() -> hello.equals(world))
            .getTime();
        System.out.println("equalsTime: " + equalsTime);

        long hashCodeTime = Performance.create()
            .test(hello::hashCode)
            .getTime();
        System.out.println("hashCodeTime: " + hashCodeTime);
    }

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
