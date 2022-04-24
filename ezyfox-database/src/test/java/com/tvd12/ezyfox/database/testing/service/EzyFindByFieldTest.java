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
}
