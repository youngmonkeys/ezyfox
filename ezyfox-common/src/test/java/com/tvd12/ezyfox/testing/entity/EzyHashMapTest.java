package com.tvd12.ezyfox.testing.entity;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.entity.EzyHashMap;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.exception.EzyObjectGetException;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.test.base.BaseTest;

public class EzyHashMapTest extends BaseTest {

    @Test
    public void test() {
        EzyHashMap map = new ExEzyHashMap();
        map.put("1", "2");
        assert map.isNotNullValue("1");
        assert !map.isNotNullValue("2");

        EzyObject object = EzyEntityFactory.newObject();
        object.put("a", "b");
        try {
            object.get("a", int.class);
        }
        catch (Exception e) {
            assert e instanceof EzyObjectGetException;
            EzyObjectGetException ex = (EzyObjectGetException)e;
            assert ex.getOutType() == int.class;
            assert ex.getKey().equals("a");
            assert ex.getValue().equals("b");
        }
    }


    public static class ExEzyHashMap extends EzyHashMap {
        private static final long serialVersionUID = -5733872612446105669L;

        public ExEzyHashMap() {
            super(null, null);
        }

        @SuppressWarnings("unchecked")
        @Override
        public <V> V put(Object key, Object value) {
            return (V) map.put(key, value);
        }

    }
}