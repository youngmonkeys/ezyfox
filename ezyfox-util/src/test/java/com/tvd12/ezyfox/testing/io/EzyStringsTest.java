package com.tvd12.ezyfox.testing.io;

import static org.testng.Assert.assertEquals;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.test.base.BaseTest;

import lombok.AllArgsConstructor;

public class EzyStringsTest extends BaseTest {

	@Test
	public void test() throws Exception {
		assertEquals("abc", EzyStrings.newUtf("abc".getBytes("UTF-8")));
		assertEquals("abc", EzyStrings.newUtf(ByteBuffer.wrap("abc".getBytes("UTF-8")), 3));
		assertEquals(EzyStrings.getUtfBytes("abc"), "abc".getBytes("UTF-8"));
		assertEquals(EzyStrings.newString('a', 3), "aaa");
		assertEquals(EzyStrings.quote(null), "\"null\"");
	}
	
	@Test(expectedExceptions = {IllegalArgumentException.class})
	public void test1() {
		EzyStrings.newString(new byte[] {1, 2, 3}, "kkk");
	}
	
	@Test(expectedExceptions = {IllegalArgumentException.class})
	public void test2() {
		EzyStrings.getBytes("abc", "hhh");
	}
	
	@Test
	public void test3() {
		String[] array = new String[] {"1", "2", "3"};
		assert EzyStrings.getString(array, 1, "x").equals("2");
		assert EzyStrings.getString(array, 100, "x").equals("x");
	}
	
	@Test
	public void test4() {
		Collection<String> collection1 = Lists.newArrayList("1", "2", "3");
		Collection<Integer> collection2 = Lists.newArrayList(1, 2, 3);
		Collection<ClassX> collection3 = Lists.newArrayList(new ClassX("n1", "v1"), new ClassX("n2", "v2"));
		System.out.println(EzyStrings.wrap(collection1, "[", "]", ",", true));
		System.out.println(EzyStrings.wrap(collection2, "[", "]", ",", true));
		System.out.println(EzyStrings.wrap(collection3, "[", "]", ",", true));
		System.out.println(EzyStrings.wrap(null, "[", "]", ",", true));
		System.out.println(EzyStrings.wrap(null, "[", "]", ",", false));
		System.out.println(EzyStrings.wrap(new ArrayList<>(), "[", "]", ",", false));
		System.out.println(EzyStrings.wrap(Arrays.asList(1L), "[", "]", ",", false));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void test5() {
		assert EzyStrings.isEmpty(null);
		assert EzyStrings.isEmpty("");
		assert !EzyStrings.isEmpty(" ");
		assert !EzyStrings.isEmpty("\t");
		assert !EzyStrings.isEmpty("\n");
		assert !EzyStrings.isEmpty("a");
		
		assert EzyStrings.isNoContent(null);
		assert EzyStrings.isNoContent("");
		assert EzyStrings.isNoContent(" ");
		assert EzyStrings.isNoContent("\t");
		assert EzyStrings.isNoContent("\n");
		assert !EzyStrings.isNoContent("a");
		
		System.out.println(EzyStrings.join(new double[] {1.1, 2.2, 3.3}, ","));
		System.out.println(EzyStrings.join(new int[] {1, 2, 3}, ". "));
		System.out.println(EzyStrings.join(new long[] {1, 2, 3}, ";- "));
		System.out.println(EzyStrings.join(new String[] {"a", "b", "c"}, "><"));
		
		Collection c1 = Lists.newArrayList("a", "b");
		System.out.println(EzyStrings.join(c1, ","));
		Collection<?> c2 = Lists.newArrayList("a", "b");
		System.out.println(EzyStrings.join(c2, ","));
	}
	
	@Override
	public Class<?> getTestClass() {
		return EzyStrings.class;
	}
	
	@AllArgsConstructor
	public static class ClassX {
		
		private final String name;
		private final String value;
		
		@Override
		public String toString() {
			return new StringBuilder()
					.append("{")
					.append("\"name\":").append("\"" + name + "\"").append(",")
					.append("\"value\":").append("\"" + value + "\"")
					.append("}")
					.toString();
		}
	}
	
}
