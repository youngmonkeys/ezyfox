package com.tvd12.ezyfox.testing.constant;

import com.tvd12.ezyfox.constant.EzyAttribute;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyAttributeTest extends BaseTest {

    @Test
    public void test() {
        EzyAttribute A = EzyAttribute.valueOf(1, "a");
        assert A.getName().equals("a");
        EzyAttribute B = EzyAttribute.valueOf(2);
        assert B.getName().equals("attribute#2");
        EzyAttribute C = EzyAttribute.valueOf("c");
        assert C.getId() > 2;
        EzyAttribute D = EzyAttribute.one();
        System.err.println("D.id = " + D.getId() + ", D.name = " + D.getName());
    }
}
