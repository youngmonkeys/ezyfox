package com.tvd12.ezyfox.example.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Lock;

import com.tvd12.ezyfox.concurrent.EzyExecutors;
import com.tvd12.ezyfox.concurrent.EzyFuture;
import com.tvd12.ezyfox.concurrent.EzyFutureTask;
import com.tvd12.ezyfox.concurrent.EzyHashMapLockProxyProvider;
import com.tvd12.ezyfox.concurrent.EzyMapLockProvider;

public class ConcurrentExample {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		ExecutorService threadPool = EzyExecutors.newFixedThreadPool(3, "test-thread-name");
		
		EzyFuture future = new EzyFutureTask();
		Thread newThread = new Thread(() -> {
			future.setResult("Hello World");
		});
		newThread.start();
		String result = future.get();
		System.out.println(result);
		
		EzyMapLockProvider lockProvider = new EzyHashMapLockProxyProvider();
		Lock lock = lockProvider.provideLock("test");
		lock.lock();
		try {
			// do something
		}
		finally {
			lock.unlock();
			lockProvider.removeLock("test");
		}
	}
	
}
