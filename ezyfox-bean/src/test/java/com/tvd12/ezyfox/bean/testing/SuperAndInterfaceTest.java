package com.tvd12.ezyfox.bean.testing;

import java.util.Arrays;

public class SuperAndInterfaceTest {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(B.class.getInterfaces()));
    }

    public interface A {}

    public interface B extends A {}
}
