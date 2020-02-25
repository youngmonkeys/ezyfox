package com.tvd12.ezyfox.testing.io;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.io.EzyCollections;
import com.tvd12.test.base.BaseTest;

public class EzyCollectionsTest extends BaseTest {

	@Override
	public Class<?> getTestClass() {
		return EzyCollections.class;
	}
	
	@Test
	public void test() {
		assertTrue(EzyCollections.containsAny(
				Sets.newHashSet("1", "2", "3"), Sets.newHashSet("2", "4", "5")));
		assertEquals(EzyCollections.countItems(
				Sets.newHashSet("ab", "ac", "de", "ef", "ah"), str -> str.startsWith("a")), 3);
		assertEquals(EzyCollections.flatMapToInt(
				Sets.newHashSet('a', 'b', 'c'), ch -> (int)ch), (int)'a' + (int)'b' + (int)'c');
		Set<String> set = Sets.newHashSet("a", "b", "c");
		assertEquals(EzyCollections.getItem(set, (i) -> i.equals("b")), "b");
		assertNull(EzyCollections.getItem(set, (i) -> i.equals("z")));
		assertEquals(EzyCollections.toArray(Lists.newArrayList("a", "b", "c"), size -> new String[size]), 
				new String[] {"a", "b", "c"});
		assertEquals(EzyCollections.sumItemsToDouble(Lists.newArrayList(1, 2, 3), i -> i), 6.0D);
		assertEquals(EzyCollections.sumItemsToInt(Lists.newArrayList(1, 2, 3), i -> i), 6);
		assertEquals(EzyCollections.sumItemsToLong(Lists.newArrayList(1, 2, 3), i -> i), 6L);
		assert EzyCollections.isEmpty(null);
		assert EzyCollections.isEmpty(new ArrayList<>());
		assert !EzyCollections.isEmpty(Arrays.asList(1, 2, 3));
	}
	
	@Test
	public void test1() {
		assert EzyCollections.isEmpty(null);
		assert EzyCollections.isEmpty(Lists.newArrayList());
		assert !EzyCollections.isEmpty(Lists.newArrayList(1));
	}
	
}
