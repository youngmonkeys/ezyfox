package com.tvd12.ezyfox.database.testing.service;

import com.tvd12.ezyfox.database.service.EzyFindById;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.util.Optional;

public class EzyFindByIdTest {

    @Test
    public void findByFieldOptionalTest() {
        // given
        EzyFindById<String, String> sut = id -> "hello";

        // when
        Optional<String> actual = sut.findByIdOptional(null);

        // then
        Asserts.assertEquals(actual, Optional.of("hello"));
    }
}
