package com.tvd12.ezyfox.testing.entity;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.testing.CommonBaseTest;

import static org.testng.Assert.*;

public class EzyArray3Test extends CommonBaseTest {

    @Test
    public void test1() {
        EzyArray array = newArrayBuilder()
                .append(new boolean[][] {{true, false}, {false, true}})
                .build();
        assertEquals((Object)array.toArray(boolean[].class), new boolean[][] {{true, false}, {false, true}});
    }

    public void test2() {
        EzyArray array = newArrayBuilder()
                .append(new byte[][] {{1,2},{3,4}})
                .build();
        assertEquals((Object)array.toArray(byte[].class), new byte[][] {{1,2},{3,4}});
    }

    @Test
    public void test3() {
        EzyArray array = newArrayBuilder()
                .append(new char[][] {{'a', 'b'}, {'c', 'd'}})
                .build();
        assertEquals((Object)array.toArray(char[].class), new char[][] {{'a', 'b'}, {'c', 'd'}});
    }

    @Test
    public void test4() {
        EzyArray array = newArrayBuilder()
                .append(new double[][] {{1,2},{3,4}})
                .build();
        assertEquals((Object)array.toArray(double[].class), new double[][] {{1,2},{3,4}});
    }

    @Test
    public void test5() {
        EzyArray array = newArrayBuilder()
                .append(new float[][] {{1,2}, {3,4}})
                .build();
        assertEquals((Object)array.toArray(float[].class), new float[][] {{1,2}, {3,4}});
    }

    @Test
    public void test6() {
        EzyArray array = newArrayBuilder()
                .append(new int[][] {{1,2}, {3,4}})
                .build();
        assertEquals((Object)array.toArray(int[].class), new int[][] {{1,2}, {3,4}});
    }

    @Test
    public void test7() {
        EzyArray array = newArrayBuilder()
                .append(new long[][] {{1,2}, {3,4}})
                .build();
        assertEquals((Object)array.toArray(long[].class), new long[][] {{1,2}, {3,4}});
    }

    @Test
    public void test8() {
        EzyArray array = newArrayBuilder()
                .append(new short[][] {{1,2}, {3,4}})
                .build();
        assertEquals((Object)array.toArray(short[].class), new short[][] {{1,2}, {3,4}});
    }

    @Test
    public void test9() {
        EzyArray array = newArrayBuilder()
                .append(new String[][] {{"1","2"}, {"3","4"}})
                .build();
        assertEquals((Object)array.toArray(String[].class), new String[][] {{"1","2"}, {"3","4"}});
    }

}
