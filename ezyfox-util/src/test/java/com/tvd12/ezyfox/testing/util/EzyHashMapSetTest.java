package com.tvd12.ezyfox.testing.util;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.common.collect.Sets;
import com.tvd12.ezyfox.util.EzyHashMapSet;
import com.tvd12.ezyfox.util.EzyMapSet;
import com.tvd12.test.base.BaseTest;

public class EzyHashMapSetTest extends BaseTest {

	@Test
	public void test() {
		EzyMapSet<String, String> map = new EzyHashMapSet<>();
		map.addItems("1", "a", "b", "c");
		assertEquals(map.get("1"), Sets.newHashSet("a", "b", "c"));
		map.removeItems("1", "b", "c");
		assertEquals(map.get("1"),Sets.newHashSet("a"));
		map.removeItems("zzz");
	}
	
}
