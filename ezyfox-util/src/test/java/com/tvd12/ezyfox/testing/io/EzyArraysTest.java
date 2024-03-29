package com.tvd12.ezyfox.testing.io;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.io.EzyArrays;
import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static org.testng.Assert.assertEquals;

public class EzyArraysTest extends BaseTest {

    @Override
    public Class<?> getTestClass() {
        return EzyArrays.class;
    }

    @Test
    public void test() {
        Collection<String> coll = Lists.newArrayList("1", "2", "3");
        Long[] array = EzyArrays.newArray(coll,
            Long[]::new,
            Long::valueOf);
        Assert.assertEquals(array, new Long[]{1L, 2L, 3L});
        byte[] bytes1 = new byte[]{1, 2, 3};
        byte[] bytes2 = new byte[2];
        EzyArrays.copy(bytes1, bytes2, 0);
        assertEquals(bytes2, new byte[]{1, 2});
        byte[] bytes3 = new byte[5];
        EzyArrays.copy(bytes1, bytes3, 2);
        assertEquals(bytes3, new byte[]{0, 0, 1, 2, 3});
        byte[] bytes4 = new byte[6];
        EzyArrays.copy(bytes1, bytes4, 2);
        assertEquals(bytes4, new byte[]{0, 0, 1, 2, 3, 0});
    }

    @Test
    public void test1() {
        assert EzyArrays.min(new Long[]{3L, 1L, 2L}) == 1L;
        assert EzyArrays.max(new Long[]{3L, 1L, 5L, 6L, 2L}) == 6L;
    }

    @Test
    public void test3() {
        byte[] bytes = new byte[2000];
        Arrays.fill(bytes, (byte) 2);
        byte[] bytes1 = new byte[bytes.length + 1];
        bytes1[0] = 1;
        System.arraycopy(bytes, 0, bytes1, 1, bytes.length);
        byte[] bytes2 = EzyBytes.merge((byte) 1, bytes);
        assertEquals(bytes1, bytes2);
    }

    @Test
    public void test5() {
        System.out.println(Arrays.toString(EzyBytes.merge((byte) 1, new byte[]{3, 6, 2, 5})));
    }

    @Test
    public void minFailedDueToStreamIsEmpty() {
        // given
        // when
        Throwable e = Asserts.assertThrows(() ->
            EzyArrays.min(
                Stream.empty(),
                Integer::compareTo
            )
        );

        // then
        Asserts.assertEqualsType(e, IllegalArgumentException.class);
    }

    @Test
    public void maxFailedDueToStreamIsEmpty() {
        // given
        // when
        Throwable e = Asserts.assertThrows(() ->
            EzyArrays.max(
                Stream.empty(),
                Integer::compareTo
            )
        );

        // then
        Asserts.assertEqualsType(e, IllegalArgumentException.class);
    }
}
