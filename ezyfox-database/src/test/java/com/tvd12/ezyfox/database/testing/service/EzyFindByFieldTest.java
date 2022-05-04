package com.tvd12.ezyfox.database.testing.service;

import com.tvd12.ezyfox.database.service.EzyFindByField;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.util.Optional;

public class EzyFindByFieldTest {

    @Test
    public void findByFieldOptionalTest() {
        // given
        EzyFindByField<String> sut = (field, value) -> "hello";

        // when
        Optional<String> actual = sut.findByFieldOptional(null, null);

        // then
        Asserts.assertEquals(actual, Optional.of("hello"));
    }

    @Test
    public void containsByFieldReturnTrueFalse() {
        // given
        EzyFindByField<String> sut = (field, value) -> "hello";

        // when
        boolean actual = sut.containsByField("field", "value");

        // then
        Asserts.assertTrue(actual);
    }

    @Test
    public void containsByFieldReturnFalseFalse() {
        // given
        EzyFindByField<String> sut = (field, value) -> null;

        // when
        boolean actual = sut.containsByField("field", "value");

        // then
        Asserts.assertFalse(actual);
    }
}
