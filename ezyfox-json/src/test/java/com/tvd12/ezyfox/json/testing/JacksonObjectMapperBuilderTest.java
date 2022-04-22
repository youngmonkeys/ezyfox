package com.tvd12.ezyfox.json.testing;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.io.EzyDates;
import com.tvd12.ezyfox.jackson.JacksonObjectMapperBuilder;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.util.RandomUtil;

import lombok.ToString;

public class JacksonObjectMapperBuilderTest {

    @Test
    public void test() throws Exception {
        JacksonObjectMapperBuilder builder = JacksonObjectMapperBuilder.newInstance();
        ObjectMapper mapper = builder.build();
        System.out.println(mapper.writeValueAsString(EzyEntityFactory.newArrayBuilder().append(1, 2, 3).build()));
        System.out.println(mapper.writeValueAsString(EzyEntityFactory.newObjectBuilder().append("hello", "word").build()));
        System.out.println(mapper.writeValueAsString(Instant.now()));
        System.out.println(mapper.writeValueAsString(new Date()));
        System.out.println(mapper.writeValueAsString(LocalDate.now()));
        System.out.println(mapper.writeValueAsString(LocalTime.now()));
        System.out.println(mapper.writeValueAsString(LocalDateTime.now()));
        System.out.println(mapper.writeValueAsString(new BigDecimal("100.123")));
        System.out.println(mapper.writeValueAsString(new Data()));
    }

    @Test
    public void testSerializer() throws Exception {
        JacksonObjectMapperBuilder builder = JacksonObjectMapperBuilder.newInstance();
        ObjectMapper mapper = builder.build();
        Instant instant = RandomUtil.randomInstant();
        Asserts.assertEquals(
                String.valueOf(instant.toEpochMilli()),
                mapper.writeValueAsString(instant)
        );
        Date date = RandomUtil.randomDate();
        Asserts.assertEquals(
                "\"" + EzyDates.format(date) + "\"",
                mapper.writeValueAsString(date)
        );
        LocalDate localDate = RandomUtil.randomLocalDate();
        Asserts.assertEquals(
                "\"" + EzyDates.format(localDate, EzyDates.DATE_PATTERN) + "\"",
                mapper.writeValueAsString(localDate)
        );
        LocalTime localTime = RandomUtil.randomLocalTime();
        Asserts.assertEquals(
                "\"" + EzyDates.format(localTime, EzyDates.TIME_PATTERN) + "\"",
                mapper.writeValueAsString(localTime)
        );
        LocalDateTime localDateTime = RandomUtil.randomLocalDateTime();
        Asserts.assertEquals(
                "\"" + EzyDates.format(localDateTime) + "\"",
                mapper.writeValueAsString(localDateTime)
        );
    }

    @ToString
    public static class Data {
        public Date date = RandomUtil.randomDate();
        public Instant instant = RandomUtil.randomInstant();
        public LocalDate localDate = RandomUtil.randomLocalDate();
        public LocalTime localTime = RandomUtil.randomLocalTime();
        public LocalDateTime localDateTime = RandomUtil.randomLocalDateTime();
    }

}
