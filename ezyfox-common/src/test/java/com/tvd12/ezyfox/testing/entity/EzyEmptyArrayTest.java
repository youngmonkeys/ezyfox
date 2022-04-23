package com.tvd12.ezyfox.testing.entity;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.entity.EzyEmptyArray;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import org.testng.annotations.Test;

import java.util.HashSet;

public class EzyEmptyArrayTest {

    @Test
    public void test() {
        EzyEmptyArray array = (EzyEmptyArray) EzyEntityFactory.EMPTY_ARRAY;
        try {
            array.get(0);
        } catch (Exception e) {
            assert e instanceof UnsupportedOperationException;
        }
        try {
            array.get(0, int.class);
        } catch (Exception e) {
            assert e instanceof UnsupportedOperationException;
        }
        try {
            array.getValue(0, int.class);
        } catch (Exception e) {
            assert e instanceof UnsupportedOperationException;
        }
        assert !array.isNotNullValue(0);
        assert array.size() == 0;
        assert array.toList().size() == 0;
        try {
            array.toList(Integer.class);
        } catch (Exception e) {
            assert e instanceof UnsupportedOperationException;
        }
        try {
            array.toArray(Integer.class);
        } catch (Exception e) {
            assert e instanceof UnsupportedOperationException;
        }
        array.add(0L);
        array.add(1, 2, 3);
        array.add(Lists.newArrayList(1, 2, 3));
        assert array.set(0, 1).equals(1);
        try {
            array.remove(0);
        } catch (Exception e) {
            assert e instanceof UnsupportedOperationException;
        }
        try {
            array.sub(0, 1);
        } catch (Exception e) {
            assert e instanceof UnsupportedOperationException;
        }
        array.clear();
        array.forEach(i -> {});
        assert !array.iterator().hasNext();
        try {
            array.clone();
        } catch (Exception e) {
            assert e instanceof CloneNotSupportedException;
        }
        try {
            array.duplicate();
        } catch (Exception e) {
            assert e instanceof UnsupportedOperationException;
        }
        assert !array.contains(new Object());
        assert !array.containsAll(new HashSet<>());
        array.sort(null);
    }
}
