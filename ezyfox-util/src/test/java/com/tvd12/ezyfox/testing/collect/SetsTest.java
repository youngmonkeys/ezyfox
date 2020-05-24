package com.tvd12.ezyfox.testing.collect;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.test.base.BaseTest;

public class SetsTest extends BaseTest {

	@Override
	public Class<?> getTestClass() {
		return Sets.class;
	}
	
	@Test
	public void test() {
		Set<String> set = new HashSet<>();
		set.add("1");
		set.add("2");
		assertEquals(Sets.newHashSet(Lists.newArrayList("1", "2")), set);
		assert Sets.toSet(set) == set;
		assert Sets.toSet(Arrays.asList(1, 2, 3)).size() == 3;
	}
	
}
