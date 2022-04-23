package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.constant.EzyConstant;
import com.tvd12.ezyfox.util.EzyEnums;
import com.tvd12.test.base.BaseTest;
import lombok.Getter;
import org.testng.annotations.Test;

import java.util.Map;

public class EzyEnumsTest extends BaseTest {

    @Override
    public Class<?> getTestClass() {
        return EzyEnums.class;
    }

    @Test
    public void test1() {
        assert EzyEnums.valueOf(ABC.values(), 1) == ABC.A;
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void test2() {
        assert EzyEnums.valueOf(ABC.values(), 10) == ABC.A;
    }

    @Test
    public void test3() {
        Map<Integer, ABC> enumMapInt = EzyEnums.enumMapInt(ABC.class);
        assert enumMapInt.size() == 3;
        System.out.println("enumMapInt: " + enumMapInt);
    }

    public static enum ABC implements EzyConstant {

        A(1),
        B(2),
        C(3);

        @Getter
        private int id;

        private ABC(int id) {
            this.id = id;
        }

    }
}
