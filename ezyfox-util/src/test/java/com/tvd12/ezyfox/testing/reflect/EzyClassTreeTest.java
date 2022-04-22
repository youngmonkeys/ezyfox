package com.tvd12.ezyfox.testing.reflect;

import java.util.Comparator;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.reflect.EzyClassTree;

public class EzyClassTreeTest {

    @Test
    public void test() {
        EzyClassTree  tree = new EzyClassTree();
        tree = new EzyClassTree(Ex2Branch2Exception.class);
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
        System.out.println(String.join("\n", tree.toList().stream()
                .map(t -> t.toString())
                .collect(Collectors.toList())));
    }

    public static class Compa implements Comparator<Class<?>> {

        @Override
        public int compare(Class<?> a, Class<?> b) {
            if(a == b)
                return 0;
            if(a.isAssignableFrom(b))
                return 1;
            if(b.isAssignableFrom(a))
                return -1;
            return a.getName().compareTo(b.getName());
        }

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
