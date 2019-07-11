package com.tvd12.ezyfox.testing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.tvd12.test.performance.Performance;

public class ListSetTest {

	public static void main(String[] args) {
		int size = 1000;
		Set<String> set = new HashSet<>();
		for(int i = 0 ; i < size ; ++i) {
			set.add(String.valueOf(i));
		}
		System.out.println("start test1");
		long time1 = new Performance()
			.test(() -> new ArrayList<>(set))
			.getTime();
		System.out.println("time1: " + time1);

		System.out.println("start test2");
		long time2 = new Performance()
			.test(() -> new HashSet<>(set))
			.getTime();
		System.out.println("time2: " + time2);
	}
	
}
