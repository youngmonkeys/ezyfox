package com.tvd12.ezyfox.binding.testing.full;

import static com.tvd12.ezyfox.factory.EzyEntityFactory.newArrayBuilder;
import static com.tvd12.ezyfox.factory.EzyEntityFactory.newObjectBuilder;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.EzyWriter;
import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.io.EzyMaps;
import com.tvd12.ezyfox.io.EzyPrints;
import com.tvd12.ezyfox.reflect.EzyReflectionProxy;
import com.tvd12.ezyfox.tool.EzyObjectInstanceRandom;


public class FullTest {

    @SuppressWarnings("rawtypes")
    @Test
    public void test1() {
        FullData fullData = new EzyObjectInstanceRandom()
                .randomObject(FullData.class, false);
        EzyBindingContext bindingContext = EzyBindingContext.builder()
                .addClass(FullData.class)
                .addClass(Tiny.class)
                .addAllClasses(new Object())
                .addAllClasses(new EzyReflectionProxy("com.tvd12.ezyfox.binding.testing.full"))
                .scan(new Iterable<String>() {

                    @Override
                    public Iterator<String> iterator() {
                        return new ArrayList<String>().iterator();
                    }
                })
                .scan(new Iterable<String>() {

                    @Override
                    public Iterator<String> iterator() {
                        return Lists.newArrayList("com.tvd12.ezyfox.binding.testing.full").iterator();
                    }
                })
                .build();
        EzyMarshaller marshaller = bindingContext.newMarshaller();
        EzyUnmarshaller unmarshaller = bindingContext.newUnmarshaller();
        EzyObject object = (EzyObject)marshaller.marshal(fullData);
        FullData fullData1 = unmarshaller.unmarshal(object, FullData.class);
        System.out.println("fullData1: " + fullData1);
        EzyObject fullDataObject2 = newFullDataObject2();
        FullData fullData2 = unmarshaller.unmarshal(fullDataObject2, FullData.class);
        System.out.println("fullData2: " + fullData2);

        assertNull(marshaller.marshal((Class<? extends EzyWriter>)null, (Object)null));

        assertEquals(unmarshaller.unmarshal(newArrayBuilder().append(true, false).build(), boolean[].class), new boolean[] {true, false});
        assertEquals(unmarshaller.unmarshal(newArrayBuilder().append(1, 2).build(), byte[].class), new byte[] {1, 2});
        assertEquals(unmarshaller.unmarshal(newArrayBuilder().append('a', 'b').build(), char[].class), new char[] {'a', 'b'});
        assertEquals(unmarshaller.unmarshal(newArrayBuilder().append(3, 4).build(), double[].class), new double[] {3, 4});
        assertEquals(unmarshaller.unmarshal(newArrayBuilder().append(5, 6).build(), float[].class), new float[] {5, 6});
        assertEquals(unmarshaller.unmarshal(newArrayBuilder().append(7, 8).build(), int[].class), new int[] {7, 8});
        assertEquals(unmarshaller.unmarshal(newArrayBuilder().append(9, 10).build(), long[].class), new long[] {9, 10});
        assertEquals(unmarshaller.unmarshal(newArrayBuilder().append(11, 12).build(), short[].class), new short[] {11, 12});

        assertEquals(unmarshaller.unmarshal(Lists.newArrayList(true, false), boolean[].class), new boolean[] {true, false});
        assertEquals(unmarshaller.unmarshal(Lists.newArrayList(1, 2), byte[].class), new byte[] {1, 2});
        assertEquals(unmarshaller.unmarshal(Lists.newArrayList('a', 'b'), char[].class), new char[] {'a', 'b'});
        assertEquals(unmarshaller.unmarshal(Lists.newArrayList(3, 4), double[].class), new double[] {3, 4});
        assertEquals(unmarshaller.unmarshal(Lists.newArrayList(5, 6), float[].class), new float[] {5, 6});
        assertEquals(unmarshaller.unmarshal(Lists.newArrayList(7, 8), int[].class), new int[] {7, 8});
        assertEquals(unmarshaller.unmarshal(Lists.newArrayList(9, 10), long[].class), new long[] {9, 10});
        assertEquals(unmarshaller.unmarshal(Lists.newArrayList(11, 12), short[].class), new short[] {11, 12});

        assertEquals(unmarshaller.unmarshal(newArrayBuilder().append(true, false).build(), Boolean[].class), new Boolean[] {true, false});
        assertEquals(unmarshaller.unmarshal(newArrayBuilder().append(1, 2).build(), Byte[].class), new Byte[] {1, 2});
        assertEquals(unmarshaller.unmarshal(newArrayBuilder().append('a', 'b').build(), Character[].class), new Character[] {'a', 'b'});
        assertEquals(unmarshaller.unmarshal(newArrayBuilder().append(3, 4).build(), Double[].class), new Double[] {3D, 4D});
        assertEquals(unmarshaller.unmarshal(newArrayBuilder().append(5, 6).build(), Float[].class), new Float[] {5F, 6F});
        assertEquals(unmarshaller.unmarshal(newArrayBuilder().append(7, 8).build(), Integer[].class), new Integer[] {7, 8});
        assertEquals(unmarshaller.unmarshal(newArrayBuilder().append(9, 10).build(), Long[].class), new Long[] {9L, 10L});
        assertEquals(unmarshaller.unmarshal(newArrayBuilder().append(11, 12).build(), Short[].class), new Short[] {11, 12});

        assertEquals(unmarshaller.unmarshal(Lists.newArrayList(true, false), Boolean[].class), new Boolean[] {true, false});
        assertEquals(unmarshaller.unmarshal(Lists.newArrayList(1, 2), Byte[].class), new Byte[] {1, 2});
        assertEquals(unmarshaller.unmarshal(Lists.newArrayList('a', 'b'), Character[].class), new Character[] {'a', 'b'});
        assertEquals(unmarshaller.unmarshal(Lists.newArrayList(3, 4), Double[].class), new Double[] {3D, 4D});
        assertEquals(unmarshaller.unmarshal(Lists.newArrayList(5, 6), Float[].class), new Float[] {5F, 6F});
        assertEquals(unmarshaller.unmarshal(Lists.newArrayList(7, 8), Integer[].class), new Integer[] {7, 8});
        assertEquals(unmarshaller.unmarshal(Lists.newArrayList(9, 10), Long[].class), new Long[] {9L, 10L});
        assertEquals(unmarshaller.unmarshal(Lists.newArrayList(11, 12), Short[].class), new Short[] {11, 12});

        assertEquals(unmarshaller.unmarshal(Lists.newArrayList("a", "b"), String[].class), new String[] {"a", "b"});

        assertEquals(unmarshaller.unmarshal(newObjectBuilder().append("a", "b").build(), Map.class), EzyMaps.newHashMap("a", "b"));
        assertEquals(unmarshaller.unmarshal(newObjectBuilder().append("a", "b").build(), TreeMap.class), new TreeMap<>(EzyMaps.newHashMap("a", "b")));
        assertEquals(unmarshaller.unmarshal(newObjectBuilder().append("a", "b").build(), ConcurrentHashMap.class), new ConcurrentHashMap<>(EzyMaps.newHashMap("a", "b")));

        assertEquals(unmarshaller.unmarshal(newArrayBuilder().append(1, 2).build(), List.class), Lists.newArrayList(1, 2));
        assertEquals(unmarshaller.unmarshal(newArrayBuilder().append(1, 2).build(), Set.class), Sets.newHashSet(1, 2));

        assertEquals(unmarshaller.unmarshal(2, Character.class), Character.valueOf((char)2));
        assertEquals(unmarshaller.unmarshal("2", Character.class), Character.valueOf('2'));
        assertEquals(unmarshaller.unmarshal("", Character.class), Character.valueOf((char)0));

//        Tiny tiny = new EzyObjectInstanceRandom().randomObject(Tiny.class, false);
        Collection<EzyArray> tinyArrayColl = new ArrayList<>();
        tinyArrayColl.add(newArrayBuilder()
                .append("hello", "world")
                .build());

        Tiny[] tinies = unmarshaller.unmarshal(tinyArrayColl, Tiny[].class);
        System.out.println(EzyPrints.print(tinies));
        unmarshaller.unmarshal(new Tiny[0], Tiny[].class);

        assertNull(unmarshaller.unmarshal((Class<? extends EzyReader>)null, (Object)null));
        boolean containsUnwrapper = unmarshaller.containsUnwrapper(Tiny.class);
        assertTrue(containsUnwrapper);

        assertEquals(unmarshaller.unmarshal("FOO", FullEnum.class), FullEnum.FOO);

        unmarshaller.unwrap(new Object(), Object.class);

        assertEquals((Object)unmarshaller.unmarshal(newArrayBuilder()
                .append(newArrayBuilder().append(1, 2))
                .append(newArrayBuilder().append(3, 4)).build(), int[][].class),
                new int[][] {{1, 2}, {3, 4}});
    }

    protected EzyObject newFullDataObject2() {
        EzyArrayBuilder builder1 = newArrayBuilder().append(true, false);
        EzyObjectBuilder objectBuilder = EzyEntityFactory.newObjectBuilder()
                .append("primitiveBooleanValues", builder1)
                .append("primitiveByteValues", newArrayBuilder().append(1, 2))
                .append("primitiveCharValues", newArrayBuilder().append('a', 'b'))
                .append("primitiveDoubleValues", newArrayBuilder().append(3, 4))
                .append("primitiveFloatValues", newArrayBuilder().append(5, 6))
                .append("primitiveIntValues", newArrayBuilder().append(7, 8))
                .append("primitiveLongValues", newArrayBuilder().append(9, 10))
                .append("primitivewrapperShortValues", newArrayBuilder().append(11, 12))
                .append("wrapperBooleanValues", newArrayBuilder().append(true, false))
                .append("wrapperByteValues", newArrayBuilder().append(1, 2))
                .append("wrapperCharValues", newArrayBuilder().append('a', 'b'))
                .append("wrapperDoubleValues", newArrayBuilder().append(3, 4))
                .append("wrapperFloatValues", newArrayBuilder().append(5, 6))
                .append("wrapperIntValues", newArrayBuilder().append(7, 8))
                .append("wrapperLongValues", newArrayBuilder().append(9, 10))
                .append("wrapperShortValues", newArrayBuilder().append(11, 12))
                .append("stringValues", newArrayBuilder().append("x", "y"));
        return objectBuilder.build();
    }}