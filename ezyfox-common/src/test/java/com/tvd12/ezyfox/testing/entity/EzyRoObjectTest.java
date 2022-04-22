package com.tvd12.ezyfox.testing.entity;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

public class EzyRoObjectTest {

    @Test
    public void test() {
        EzyObject object = EzyEntityFactory.newObjectBuilder()
                .append("a", 1)
                .build();
        assert object.get("a", int.class, 0) == (Object)1;
        assert object.get("aaa", int.class, 0) == (Object)0;
        assert object.getValue("a", int.class, 0) == (Object)1;
        assert object.getValue("aaa", int.class, 0) == (Object)0;
        object.removeAll(Sets.newHashSet("c", "b"));
        assert object.size() == 1;
        object.removeAll(Sets.newHashSet("a", "b"));
        assert object.size() == 0;
    }
    
}
