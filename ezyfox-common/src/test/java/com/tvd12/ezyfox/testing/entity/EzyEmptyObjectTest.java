package com.tvd12.ezyfox.testing.entity;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.entity.EzyEmptyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.HashSet;

public class EzyEmptyObjectTest {

    @Test
    public void test() {
        EzyEmptyObject object = (EzyEmptyObject) EzyEntityFactory.EMPTY_OBJECT;
        assert object.size() == 0;
        assert !object.containsKey("a");
        assert !object.isNotNullValue("a");
        try {
            object.get("a");
        } catch (Exception e) {
            assert e instanceof UnsupportedOperationException;
        }
        try {
            object.get("a", Integer.class);
        } catch (Exception e) {
            assert e instanceof UnsupportedOperationException;
        }
        try {
            object.getValue("a", Integer.class);
        } catch (Exception e) {
            assert e instanceof UnsupportedOperationException;
        }
        assert object.keySet().size() == 0;
        assert object.entrySet().size() == 0;
        assert object.toMap().isEmpty();
        assert object.put("a", 1).equals(1);
        object.putAll(new HashMap<>());
        assert object.remove("a") == null;
        object.removeAll(Sets.newHashSet(1, 2, 3));
        object.clear();
        assert !object.containsKeys(new HashSet<>());
        try {
            object.compute("a", (a, b) -> 1);
        } catch (Exception e) {
            assert e instanceof UnsupportedOperationException;
        }
        try {
            object.clone();
        } catch (Exception e) {
            assert e instanceof CloneNotSupportedException;
        }
        try {
            object.duplicate();
        } catch (Exception e) {
            assert e instanceof UnsupportedOperationException;
        }
    }
}
