package com.tvd12.ezyfox.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import com.tvd12.ezyfox.concurrent.EzyErrorScheduledExecutorService;
import com.tvd12.ezyfox.concurrent.EzyThreadFactory;

public final class EzyExecutors {

	private EzyExecutors() {
	}
	
	public static ScheduledExecutorService newScheduledThreadPool(
			int corePoolSize, String threadName) {
		ScheduledExecutorService service = newScheduledThreadPool(corePoolSize, newThreadFactory(threadName));
		return service;
	}
	
	public static ExecutorService newFixedThreadPool(
			int nThreads, String threadName) {
		ExecutorService service = newFixedThreadPool(nThreads, newThreadFactory(threadName));
		return service;
	}
	
	public static ExecutorService newSingleThreadExecutor(
			String threadName) {
		ExecutorService service = newSingleThreadExecutor(newThreadFactory(threadName));
		return service;
	}
	
	public static ScheduledExecutorService newSingleThreadScheduledExecutor(
			String threadName) {
		ScheduledExecutorService service = newSingleThreadScheduledExecutor(newThreadFactory(threadName));
		return service;
	}
	
	// ====== by thread factory ==========
	public static ScheduledExecutorService newScheduledThreadPool(
			int corePoolSize, ThreadFactory threadFactory) {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(corePoolSize, threadFactory);
		return service;
	}
	
	public static ExecutorService newFixedThreadPool(
			int nThreads, ThreadFactory threadFactory) {
		ExecutorService service = Executors.newFixedThreadPool(nThreads, threadFactory);
		return service;
	}
	
	public static ExecutorService newSingleThreadExecutor(
			ThreadFactory threadFactory) {
		ExecutorService service = Executors.newSingleThreadExecutor(threadFactory);
		return service;
	}
	
	public static ScheduledExecutorService newSingleThreadScheduledExecutor(
			ThreadFactory threadFactory) {
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(threadFactory);
		return service;
	}
	
	public static ScheduledExecutorService newErrorScheduledExecutor(
			String errorMessage) {
		ScheduledExecutorService service = new EzyErrorScheduledExecutorService(errorMessage);
		return service;
	}
	
	// ===================================
	
	public static EzyThreadFactory newThreadFactory(String poolName) {
		EzyThreadFactory factory = newThreadFactory(poolName, false, Thread.NORM_PRIORITY);
		return factory;
	}
	
	public static EzyThreadFactory newThreadFactory(
			String poolName, int priority) {
		EzyThreadFactory factory = newThreadFactory(poolName, false, priority);
		return factory;
	}
	
	public static EzyThreadFactory newThreadFactory(
			String poolName, boolean daemon) {
		EzyThreadFactory factory = newThreadFactory(poolName, daemon, Thread.NORM_PRIORITY);
		return factory;
	}
	
	public static EzyThreadFactory newThreadFactory(
			String poolName, boolean daemon, int priority) {
		return EzyThreadFactory.builder()
				.daemon(daemon)
				.priority(priority)
				.poolName(poolName)
				.build();
	}
}
