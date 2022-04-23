package com.tvd12.ezyfox.testing.io;

import com.tvd12.ezyfox.io.EzyDates;
import com.tvd12.ezyfox.io.EzySimpleValueConverter;
import com.tvd12.ezyfox.io.EzyValueConverter;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class EzySimpleValueTransformerOtherTest extends BaseTest {

    @Test
    public void test() {
        EzyValueConverter transformer = new EzySimpleValueConverter();
        assert transformer.convert("abc", Date.class) == null;
        assert transformer.convert("2017-10-10T10:10:10:10", Date.class) != null;

        assert transformer.convert("abc", LocalDate.class) == null;
        assert transformer.convert("2017-10-10", LocalDate.class) != null;

        assert transformer.convert("abc", LocalDateTime.class) == null;
        assert transformer.convert(EzyDates.format(LocalDateTime.now()), LocalDateTime.class) != null;

        assert transformer.convert("", Class.class) == null;
        assert transformer.convert(getClass().getName(), Class.class) != null;

    }
}
