package com.tvd12.ezyfox.testing.reflect;

import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import lombok.Getter;
import lombok.Setter;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class EzyClassTest extends BaseTest {

    @Test
    public void test() throws Exception {
        EzyClass clazz = new EzyClass(A.class);
        System.out.println("test classA methods: " + clazz.getMethods());
        assertEquals(clazz.getName(), A.class.getName());
        assertTrue(clazz.isAnnotated(ExampleAnnotation.class));
        ExampleAnnotation ann = clazz.getAnnotation(ExampleAnnotation.class);
        assertNotNull(ann);
        assertEquals(clazz.getPublicMethods().size(), 10);
        assertEquals(clazz.getSetterMethods().size(), 5);
        assertEquals(clazz.getGetterMethods().size(), 5);
        assertEquals(clazz.getPublicMethods(m -> !m.getName().contains("D")).size(), 8);
        assertEquals(clazz.getSetterMethods(m -> !m.getName().contains("D")).size(), 4);
        assertEquals(clazz.getGetterMethods(m -> !m.getName().contains("D")).size(), 4);
        assertEquals(clazz.getGetterMethods(m -> !m.getName().contains("D")).size(), 4);
        assertNotNull(clazz.getField(f -> f.getName().equals("a")));
        assertNotNull(clazz.getMethod(m -> m.getName().equals("getE")));
        assertNotNull(clazz.getPublicMethod(m -> m.getName().equals("getE")));
        System.err.println("size = " + clazz.getMethods(m -> !m.getName().contains("D")).size());
        Asserts.assertTrue(
            clazz.getMethods(m -> !m.getName().contains("D")).size() >= 9
        );
        Asserts.assertTrue(
            clazz.getFields().size() >= 10
        );
        Asserts.assertTrue(
            clazz.getWritableFields().size() >= 9
        );
        assertEquals(clazz.getPublicFields().size(), 4);
        assertEquals(clazz.getPublicFields(f -> !f.getName().equals("a")).size(), 3);
        Asserts.assertTrue(
            clazz.getFields(f -> !f.getName().equals("a")).size() >= 9
        );
        clazz.getMethods(m -> !m.getName().contains("D"), m -> new EzyMethod(m.getMethod()));
        assertEquals(A.class.toString(), clazz.toString());
        assertEquals(clazz.getClazz(), A.class);
        Asserts.assertTrue(clazz.getMethods().size() >= 11);

        Asserts.assertTrue(clazz.getDeclaredFields().size() >= 6);
        Asserts.assertTrue(clazz.getDeclaredMethods().size() >= 6);
        System.err.println("fields = " + clazz.getDeclaredFields());
        System.err.println("methods = " + clazz.getDeclaredMethods());
        assert clazz.getField("x") != null;
        clazz.getDeclaredMethods(m -> true);
        clazz.getDeclaredSetterMethods();
        clazz.getDeclaredGetterMethods();
        Asserts.assertTrue(clazz.getFieldsByName().size() > 0);
        Asserts.assertTrue(clazz.getMethodsByName().size() > 0);
        clazz.getMethod("setY");
        clazz.newInstance();
        clazz.getDeclaredConstructors();
        Asserts.assertEquals(
            clazz.getDeclaredConstructor(),
            A.class.getDeclaredConstructor()
        );

        Asserts.assertEquals(clazz.getModifiers(), clazz.getClazz().getModifiers());

        EzyClass clazzX = new EzyClass(AX.class);
        assertEquals(clazzX.getAnnotatedMethods(ExampleAnnotation.class).size(), 4);
        assertTrue(clazzX.getAnnotatedMethod(ExampleAnnotation.class).isPresent());
        assertTrue(clazzX.getAnnotatedSetterMethod(ExampleAnnotation.class).isPresent());
        assertTrue(clazzX.getAnnotatedGetterMethod(ExampleAnnotation.class).isPresent());
        assertEquals(clazzX.getAnnotatedFields(ExampleAnnotation.class).size(), 2);
        assertTrue(clazzX.getAnnotatedField(ExampleAnnotation.class).isPresent());
    }

    @Test
    public void test2() {
        EzyClass clazz = new EzyClass(A2.class);
        assertEquals(
            clazz.getGetterMethod(m -> m.isAnnotated(EzyId.class))
                .orElseThrow(IllegalArgumentException::new).getName(),
            "getName"
        );
        assertEquals(clazz.getSetterMethod(
            m -> m.isAnnotated(EzyId.class))
                .orElseThrow(IllegalArgumentException::new).getName(),
            "setName"
        );
        assertFalse(clazz.getGetterMethod(
            m -> m.isAnnotated(EzyAutoImpl.class)).isPresent()
        );
        assertFalse(clazz.getSetterMethod(
            m -> m.isAnnotated(EzyAutoImpl.class)).isPresent()
        );

        assertEquals(clazz.getGetterMethod("getName").getName(), "getName");
        assertEquals(clazz.getSetterMethod("setName").getName(), "setName");

        assertNull(clazz.getGetterMethod("getName x"));
        assertNull(clazz.getSetterMethod("setName z"));

        assert clazz.getPublicMethod(m -> m.getName().equals("getName")).isPresent();
        assert !clazz.getPublicMethod(m -> m.getName().equals("getNameXYZ")).isPresent();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test3() {
        new EzyClass(ABC.class).getDeclaredConstructor();
    }

    @Test
    public void getNoArgsDeclaredConstructorTest() {
        EzyClass clazz = new EzyClass(BX.class);
        assert clazz.getNoArgsDeclaredConstructor().getParameterCount() == 0;
    }

    @Test
    public void getNoArgsDeclaredConstructorWithDefaultConstructorTest() {
        EzyClass clazz = new EzyClass(CX.class);
        assert clazz.getNoArgsDeclaredConstructor().getParameterCount() == 0;
    }

    @Test
    public void getNoArgsDeclaredConstructorNullTest() {
        EzyClass clazz = new EzyClass(DX.class);
        assert clazz.getNoArgsDeclaredConstructor() == null;
    }

    @Test
    public void getMaxArgsDeclaredConstructor() {
        EzyClass clazz = new EzyClass(BX.class);
        assert clazz.getMaxArgsDeclaredConstructor().getParameterCount() == 2;
    }

    @Test
    public void getDistinctMethodsTest() {
        // given
        EzyClass clazz = new EzyClass(InterfaceB.class);

        // when
        List<String> actual = clazz.getDistinctMethods(it -> true)
            .stream()
            .map(EzyMethod::getName)
            .collect(Collectors.toList());

        // then
        System.out.println(actual);
        Asserts.assertEquals(actual.size(), 3);
        Set<String> expectation = Sets.newHashSet(
            "getName", "getValue", "getSomething"
        );
        Asserts.assertEquals(new HashSet<>(actual), expectation);

    }

    @SuppressWarnings("unused")
    public interface InterfaceB extends InterfaceA {
        @Override
        String getValue();

        String getSomething();
    }

    public interface InterfaceA {

        String getName();

        String getValue();
    }

    @SuppressWarnings("unused")
    public static class ABC {
        public ABC(String s) {}
    }

    @SuppressWarnings("unused")
    public static class A2 {

        @EzyId
        public String getName() {
            return "name";
        }

        @EzyId
        public void setName(String name) {}

        protected void setNo() {}
    }

    @SuppressWarnings("unused")
    public static class B {
        protected static final String FINAL_FIELD = "final field";
        public String x = "x";
        @Setter
        @Getter
        protected String y = "y";
        @Getter
        @Setter
        private String z = "z";

        protected String protectedMethod() {
            return "a";
        }
    }

    @ExampleAnnotation
    public static class A extends B {

        public String a = "a";
        public String b = "b";
        @ExampleAnnotation
        public String c = "c";

        @Setter
        @Getter
        @ExampleAnnotation
        private String d = "d";

        @Setter
        @Getter
        @ExampleAnnotation
        private String e = "e";

        @Setter
        @Getter
        @ExampleAnnotation
        private String f = "f";

    }

    @SuppressWarnings("unused")
    public static class AX {
        @ExampleAnnotation
        protected String fieldA;
        @ExampleAnnotation
        protected String fieldB;
        protected String fieldC;

        @ExampleAnnotation
        protected void methodA() {}

        @ExampleAnnotation
        protected void methodB() {}

        @ExampleAnnotation
        public String getValue() {
            return "";
        }

        @ExampleAnnotation
        public void setValue(String value) {}
    }

    @SuppressWarnings("unused")
    public static class BX {
        protected BX(String name) {
            this(name, "value");
        }

        private BX(String name, String value) {}

        public BX() {
            this("name");
        }
    }

    public static class CX {}

    @SuppressWarnings("unused")
    public static class DX {
        public DX(String name) {}
    }
}
