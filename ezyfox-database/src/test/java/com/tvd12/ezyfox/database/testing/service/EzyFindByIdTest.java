package com.tvd12.ezyfox.database.testing.service;

import com.tvd12.ezyfox.database.service.EzyFindById;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.util.Optional;

public class EzyFindByIdTest {

    @Test
    public void findByIdOptionalTest() {
        // given
        EzyFindById<String, String> sut = id -> "hello";

        // when
        Optional<String> actual = sut.findByIdOptional(null);

        // then
        Asserts.assertEquals(actual, Optional.of("hello"));
    }

    @Test
    public void containsByIdReturnTrueTest() {
        // given
        EzyFindById<String, String> sut = id -> "hello";

        // when
        boolean actual = sut.containsById("id");

        // then
        Asserts.assertTrue(actual);
    }

    @Test
    public void containsByIdReturnFalseTest() {
        // given
        EzyFindById<String, String> sut = id -> null;

        // when
        boolean actual = sut.containsById("id");

        // then
        Asserts.assertFalse(actual);
    }
}
