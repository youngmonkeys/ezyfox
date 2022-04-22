package com.tvd12.ezyfox.testing.reflect;

import com.tvd12.ezyfox.reflect.EzyMethods;

public class FetchMethodTest {

    public static void main(String[] args) throws Exception {
        System.out.println(EzyMethods.getMethods(B.class));
    }

    public static class B extends A {
        @Override
        public void setValue() {
            super.setValue();
        }
    }

    public static class A {
        public void setValue() {
        }
    }

}
