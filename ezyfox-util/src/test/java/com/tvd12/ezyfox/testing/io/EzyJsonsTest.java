package com.tvd12.ezyfox.testing.io;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.io.EzyJsons;
import com.tvd12.test.base.BaseTest;

public class EzyJsonsTest extends BaseTest {

	@Override
	public Class<?> getTestClass() {
		return EzyJsons.class;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void test() {
		Map<String, String> map1 = new HashMap<>();
		map1.put("a", "hello");
		map1.put("b", "world");
		System.out.println((Collection)null);
		System.out.println((Map)null);
		System.out.println(new ArrayList<>());
		System.out.println(new HashMap<>());
		System.out.println(EzyJsons.parse(Lists.newArrayList(1, 2, 3)));
		System.out.println(EzyJsons.parse(map1));
		System.out.println(EzyJsons.parse(map1, EzyJsons.QUOTE_FUNC));
	}
	
}
