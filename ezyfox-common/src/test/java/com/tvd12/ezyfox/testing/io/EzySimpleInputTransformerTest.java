package com.tvd12.ezyfox.testing.io;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.io.EzyInputTransformer;
import com.tvd12.ezyfox.io.EzyMaps;
import com.tvd12.ezyfox.io.EzySimpleInputTransformer;
import com.tvd12.ezyfox.testing.entity.EzyEntityTest;

import static org.testng.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class EzySimpleInputTransformerTest extends EzyEntityTest {

    private EzyInputTransformer transformer = new EzySimpleInputTransformer();

    @SuppressWarnings("unchecked")
    @Test
    public void test() {
        assertEquals(transform(new boolean[] {true, false, true}),
                Lists.newArrayList(true, false, true));

        assertEquals(transform(new double[] {1D, 2D, 3D}),
                Lists.newArrayList(1D, 2D, 3D));

        assertEquals(transform(new float[] {1F, 2F, 3F}),
                Lists.newArrayList(1F, 2F, 3F));

        assertEquals(transform(new int[] {1, 2, 3}),
                Lists.newArrayList(1, 2, 3));

        assertEquals(transform(new long[] {1L, 2L, 3L}),
                Lists.newArrayList(1L, 2L, 3L));

        assertEquals(transform(new short[] {(short)1, (short)2, (short)3}),
                Lists.newArrayList((short)1, (short)2, (short)3));

        assertEquals(transform(new String[] {"a", "b", "c"}),
                Lists.newArrayList("a", "b", "c"));


        assertEquals(transform(new Boolean[] {true, false, true}),
                Lists.newArrayList(true, false, true));

        assertEquals(transform(new Byte[] {1, 2, 3}),
                new byte[] {1, 2, 3});

        assertEquals(transform(new Character[] {'a', 'b', 'c'}),
                new byte[] {'a', 'b', 'c'});

        assertEquals(transform(new Double[] {1D, 2D, 3D}),
                Lists.newArrayList(1D, 2D, 3D));

        assertEquals(transform(new Float[] {1F, 2F, 3F}),
                Lists.newArrayList(1F, 2F, 3F));

        assertEquals(transform(new Integer[] {1, 2, 3}),
                Lists.newArrayList(1, 2, 3));

        assertEquals(transform(new Long[] {1L, 2L, 3L}),
                Lists.newArrayList(1L, 2L, 3L));

        assertEquals(transform(new Short[] {(short)1, (short)2, (short)3}),
                Lists.newArrayList((short)1, (short)2, (short)3));

        assertEquals(transform(new String[] {"a", "b", "c"}),
                Lists.newArrayList("a", "b", "c"));

        assertEquals(transform(String.class), String.class.getName());

        EzyObject[] objects = new EzyObject[] {
                newObjectBuilder().append("1", "a").build()
        };
        EzyArray marray = ((EzyArray)transform(objects));
        assertEquals(marray.toList(),
                Lists.newArrayList(EzyMaps.newHashMap("1", "a")));

        assertEquals(transform(LocalDate.of(2017, 05, 30)), "2017-05-30");

        assertEquals(transform(LocalDateTime.of(2017, 05, 30, 12, 34, 56, 78)), "2017-05-30T12:34:56:000");

        transformer.transform(newArrayBuilder());

        Map<String, String> newMap = new HashMap<>();
        newMap.put("hello", "world");
        EzyObject tobject = (EzyObject) transformer.transform(newMap);
        assert tobject.size() == 1;
        System.out.println("tobject: " + tobject);
    }

    private Object transform(Object value) {
        return transformer.transform(value);
    }
}
