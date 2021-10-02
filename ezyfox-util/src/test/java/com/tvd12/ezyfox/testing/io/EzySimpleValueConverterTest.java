package com.tvd12.ezyfox.testing.io;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.io.EzySimpleValueConverter;
import com.tvd12.test.assertion.Asserts;

public class EzySimpleValueConverterTest {

    @Test
    public void convertEnumTest() {
        // given
        EzySimpleValueConverter sut = new EzySimpleValueConverter();
        
        // when
        ExEnum actual = sut.convert("HELLO", ExEnum.class);
        
        // then
        Asserts.assertEquals(ExEnum.HELLO, actual);
    }
    
    public static enum ExEnum {
        HELLO,
        WORLD
    }
}
