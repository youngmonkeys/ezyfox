package com.tvd12.ezyfox.bean.testing.combine;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import org.testng.annotations.Test;

public class GetSingletonOfErrorBeanKeyTest {

    @Test
    public void test() {
        EzyBeanContext beanContext = EzyBeanContext.builder()
            .addSingletonClass("a", A.class)
            .addSingletonClass("b", B.class)
            .addSingletonClass("c", C.class)
            .addConfigurationClass(Config.class)
            .build();
        assert beanContext.getBean(A.class) != null;
    }

    public interface IA {}

    public interface IB {}

    public interface IC {}

    public interface ID {}

    public static class Config {

        @EzySingleton
        public ID d() {
            return new D();
        }
    }

    public static class A implements IA {

        @EzyAutoBind
        public A(IB b) {}
    }

    public static class B implements IB {

        @EzyAutoBind
        public B(IC c) {}
    }

    public static class C implements IC {

        @EzyAutoBind
        public C(ID d) {
            System.out.println("new c with d: " + d);
        }

        @EzyAutoBind
        public void setA(IA a) {
            System.out.println("set a: " + a);
        }
    }

    public static class D implements ID {}
}
