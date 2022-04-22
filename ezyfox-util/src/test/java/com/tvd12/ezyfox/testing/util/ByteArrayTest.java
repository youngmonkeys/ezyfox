package com.tvd12.ezyfox.testing.util;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.util.ByteArray;
import com.tvd12.test.assertion.Asserts;

public class ByteArrayTest {

    @Test
    public void test() {
        ByteArray byteArray = ByteArray.wrap("abc".getBytes());
        assert byteArray.equals(new ByteArray("abc".getBytes()));
        assert byteArray.equals(byteArray);
        assert !byteArray.equals(null);
        assert !byteArray.equals(new Object());
        assert !byteArray.equals(new ByteArray("".getBytes()));
        assert Arrays.equals(byteArray.getBytes(), "abc".getBytes());
        assert byteArray.hashCode() == Arrays.hashCode("abc".getBytes());
        
        Asserts.assertEquals("abc", ByteArray.wrap("abc").toString());
        Asserts.assertThat(
                ByteArray.wrap(new byte[][] {{'a', 'b'}, {'c', 'd'}, {'e', 'f'}})
                    .stream()
                    .map(it -> it.toString())
                    .collect(Collectors.toList())
        )
        .isEqualsTo(Arrays.asList("ab", "cd", "ef"), false);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void test2() {
        ByteArray.wrap((byte[])null);
    }
    }