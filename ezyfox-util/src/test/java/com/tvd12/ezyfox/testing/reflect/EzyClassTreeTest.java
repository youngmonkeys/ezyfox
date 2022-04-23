package com.tvd12.ezyfox.testing.reflect;

import com.tvd12.ezyfox.reflect.EzyClassTree;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class EzyClassTreeTest {

    @Test
    public void test() {
        EzyClassTree tree = new EzyClassTree(Ex2Branch2Exception.class);
        tree.add(Ex2Branch2Exception.class);
        tree.add(Ex2Branch1Exception.class);
        tree.add(Ex1Branch2Exception.class);
        tree.add(Ex1Branch1Exception.class);
        tree.add(Branch2Exception.class);
        tree.add(Branch1Exception.class);
        tree.add(Exception.class);
        tree.add(RootException.class);
        tree.add(RootException.class);
        System.out.println(tree);

        System.out.println("\n================================");
        System.out.println(tree.toList().stream()
            .map(Class::toString)
            .collect(Collectors.joining("\n")));
    }

    @Test
    public void defaultConstructor() {
        // given
        EzyClassTree sut = new EzyClassTree();

        // when
        List<Class<?>> actual = sut.toList();

        // then
        Asserts.assertEmpty(actual);
    }

    public static class Ex2Branch2Exception extends Branch2Exception {
        private static final long serialVersionUID = -6068208036555261492L;
    }

    public static class Ex1Branch2Exception extends Branch2Exception {
        private static final long serialVersionUID = -6068208036555261492L;
    }

    public static class Ex2Branch1Exception extends Branch1Exception {
        private static final long serialVersionUID = -6068208036555261492L;
    }

    public static class Ex1Branch1Exception extends Branch1Exception {
        private static final long serialVersionUID = -6068208036555261492L;
    }

    public static class Branch2Exception extends RootException {
        private static final long serialVersionUID = -6068208036555261492L;
    }

    public static class Branch1Exception extends RootException {
        private static final long serialVersionUID = -6068208036555261492L;
    }

    public static class RootException extends Exception {
        private static final long serialVersionUID = -6068208036555261492L;
    }
}
