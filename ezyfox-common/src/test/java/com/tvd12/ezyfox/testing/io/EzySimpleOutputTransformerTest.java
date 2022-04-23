package com.tvd12.ezyfox.testing.io;

import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.io.EzyOutputTransformer;
import com.tvd12.ezyfox.io.EzySimpleOutputTransformer;
import com.tvd12.ezyfox.testing.entity.EzyEntityTest;
import org.testng.annotations.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class EzySimpleOutputTransformerTest extends EzyEntityTest {

    private final EzyOutputTransformer transformer
        = new EzySimpleOutputTransformer();

    @Test
    public void test() {
        // given
        ClassA classA = new ClassA();
        EzyArray array = newArray();
        long now = System.currentTimeMillis();

        // when
        // then
        assertNull(transformer.transform(null, String.class));
        assertEquals(transformer.transform(classA, ClassA.class), classA);
        assertEquals(transformer.transform(String.class.getName(), Class.class), String.class);
        assertEquals((((EzyObject[]) transformer.transform(array, EzyObject[].class))).length, 1);

        assertNull(transformer.transform("124", Date.class));
        assertNull(transformer.transform("124", Class.class));

        assertNull(transformer.transform("124", LocalDate.class));
        assertNull(transformer.transform("124", LocalDateTime.class));

        assertEquals(transformer.transform("2017-05-30", LocalDate.class),
            LocalDate.of(2017, 5, 30));
        assertEquals(transformer.transform("2017-05-30T12:34:56:000", LocalDateTime.class),
            LocalDateTime.of(2017, 5, 30, 12, 34, 56, 0));
        assertEquals(transformer.transform(now, Instant.class), Instant.ofEpochMilli(now));
        assertNull(transformer.transform("invalid", Instant.class));
    }

    private EzyArray newArray() {
        EzyObjectBuilder objectBuilder = EzyEntityFactory.create(EzyObjectBuilder.class)
            .append("1", "a");
        return EzyEntityFactory.create(EzyArrayBuilder.class).append(objectBuilder).build();
    }

    public static class ClassA {}
}
