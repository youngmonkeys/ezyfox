package com.tvd12.ezyfox.example.generics;

import java.util.List;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.util.EzyHashMapSet;
import com.tvd12.ezyfox.util.EzyMapSet;

public class EzyGenericsExample {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		List<String> stringList = Lists.newArrayList("Hello", "World");
		
		EzyMapSet<String, Integer> multimap = new EzyHashMapSet<>();
		multimap.addItems("Hello", 1, 2, 3);
		multimap.addItems("World", 4, 5, 6);
	}
	
}
