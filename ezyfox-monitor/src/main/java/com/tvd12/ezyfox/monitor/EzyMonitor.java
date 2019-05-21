package com.tvd12.ezyfox.monitor;

import lombok.Getter;

@Getter
public class EzyMonitor {
	
	protected final EzyCpuMonitor cpuMonitor;
	protected final EzyMemoryMonitor memoryMonitor;
	protected final EzyThreadsMonitor threadsMonitor;

	public EzyMonitor() {
		this.cpuMonitor = new EzyCpuMonitor();
		this.memoryMonitor = new EzyMemoryMonitor();
		this.threadsMonitor = new EzyThreadsMonitor();
	}
	
}
