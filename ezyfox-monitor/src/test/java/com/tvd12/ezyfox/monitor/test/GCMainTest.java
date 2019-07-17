package com.tvd12.ezyfox.monitor.test;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class GCMainTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception{
		while(true) {
			byte[] bytes = new byte[1000000];
			Thread.sleep(50);
			Integer abc = new Integer(10);
			printgc();
			Thread.sleep(1000);
		}
	}
	
	public static void printgc() {
		List<GarbageCollectorMXBean> gcmxb = ManagementFactory.getGarbageCollectorMXBeans();
		StringBuilder builder = new StringBuilder();
		builder.append("count: ").append(gcmxb.size());
		long time = 0;
		int count = 0;
		for(GarbageCollectorMXBean x : gcmxb) {
			time += x.getCollectionTime();
			count += x.getCollectionCount();
		}
		builder.append(", time: " + time + ", count: " + count);
		System.out.println(builder);
	}
	
}
