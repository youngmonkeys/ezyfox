package com.tvd12.ezyfox.testing.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.util.EzyMapBuilder;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;

public class EzyMapBuilderTest extends BaseTest {

    @Test
    public void test() {
        // given
        EzyMapBuilder sut = EzyMapBuilder.mapBuilder()
                .map(new HashMap<>())
                .put("a", "b")
                .putAll(Collections.singletonMap("foo", "bar"));

        // when
        // then
        Map<String, Object> expectation = new HashMap<>();
        expectation.put("a", "b");
        expectation.put("foo", "bar");
        Asserts.assertEquals(sut.build(), expectation);
        Asserts.assertEquals(sut.toMap(), expectation);
    }
}