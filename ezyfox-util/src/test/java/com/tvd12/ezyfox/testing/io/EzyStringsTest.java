package com.tvd12.ezyfox.testing.io;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;

import lombok.AllArgsConstructor;

public class EzyStringsTest extends BaseTest {

    @Test
    public void test() throws Exception {
        assertEquals("abc", EzyStrings.newUtf("abc".getBytes("UTF-8")));
        assertEquals("abc", EzyStrings.newUtf(ByteBuffer.wrap("abc".getBytes("UTF-8")), 3));
        assertEquals(EzyStrings.getUtfBytes("abc"), "abc".getBytes("UTF-8"));
        assertEquals(EzyStrings.newString('a', 3), "aaa");
        assertEquals(EzyStrings.quote(null), "\"null\"");
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void test1() {
        EzyStrings.newString(new byte[] {1, 2, 3}, "kkk");
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void test2() {
        EzyStrings.getBytes("abc", "hhh");
    }

    @Test
    public void test3() {
        String[] array = new String[] {"1", "2", "3"};
        assert EzyStrings.getString(array, 1, "x").equals("2");
        assert EzyStrings.getString(array, 100, "x").equals("x");
    }

    @Test
    public void test4() {
        Collection<String> collection1 = Lists.newArrayList("1", "2", "3");
        Collection<Integer> collection2 = Lists.newArrayList(1, 2, 3);
        Collection<ClassX> collection3 = Lists.newArrayList(new ClassX("n1", "v1"), new ClassX("n2", "v2"));
        System.out.println(EzyStrings.wrap(collection1, "[", "]", ",", true));
        System.out.println(EzyStrings.wrap(collection2, "[", "]", ",", true));
        System.out.println(EzyStrings.wrap(collection3, "[", "]", ",", true));
        System.out.println(EzyStrings.wrap(null, "[", "]", ",", true));
        System.out.println(EzyStrings.wrap(null, "[", "]", ",", false));
        System.out.println(EzyStrings.wrap(new ArrayList<>(), "[", "]", ",", false));
        System.out.println(EzyStrings.wrap(Arrays.asList(1L), "[", "]", ",", false));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void test5() {
        assert EzyStrings.isEmpty(null);
        assert EzyStrings.isEmpty("");
        assert !EzyStrings.isEmpty(" ");
        assert !EzyStrings.isEmpty("\t");
        assert !EzyStrings.isEmpty("\n");
        assert !EzyStrings.isEmpty("a");

        assert EzyStrings.isNoContent(null);
        assert EzyStrings.isNoContent("");
        assert EzyStrings.isNoContent(" ");
        assert EzyStrings.isNoContent("\t");
        assert EzyStrings.isNoContent("\n");
        assert !EzyStrings.isNoContent("a");
        assert !EzyStrings.isBlank("a");
        assert EzyStrings.isBlank("\t");
        assert EzyStrings.isNotEmpty("a");
        assert !EzyStrings.isNotEmpty("");
        assert EzyStrings.isNotBlank("a");
        assert !EzyStrings.isNotBlank("\t");

        System.out.println(EzyStrings.join(new double[] {1.1, 2.2, 3.3}, ","));
        System.out.println(EzyStrings.join(new int[] {1, 2, 3}, ". "));
        System.out.println(EzyStrings.join(new long[] {1, 2, 3}, ";- "));
        System.out.println(EzyStrings.join(new String[] {"a", "b", "c"}, "><"));

        Collection c1 = Lists.newArrayList("a", "b");
        System.out.println(EzyStrings.join(c1, ","));
        Collection<?> c2 = Lists.newArrayList("a", "b");
        System.out.println(EzyStrings.join(c2, ","));
    }

    @Test
    public void replaceOkTest() {
        // given
        String queryString = "select e from E e where id = ?0 and name = ?1";
        Object params[] = new Object[] {1, "'monkey'"};

        // when
        String actual = EzyStrings.replace(queryString, params);

        // then
        String expected = "select e from E e where id = 1 and name = 'monkey'";
        Asserts.assertEquals(expected, actual);
    }

    @Test
    public void replaceOkWithConverterTest() {
        // given
        String queryString = "select e from E e where id = ?0 and name = ?1";
        Object params[] = new Object[] {1, "'monkey'"};

        // when
        String actual = EzyStrings.replace(queryString, params, it -> it);

        // then
        String expected = "select e from E e where id = 1 and name = 'monkey'";
        Asserts.assertEquals(expected, actual);
    }

    @Test
    public void replaceOkEndWithQuestionChar() {
        // given
        String queryString = "select e from E e where id = ?0 and name = ?";
        Object params[] = new Object[] {1, "'monkey'"};

        // when
        String actual = EzyStrings.replace(queryString, params);

        // then
        String expected = "select e from E e where id = 1 and name = ?";
        Asserts.assertEquals(expected, actual);
    }

    @Test
    public void replaceOkDoubleQuestionChar() {
        // given
        String queryString = "select e from E e where id = ??0 and name = ??";
        Object params[] = new Object[] {1, "'monkey'"};

        // when
        String actual = EzyStrings.replace(queryString, params);

        // then
        String expected = "select e from E e where id = ?1 and name = ??";
        Asserts.assertEquals(expected, actual);
    }

    @Test
    public void replaceOkWithDuplicateParams() {
        // given
        String queryString = "select e from E e where id = ?0 or id = ?0 and name = ?1";
        Object params[] = new Object[] {1, "'monkey'"};

        // when
        String actual = EzyStrings.replace(queryString, params);

        // then
        String expected = "select e from E e where id = 1 or id = 1 and name = 'monkey'";
        Asserts.assertEquals(expected, actual);
    }

    @Test
    public void replaceFailedWithMissingParam() {
        // given
        String queryString = "select e from E e where id = ?0 or id = ?0 and name = ?2";
        Object params[] = new Object[] {1, "'monkey'"};

        // when
        // then
        Asserts.assertThat(() ->
            EzyStrings.replace(queryString, params)
        )
        .testException(it -> it.getClass().equals(IllegalArgumentException.class));
    }

    @Test
    public void underscoreToCamelCaseTest() {
        Asserts.assertNull(EzyStrings.underscoreToCamelCase(null));
        Asserts.assertEquals(EzyStrings.underscoreToCamelCase(""), "");
        Asserts.assertEquals(EzyStrings.underscoreToCamelCase("A"), "a");
        Asserts.assertEquals(EzyStrings.underscoreToCamelCase("abc"), "abc");
        Asserts.assertEquals(EzyStrings.underscoreToCamelCase("aa_bb_cc"), "aaBbCc");
        Asserts.assertEquals(EzyStrings.underscoreToCamelCase("aa__bb___cc"), "aaBbCc");
    }

    @Test
    public void toDisplayNameTest() {
        // given
        String string = "--hello..world_i;fine,how--are-you fine\tor  not?";

        // when
        // then
        Asserts.assertTrue(EzyStrings.toDisplayName(null).isEmpty());
        Asserts.assertEquals(EzyStrings.toDisplayName(string), "Hello World I Fine How Are You Fine Or Not?");
    }

    @Test
    public void toDisplayNameBoundingTest() {
        // given
        String string = "hello..world_i;fine,how--are-you fine\tor  not?--";
        
        // when
        // then
        Asserts.assertTrue(EzyStrings.toDisplayName(null).isEmpty());
        Asserts.assertEquals(EzyStrings.toDisplayName(string), "Hello World I Fine How Are You Fine Or Not?");
    }

    @Test
    public void isEqualsIgnoreCaseTest() {
        Asserts.assertTrue(EzyStrings.isEqualsIgnoreCase(null, null));
        Asserts.assertTrue(EzyStrings.isEqualsIgnoreCase(EzyStrings.EMPTY_STRING, EzyStrings.EMPTY_STRING));
        Asserts.assertFalse(EzyStrings.isEqualsIgnoreCase("a", null));
        Asserts.assertFalse(EzyStrings.isEqualsIgnoreCase(null, "b"));
        Asserts.assertFalse(EzyStrings.isEqualsIgnoreCase("ab", "b"));
        Asserts.assertFalse(EzyStrings.isEqualsIgnoreCase("ab", "ba"));
        Asserts.assertTrue(EzyStrings.isEqualsIgnoreCase("a.b", "a-b"));
        Asserts.assertTrue(EzyStrings.isEqualsIgnoreCase("a_b", "a-b"));
        Asserts.assertFalse(EzyStrings.isEqualsIgnoreCase("a_b", "acb"));
    }

    @Test
    public void displayNameToDashCase() {
        // given
        String displayName = "Hello World";

        // when
        String actual = EzyStrings.toDashCase(displayName);

        // then
        Asserts.assertEquals(actual, "hello-world");
    }

    @Test
    public void traceStackToStringTest() {
        // given
        Exception e = new Exception("test");

        // when
        String actual = EzyStrings.traceStackToString(e);

        // then
        Asserts.assertNotNull(actual);
    }

    @Test
    public void traceStackToStringExceptionTest() {
        // given
        Throwable e = Mockito.mock(Throwable.class);
        doThrow(IOException.class)
            .when(e)
            .printStackTrace(any(PrintWriter.class));
        
        // when
        String actual = EzyStrings.traceStackToString(e);
        
        // then
        Asserts.assertNotNull(actual);
        verify(e, times(1)).printStackTrace(any(PrintWriter.class));
    }

    @Override
    public Class<?> getTestClass() {
        return EzyStrings.class;
    }

    @AllArgsConstructor
    public static class ClassX {

        private final String name;
        private final String value;

        @Override
        public String toString() {
            return new StringBuilder()
                    .append("{")
                    .append("\"name\":").append("\"" + name + "\"").append(",")
                    .append("\"value\":").append("\"" + value + "\"")
                    .append("}")
                    .toString();
        }
    }
}
