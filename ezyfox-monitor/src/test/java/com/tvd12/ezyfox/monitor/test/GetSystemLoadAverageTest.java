package com.tvd12.ezyfox.monitor.test;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

@SuppressWarnings("restriction")
public class GetSystemLoadAverageTest {

	public static void main(String[] args) {
		OperatingSystemMXBean osMxBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		double systemLoadAverage = osMxBean.getSystemLoadAverage();
		System.out.println("systemLoadAverage: " + systemLoadAverage);
	}
	
}
