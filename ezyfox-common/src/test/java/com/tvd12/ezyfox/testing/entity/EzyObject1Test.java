package com.tvd12.ezyfox.testing.entity;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.entity.EzyHashMap;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

import static org.testng.Assert.*;

public class EzyObject1Test extends EzyEntityTest {

	@Test
	public void test() {
	}
	
	@Test
	public void test1() {
		Map<String, Object> map = new HashMap<>();
		EzyObjectBuilder firstBuilder 
			= newObjectBuilder().append("1'", "a'");
		map.put("1", "a");
		EzyObject object = newObjectBuilder()
				.append(map)
				.append("2", firstBuilder)
				.append("3", "c")
				.append("4", "d")
				.append("5", "e")
				.build();
		assertEquals(object.get("1"), "a");
		assertEquals(object.getWithDefault("1", "b"), "a");
		assertEquals(object.get("3", String.class), "c");
		assertEquals(object.remove("4"), "d");
		assertEquals(object.compute("6", (k,v) -> v != null ? v : "f"), "f");
		assertEquals(object.size(), 5);
		assertEquals(object.isEmpty(), false);
		assertEquals(object.containsKey("6"), true);
		assertEquals(object.keySet().containsAll(Sets.newHashSet("1", "2", "3", "5", "6")), true);
		object.entrySet();
		object.toMap();
		object.toString();
		EzyObject clone = object.duplicate();
		assertEquals(object, clone);
		assertTrue(clone.keySet().containsAll(Sets.newHashSet("1", "2", "3", "5", "6")));
		object.clear();
		assertEquals(object.getWithDefault("1", "b"), "b");
	}
	
	@Test(expectedExceptions = IllegalStateException.class)
	public void test2() {
		EzyObject object = new EzyHashMap(null, null) {
			private static final long serialVersionUID = -4366815253239566713L;

			@Override
			public Object clone() throws CloneNotSupportedException {
				throw new CloneNotSupportedException();
			}
		};
		object.duplicate();
	}
	
	@Test
	public void equalsAndHashCodeTest() {
		EzyObject a = EzyEntityFactory.newObjectBuilder()
				.append("a", 1)
				.append("b", 2)
				.build();
		assert !a.equals(null);
		assert a.equals(a);
		assert !a.equals(new Object());
		EzyObject b = EzyEntityFactory.newObjectBuilder()
				.append("a", 1)
				.append("b", 2)
				.build();
		assert a.equals(b);
		EzyObject c = EzyEntityFactory.newObjectBuilder()
				.append("a", 1)
				.build();
		assert !a.equals(c);
	}
	
	@Test
	public void compareToTest() {
		EzyObject a = EzyEntityFactory.newObjectBuilder()
				.append("a", 1)
				.append("b", 2)
				.build();
		EzyObject b = EzyEntityFactory.newObjectBuilder()
				.append("a", 1)
				.append("b", 2)
				.build();
		EzyObject c = EzyEntityFactory.newObjectBuilder()
				.append("a", 1)
				.build();
		EzyObject d = EzyEntityFactory.newObjectBuilder()
				.append("a", 1)
				.append("b", 1)
				.build();
		EzyObject e = EzyEntityFactory.newObjectBuilder()
				.append("a", 3)
				.append("b", 1)
				.build();
		EzyObject f = EzyEntityFactory.newObjectBuilder()
				.append("a", (Object)null)
				.append("b", 1)
				.build();
		EzyObject g = EzyEntityFactory.newObjectBuilder()
				.append("a", (Object)null)
				.append("b", 1)
				.build();
		EzyObject h = EzyEntityFactory.newObjectBuilder()
				.append("a", new Object())
				.append("b", 1)
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
		
		EzyObject y = EzyEntityFactory.newObjectBuilder()
				.append("a", EzyEntityFactory.newObjectBuilder()
						.append("hello", "world"))
				.append("b", EzyEntityFactory.newArrayBuilder()
						.append(1, 2, 3))
				.build();
		EzyObject z = EzyEntityFactory.newObjectBuilder()
				.append("a", EzyEntityFactory.newObjectBuilder()
						.append("hello", "world"))
				.append("b", EzyEntityFactory.newArrayBuilder()
						.append(1, 2, 3))
				.build();	
		assert y.compareTo(z) == 0;
	}
}
