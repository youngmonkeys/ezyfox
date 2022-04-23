package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import org.testng.annotations.Test;

import java.util.Map;

import static com.tvd12.ezyfox.factory.EzyEntityFactory.newArrayBuilder;
import static com.tvd12.ezyfox.factory.EzyEntityFactory.newObjectBuilder;

public class EzyObjectToMapTest {

    @SuppressWarnings("rawtypes")
    @Test
    public void test() {
        EzyObject object = EzyEntityFactory.newObjectBuilder()
            .append(newArrayBuilder().append("1").build(), newArrayBuilder().append("2"))
            .append(newObjectBuilder().append("a", "b").build(), newArrayBuilder().append("x"))
            .append("null", (Object) null)
            .build();
        Map map = object.toMap();
        System.out.println("map: " + map);
    }
}
