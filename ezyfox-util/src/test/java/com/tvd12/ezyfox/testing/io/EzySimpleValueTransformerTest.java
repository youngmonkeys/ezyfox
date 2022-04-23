package com.tvd12.ezyfox.testing.io;

import java.util.List;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.io.EzySimpleValueConverter;
import com.tvd12.ezyfox.io.EzyValueConverter;
import com.tvd12.test.base.BaseTest;

public class EzySimpleValueTransformerTest extends BaseTest {

    @Test
    public void test() {
        EzyValueConverter transformer = EzySimpleValueConverter.getSingleton();
        assert transformer.convert(null, null) == null;
    }

    @SuppressWarnings({ "unused", "unchecked" })
    @Test
    public void test1() {
        EzyValueConverter transformer = new EzySimpleValueConverter();
        List<Integer> v = transformer.convert(Lists.newArrayList(1, 2, 3), List.class);
    }
}
