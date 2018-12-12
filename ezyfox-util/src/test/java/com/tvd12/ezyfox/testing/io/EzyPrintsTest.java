package com.tvd12.ezyfox.testing.io;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.io.EzyPrints;
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
	}
	
	@Override
	public Class<?> getTestClass() {
		return EzyPrints.class;
	}
	
}
