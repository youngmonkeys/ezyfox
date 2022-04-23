package com.tvd12.ezyfox.testing.io;

import com.tvd12.ezyfox.io.EzySimpleValueConverter;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

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

    @SuppressWarnings("unused")
    public enum ExEnum {
        HELLO,
        WORLD
    }
}
