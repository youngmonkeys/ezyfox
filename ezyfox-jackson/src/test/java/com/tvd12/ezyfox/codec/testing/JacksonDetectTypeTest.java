package com.tvd12.ezyfox.codec.testing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvd12.test.base.BaseTest;

import java.util.Arrays;

public class JacksonDetectTypeTest extends BaseTest {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(Arrays.toString(objectMapper.writeValueAsBytes(null)));
    }
}
