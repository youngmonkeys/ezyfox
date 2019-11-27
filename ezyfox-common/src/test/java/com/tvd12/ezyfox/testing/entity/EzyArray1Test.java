package com.tvd12.ezyfox.testing.entity;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Collection;
import java.util.Date;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyArrayList;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.io.EzyDates;

public class EzyArray1Test extends EzyEntityTest {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void test() {
		EzyArrayBuilder builder = newArrayBuilder()
				.append(new Boolean(true))
				.append(new Byte((byte)1))
				.append(new Character('a'))
				.append(new Double(2D))
				.append(new Float(3F))
				.append(new Integer(4))
				.append(new Long(5L))
				.append(new Short((short)6))
				.append(new String("str"));
		
		builder
			.append(Lists.newArrayList(true, false, true))
			.append(new byte[] {((byte)1)})
			.append(new char[] {'a', 'b', 'c'})
			.append(Lists.newArrayList(1D,2D,3D))
			.append(Lists.newArrayList(4F,5F,6F))
			.append(Lists.newArrayList(6,7,8))
			.append(Lists.newArrayList(9L,10L,11L))
			.append(Lists.newArrayList((short)12))
			.append(Lists.newArrayList("1", "2", "3"));
		
		builder
			.append(EzyDates.parse("2017-05-30T00:00:00:000"));
		
		EzyArray array = builder.build();
		
		assertEquals(array.get(0), Boolean.TRUE);
		assertEquals(array.get(1), new Byte((byte)1));
		assertEquals(array.get(2), new Byte((byte)'a'));
		assertEquals(array.get(3), new Double(2D));
		assertEquals(array.get(4), new Float(3F));
		assertEquals(array.get(5), new Integer(4));
		assertEquals(array.get(6), new Long(5L));
		assertEquals(array.get(7), new Short((short)6));
		assertEquals(array.get(8), new String("str"));
		
		assertEquals(array.get(0, boolean.class), Boolean.TRUE);
		assertEquals(array.get(1, byte.class), new Byte((byte)1));
		assertEquals(array.get(2, char.class), new Character('a'));
		assertEquals(array.get(3, double.class), new Double(2D));
		assertEquals(array.get(4, float.class), new Float(3F));
		assertEquals(array.get(5, int.class), new Integer(4));
		assertEquals(array.get(6, long.class), new Long(5L));
		assertEquals(array.get(7, short.class), new Short((short)6));
		assertEquals(array.get(8, String.class), new String("str"));
		
		assertEquals(array.get(0, Boolean.class), Boolean.TRUE);
		assertEquals(array.get(1, Byte.class), new Byte((byte)1));
		assertEquals(array.get(2, Character.class), new Character('a'));
		assertEquals(array.get(3, Double.class), new Double(2D));
		assertEquals(array.get(4, Float.class), new Float(3F));
		assertEquals(array.get(5, Integer.class), new Integer(4));
		assertEquals(array.get(6, Long.class), new Long(5L));
		assertEquals(array.get(7, Short.class), new Short((short)6));
		assertEquals(array.get(8, String.class), new String("str"));
		
		assertEquals(array.get(9, boolean[].class), new boolean[] {true, false, true});
		assertEquals(array.get(10, byte[].class), new byte[] {1});
		assertEquals(array.get(11, char[].class), new char[] {'a', 'b', 'c'});
		assertEquals(array.get(12, double[].class),  new double[] {1D, 2D, 3D});
		assertEquals(array.get(13, float[].class), new float[] {4F,5F,6F});
		assertEquals(array.get(14, int[].class), new int[] {6,7,8});
		assertEquals(array.get(15, long[].class), new long[] {9L,10L,11L});
		assertEquals(array.get(16, short[].class), new short[] {12});
		assertEquals(array.get(17, String[].class), new String[] {"1", "2", "3"});
		
		assertEquals(array.get(9, Boolean[].class), new Boolean[] {true, false, true});
		assertEquals(array.get(10, byte[].class), new byte[] {1});
		assertEquals(array.get(11, Character[].class), new Character[] {'a', 'b', 'c'});
		assertEquals(array.get(12, Double[].class),  new Double[] {1D, 2D, 3D});
		assertEquals(array.get(13, Float[].class), new Float[] {4F,5F,6F});
		assertEquals(array.get(14, Integer[].class), new Integer[] {6,7,8});
		assertEquals(array.get(15, Long[].class), new Long[] {9L,10L,11L});
		assertEquals(array.get(16, Short[].class), new Short[] {12});
		assertEquals(array.get(17, String[].class), new String[] {"1", "2", "3"});
		
		assertEquals(array.get(18, Date.class), EzyDates.parse("2017-05-30T00:00:00:000"));
		
		assertTrue(((Collection)array.get(9)).containsAll(Lists.newArrayList(true, false, true)));
		assert array.size() > 0;
		array.clear();
		assert array.size() == 0;
	}
	
	@Test
	public void test1() {
		EzyArrayBuilder builder = newArrayBuilder()
				.append("a", "b", "c");
		EzyArray array = builder.build();
		assertEquals(array.size(), 3);
		assertEquals(array.get(1), "b");
		array.set(1, "d");
		assertEquals(array.get(1), "d");
		array.remove(1);
		assertEquals(array.size(), 2);
		assertEquals(array.get(1), "c");
		array.forEach(i -> i.toString());
		assertTrue(array.iterator().hasNext());
		assertEquals(array.toList().size(), 2);
		assertEquals(array.toList(String.class).size(), 2);
		assertEquals(array.toArray(String.class), new String[] {"a", "c"});
		EzyArray dup = array.duplicate(); 
		assertTrue(array != dup);
		assertTrue(dup.toList().containsAll(Sets.newHashSet("a", "c")));
		EzyArray sub = array.sub(0, 1);
		assertEquals(sub.size(), 1);
	}
	
	@Test(expectedExceptions = {IllegalStateException.class})
	public void test2() {
		EzyArray array = new EzyArrayList(null, null, null) {
			private static final long serialVersionUID = 8714822620207886718L;

			@Override
			public Object clone() throws CloneNotSupportedException {
				throw new CloneNotSupportedException();
			}
		};
		array.duplicate();
	}
	
	@Test
	public void equalsAndHashCodeTest() {
		EzyArray a = EzyEntityFactory.newArrayBuilder()
				.append(1, 2, 3)
				.build();
		assert !a.equals(null);
		assert a.equals(a);
		assert !a.equals(new Object());
		EzyArray b = EzyEntityFactory.newArrayBuilder()
				.append(1, 2, 3)
				.build();
		assert a.equals(b);
		EzyArray c = EzyEntityFactory.newArrayBuilder()
				.append(1, 2)
				.build();
		assert !a.equals(c);
	}
	
	@Test
	public void compareToTest() {
		EzyArray a = EzyEntityFactory.newArrayBuilder()
				.append(1, 2, 3)
				.build();
		EzyArray b = EzyEntityFactory.newArrayBuilder()
				.append(1, 2, 3)
				.build();
		EzyArray c = EzyEntityFactory.newArrayBuilder()
				.append(1, 2)
				.build();
		EzyArray d = EzyEntityFactory.newArrayBuilder()
				.append(1, 1, 1)
				.build();
		EzyArray e = EzyEntityFactory.newArrayBuilder()
				.append(3, 2, 3)
				.build();
		EzyArray f = EzyEntityFactory.newArrayBuilder()
				.append(null, 2, 3)
				.build();
		EzyArray g = EzyEntityFactory.newArrayBuilder()
				.append(null, 2, 3)
				.build();
		EzyArray h = EzyEntityFactory.newArrayBuilder()
				.append(new Object(), 2, 3)
				.build();
		assert a.compareTo(b) == 0;
		assert a.compareTo(c) > 0;
		assert a.compareTo(d) > 0;
		assert a.compareTo(e) < 0;
		assert a.compareTo(f) > 0;
		assert f.compareTo(a) < 0;
		assert f.compareTo(g) == 0;
		try {
			a.compareTo(h);
		}
		catch (Exception ex) {
			assert ex instanceof IllegalArgumentException;
		}
		try {
			h.compareTo(a);
		}
		catch (Exception ex) {
			assert ex instanceof IllegalArgumentException;
		}
	}
	
	public static class ClassA implements Cloneable {
		
		@Override
		public Object clone() throws CloneNotSupportedException {
			throw new CloneNotSupportedException();
		}
	}
}
