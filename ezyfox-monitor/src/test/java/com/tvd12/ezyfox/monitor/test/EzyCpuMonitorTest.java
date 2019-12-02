package com.tvd12.ezyfox.monitor.test;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.monitor.EzyCpuMonitor;
import com.tvd12.test.base.BaseTest;

public class EzyCpuMonitorTest extends BaseTest {

	@Test
	public void test() throws Exception {
		EzyCpuMonitor monitor = new EzyCpuMonitor();
		Thread.sleep(300);
		System.out.println(monitor.getProcessCpuLoad());
		Thread.sleep(300);
		System.out.println(monitor.getSystemCpuLoad());
	}
	
}
