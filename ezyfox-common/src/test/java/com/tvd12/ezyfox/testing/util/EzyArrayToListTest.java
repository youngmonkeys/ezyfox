package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import org.testng.annotations.Test;

public class EzyArrayToListTest {

    @Test
    public void test() {
        EzyArray array = EzyEntityFactory.newArrayBuilder()
            .append((Object) null)
            .append(EzyEntityFactory.newArrayBuilder().append("1", "2"))
            .build();
        System.out.println("array: " + array.toList());
    }
}
