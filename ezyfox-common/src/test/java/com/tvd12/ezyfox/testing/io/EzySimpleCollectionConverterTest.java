package com.tvd12.ezyfox.testing.io;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.io.EzyCollectionConverter;
import com.tvd12.ezyfox.io.EzySingletonCollectionConverter;
import com.tvd12.ezyfox.sercurity.EzyBase64;
import com.tvd12.ezyfox.testing.CommonBaseTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.testng.Assert.assertEquals;

public class EzySimpleCollectionConverterTest extends CommonBaseTest {

    private final EzyCollectionConverter collectionConverter;

    public EzySimpleCollectionConverterTest() {
        super();
        this.collectionConverter = EzySingletonCollectionConverter.getInstance();
    }

    @Test
    public void test() {
        Collection<Boolean> booleans = Lists.newArrayList(true, false, true);
        assertEquals(collectionConverter.toArray(booleans, Boolean.class),
            new Boolean[]{true, false, true});

        Collection<Byte> bytes = Lists.newArrayList((byte) 1, (byte) 2, (byte) 3);
        assertEquals(collectionConverter.toArray(bytes, Byte.class),
            new Byte[]{(byte) 1, (byte) 2, (byte) 3});

        Collection<Character> characters = Lists.newArrayList('a', 'b', 'c');
        assertEquals(collectionConverter.toArray(characters, Character.class),
            new Character[]{'a', 'b', 'c'});

        Collection<Double> doubles = Lists.newArrayList(1D, 2D, 3D);
        assertEquals(collectionConverter.toArray(doubles, Double.class),
            new Double[]{1D, 2D, 3D});

        Collection<Float> floats = Lists.newArrayList(1F, 2F, 3F);
        assertEquals(collectionConverter.toArray(floats, Float.class),
            new Float[]{1F, 2F, 3F});

        Collection<Integer> integers = Lists.newArrayList(1, 2, 3);
        assertEquals(collectionConverter.toArray(integers, Integer.class),
            new Integer[]{1, 2, 3});

        Collection<Long> longs = Lists.newArrayList(1L, 2L, 3L);
        assertEquals(collectionConverter.toArray(longs, Long.class),
            new Long[]{1L, 2L, 3L});

        Collection<Short> shorts = Lists.newArrayList((short) 1, (short) 2, (short) 3);
        assertEquals(collectionConverter.toArray(shorts, Short.class),
            new Short[]{(short) 1, (short) 2, (short) 3});

        Collection<String> strings = Lists.newArrayList("a", "b", "c");
        assertEquals(collectionConverter.toArray(strings, String.class),
            new String[]{"a", "b", "c"});

        EzyObject object = newObjectBuilder().append("1", "a").build();
        assertEquals(collectionConverter.toArray(Lists.newArrayList(object), EzyObject.class),
            new EzyObject[]{object});
    }

    @Test
    public void test1() {
        Collection<Boolean> booleans = Lists.newArrayList(true, false, true);
        assertEquals(collectionConverter.toArray(booleans, boolean.class),
            new boolean[]{true, false, true});

        Collection<Byte> bytes = Lists.newArrayList((byte) 1, (byte) 2, (byte) 3);
        assertEquals(collectionConverter.toArray(bytes, byte.class),
            new byte[]{(byte) 1, (byte) 2, (byte) 3});

        Collection<Character> characters = Lists.newArrayList('a', 'b', 'c');
        assertEquals(collectionConverter.toArray(characters, char.class),
            new char[]{'a', 'b', 'c'});

        Collection<Double> doubles = Lists.newArrayList(1D, 2D, 3D);
        assertEquals(collectionConverter.toArray(doubles, double.class),
            new double[]{1D, 2D, 3D});

        Collection<Float> floats = Lists.newArrayList(1F, 2F, 3F);
        assertEquals(collectionConverter.toArray(floats, float.class),
            new float[]{1F, 2F, 3F});

        Collection<Integer> integers = Lists.newArrayList(1, 2, 3);
        assertEquals(collectionConverter.toArray(integers, int.class),
            new int[]{1, 2, 3});

        Collection<Long> longs = Lists.newArrayList(1L, 2L, 3L);
        assertEquals(collectionConverter.toArray(longs, long.class),
            new long[]{1L, 2L, 3L});

        Collection<Short> shorts = Lists.newArrayList((short) 1, (short) 2, (short) 3);
        assertEquals(collectionConverter.toArray(shorts, short.class),
            new short[]{(short) 1, (short) 2, (short) 3});

        Collection<String> strings = Lists.newArrayList("a", "b", "c");
        assertEquals(collectionConverter.toArray(strings, String.class),
            new String[]{"a", "b", "c"});
    }

    @Test
    public void test2() {
        Collection<Collection<Boolean>> booleanss = new ArrayList<>();
        booleanss.add(Lists.newArrayList(true, false, true));
        booleanss.add(Lists.newArrayList(false, true, false));
        assertEquals((Object) collectionConverter.toArray(booleanss, boolean[].class),
            new boolean[][]{{true, false, true}, {false, true, false}});

        Collection<Collection<Byte>> byteArrays = new ArrayList<>();
        byteArrays.add(Lists.newArrayList((byte) 1, (byte) 2, (byte) 3));
        byteArrays.add(Lists.newArrayList((byte) 4, (byte) 5, (byte) 6));
        assertEquals((Object) collectionConverter.toArray(byteArrays, byte[].class),
            new byte[][]{{(byte) 1, (byte) 2, (byte) 3}, {(byte) 4, (byte) 5, (byte) 6}});

        Collection<Collection<Character>> characterss = new ArrayList<>();
        characterss.add(Lists.newArrayList('a', 'b', 'c'));
        characterss.add(Lists.newArrayList('d', 'e', 'f'));
        assertEquals((Object) collectionConverter.toArray(characterss, char[].class),
            new char[][]{{'a', 'b', 'c'}, {'d', 'e', 'f'}});

        Collection<Collection<Double>> doubless = new ArrayList<>();
        doubless.add(Lists.newArrayList(1D, 2D, 3D));
        doubless.add(Lists.newArrayList(4D, 5D, 6D));
        assertEquals((Object) collectionConverter.toArray(doubless, double[].class),
            new double[][]{{1D, 2D, 3D}, {4D, 5D, 6D}});

        Collection<Collection<Float>> floatss = new ArrayList<>();
        floatss.add(Lists.newArrayList(1F, 2F, 3F));
        floatss.add(Lists.newArrayList(4F, 5F, 6F));
        assertEquals((Object) collectionConverter.toArray(floatss, float[].class),
            new float[][]{{1F, 2F, 3F}, {4F, 5F, 6F}});

        Collection<Collection<Integer>> integerss = new ArrayList<>();
        integerss.add(Lists.newArrayList(1, 2, 3));
        integerss.add(Lists.newArrayList(4, 5, 6));
        assertEquals((Object) collectionConverter.toArray(integerss, int[].class),
            new int[][]{{1, 2, 3}, {4, 5, 6}});

        Collection<Collection<Long>> longss = new ArrayList<>();
        longss.add(Lists.newArrayList(1L, 2L, 3L));
        longss.add(Lists.newArrayList(4L, 5L, 6L));
        assertEquals((Object) collectionConverter.toArray(longss, long[].class),
            new long[][]{{1L, 2L, 3L}, {4L, 5L, 6L}});

        Collection<Collection<Short>> shortss = new ArrayList<>();
        shortss.add(Lists.newArrayList((short) 1, (short) 2, (short) 3));
        shortss.add(Lists.newArrayList((short) 4, (short) 5, (short) 6));
        assertEquals((Object) collectionConverter.toArray(shortss, short[].class),
            new short[][]{{(short) 1, (short) 2, (short) 3}, {(short) 4, (short) 5, (short) 6}});

        Collection<Collection<String>> stringss = new ArrayList<>();
        stringss.add(Lists.newArrayList("a", "b", "c"));
        stringss.add(Lists.newArrayList("d", "e", "f"));
        assertEquals((Object) collectionConverter.toArray(stringss, String[].class),
            new String[][]{{"a", "b", "c"}, {"d", "e", "f"}});
    }


    @Test
    public void test3() {
        Collection<Collection<Boolean>> booleanss = new ArrayList<>();
        booleanss.add(Lists.newArrayList(true, false, true));
        booleanss.add(Lists.newArrayList(false, true, false));
        assertEquals((Object) collectionConverter.toArray(booleanss, Boolean[].class),
            new Boolean[][]{{true, false, true}, {false, true, false}});

        Collection<Collection<Byte>> byteArrays = new ArrayList<>();
        byteArrays.add(Lists.newArrayList((byte) 1, (byte) 2, (byte) 3));
        byteArrays.add(Lists.newArrayList((byte) 4, (byte) 5, (byte) 6));
        assertEquals((Object) collectionConverter.toArray(byteArrays, Byte[].class),
            new Byte[][]{{(byte) 1, (byte) 2, (byte) 3}, {(byte) 4, (byte) 5, (byte) 6}});

        Collection<Collection<Character>> characterss = new ArrayList<>();
        characterss.add(Lists.newArrayList('a', 'b', 'c'));
        characterss.add(Lists.newArrayList('d', 'e', 'f'));
        assertEquals((Object) collectionConverter.toArray(characterss, Character[].class),
            new Character[][]{{'a', 'b', 'c'}, {'d', 'e', 'f'}});

        Collection<Collection<Double>> doubless = new ArrayList<>();
        doubless.add(Lists.newArrayList(1D, 2D, 3D));
        doubless.add(Lists.newArrayList(4D, 5D, 6D));
        assertEquals((Object) collectionConverter.toArray(doubless, Double[].class),
            new Double[][]{{1D, 2D, 3D}, {4D, 5D, 6D}});

        Collection<Collection<Float>> floatss = new ArrayList<>();
        floatss.add(Lists.newArrayList(1F, 2F, 3F));
        floatss.add(Lists.newArrayList(4F, 5F, 6F));
        assertEquals((Object) collectionConverter.toArray(floatss, Float[].class),
            new Float[][]{{1F, 2F, 3F}, {4F, 5F, 6F}});

        Collection<Collection<Integer>> integerss = new ArrayList<>();
        integerss.add(Lists.newArrayList(1, 2, 3));
        integerss.add(Lists.newArrayList(4, 5, 6));
        assertEquals((Object) collectionConverter.toArray(integerss, Integer[].class),
            new Integer[][]{{1, 2, 3}, {4, 5, 6}});

        Collection<Collection<Long>> longss = new ArrayList<>();
        longss.add(Lists.newArrayList(1L, 2L, 3L));
        longss.add(Lists.newArrayList(4L, 5L, 6L));
        assertEquals((Object) collectionConverter.toArray(longss, Long[].class),
            new Long[][]{{1L, 2L, 3L}, {4L, 5L, 6L}});

        Collection<Collection<Short>> shortss = new ArrayList<>();
        shortss.add(Lists.newArrayList((short) 1, (short) 2, (short) 3));
        shortss.add(Lists.newArrayList((short) 4, (short) 5, (short) 6));
        assertEquals((Object) collectionConverter.toArray(shortss, Short[].class),
            new Short[][]{{(short) 1, (short) 2, (short) 3}, {(short) 4, (short) 5, (short) 6}});

        Collection<Collection<String>> stringss = new ArrayList<>();
        stringss.add(Lists.newArrayList("a", "b", "c"));
        stringss.add(Lists.newArrayList("d", "e", "f"));
        assertEquals((Object) collectionConverter.toArray(stringss, String[].class),
            new String[][]{{"a", "b", "c"}, {"d", "e", "f"}});
    }

    @Test
    public void test4() {
        assertEquals((Object) collectionConverter.toArray(
                Lists.newArrayList(
                    EzyBase64.encode2utf(new byte[]{1, 2, 3}),
                    EzyBase64.encode2utf(new byte[]{3, 4, 5})),
                Byte[].class),
            new Byte[][]{{1, 2, 3}, {3, 4, 5}});
    }

    @Test
    public void test5() {
        assertEquals((Object) collectionConverter.toArray(
                Lists.newArrayList(
                    new byte[]{1, 2, 3},
                    new byte[]{3, 4, 5}),
                Byte[].class),
            new Byte[][]{{1, 2, 3}, {3, 4, 5}});
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void test6() {
        EzySingletonCollectionConverter.getInstance().toArray(Lists.newArrayList(), Void.class);
    }
}
