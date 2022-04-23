package com.tvd12.ezyfox.database.testing.service;

import java.util.Optional;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.database.service.EzyFindByField;
import com.tvd12.test.assertion.Asserts;

public class EzyFindByFieldTest {

    @Test
    public void findByFieldOptionalTest() {
        // given
        EzyFindByField<String> sut = new EzyFindByField<String>() {
            @Override
            public String findByField(String field, Object value) {
                return "hello";
            }
        };
        
        // when
        Optional<String> actual = sut.findByFieldOptional(null, null);
        
        // then
        Asserts.assertEquals(actual, Optional.of("hello"));
    }
}
