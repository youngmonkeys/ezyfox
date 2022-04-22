package com.tvd12.ezyfox.testing.io;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.io.EzyChars;
import com.tvd12.test.base.BaseTest;

public class EzyCharsTest extends BaseTest {

    @Override
    public Class<?> getTestClass() {
        return EzyChars.class;
    }

    @Test
    public void test() {
        assert EzyChars.isUpperCase('A');
        assert EzyChars.isUpperCase('Z');
        assert EzyChars.isUpperCase('B');
        assert !EzyChars.isUpperCase('a');
        assert !EzyChars.isUpperCase('z');
        assert !EzyChars.isUpperCase('1');
    }
}