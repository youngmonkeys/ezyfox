package com.tvd12.ezyfox.testing.io;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.io.EzyPrints;
import com.tvd12.ezyfox.io.EzyPrints.Array2DPrinter;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;

public class EzyPrintsTest extends BaseTest {

    @Test
    public void test() {
        System.err.println(EzyPrints.printBits((byte)5));
        System.err.println(EzyPrints.printBits(new byte[] {1, 2, 3}));

        System.err.println(EzyPrints.print(null));
        System.err.println(EzyPrints.print(new boolean[] {true, false, true}));
        System.err.println(EzyPrints.print(new byte[] {1, 2, 3}));
        System.err.println(EzyPrints.print(new char[] {'a', 'b', 'c'}));
        System.err.println(EzyPrints.print(new double[] {1, 2, 3}));
        System.err.println(EzyPrints.print(new float[] {1, 2, 3}));
        System.err.println(EzyPrints.print(new int[] {1, 2, 3}));
        System.err.println(EzyPrints.print(new long[] {1, 2, 3}));
        System.err.println(EzyPrints.print(new short[] {1, 2, 3}));
        System.err.println(EzyPrints.print(new String[] {"x", "y", "z"}));
        System.err.println(EzyPrints.print(Lists.newArrayList("x", "y", "z")));

        int[][] array2dInts = new int[][] {{1, 2, 3}, {4, 5, 6}};
        System.out.println(EzyPrints.print2d(array2dInts));

        String[][] array2dStrings = new String[][] {{"1", "2", "3"}, {"4", "5", "6"}};
        System.out.println(EzyPrints.print2d(array2dStrings));

        System.out.println(EzyPrints.printHex(new byte[] {122, 22}));
        System.out.println(EzyPrints.printHexLowercase(new byte[] {122, 22}));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test2() {
        new Array2DPrinter(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test3() {
        new Array2DPrinter(new StringBuilder(), null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test4() {
        Array2DPrinter printer = new Array2DPrinter(new StringBuilder(), "null");
        printer.print(new String[0][0]);
        printer.print((String[][])null);
    }

    @Test
    public void test5() {
        StringBuilder builder = new StringBuilder();
        Array2DPrinter printer = new Array2DPrinter(builder, "null");
        String[][] strss = new String[4][];
        strss[0] = null;
        strss[1] = new String[] {"1", "2", null};
        strss[2] = new String[] {"1", "2", null, "3"};
        strss[3] = new String[] {"1", "2"};
        printer.print(strss);
        System.out.println(builder);
    }

    @Test
    public void printBytesToIntsTest() {
        // given
        byte[] bytes = new byte[] {-1, -2, 3};

        // when
        String actual = EzyPrints.printBytesToInts(bytes);

        // then
        Asserts.assertEquals("[255, 254, 3]", actual);
    }

    @Override
    public Class<?> getTestClass() {
        return EzyPrints.class;
    }
}