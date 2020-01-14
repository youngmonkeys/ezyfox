package com.tvd12.ezyfox.testing.concurrent;

import java.util.concurrent.TimeoutException;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.concurrent.EzyCallableFutureTask;
import com.tvd12.ezyfox.concurrent.EzyFutureTask;
import com.tvd12.ezyfox.concurrent.callback.EzyResultCallback;
import com.tvd12.test.base.BaseTest;

public class EzyCallableFutureTaskTest extends BaseTest {

	@Test
	public void test() throws Exception {
		EzyFutureTask task = new EzyCallableFutureTask();
		try {
			task.setResult(null);
		}
		catch (Exception e) {
			assert e instanceof NullPointerException;
		}
		
		try {
			task.setException(null);
		}
		catch (Exception e) {
			assert e instanceof NullPointerException;
		}
		
		Thread thread = new Thread(() -> {
			try {
				Integer value = task.get(3000L);
				assert value == 123;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		});
		thread.start();
		Thread.sleep(100);
		task.setResult(123);
		Thread.sleep(100);
		task.setResult(456);
		Thread.sleep(100);
		assert task.get().equals(123);
		
		EzyFutureTask task2 = new EzyCallableFutureTask();
		thread = new Thread(() -> {
			try {
				Integer value = task2.get(3000L);
				assert value == 123;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		});
		thread.start();
		Thread.sleep(100);
		task2.cancel("test cancel");
		task2.cancel("test 111");
		Thread.sleep(100);
	}
	
	@Test
	public void timeoutCaseTest() throws Exception {
		EzyFutureTask task = new EzyFutureTask();
		Thread thread = new Thread(() -> {
			try {
				task.get(10);
			}
			catch (Exception e) {
				assert e instanceof TimeoutException;
			}
		});
		thread.start();
		Thread.sleep(100);
	}
	
	@Test
	public void callbackCaseTest() {
		EzyFutureTask task = new EzyCallableFutureTask(r -> {
			System.out.println("result: " + r);
		});
		task.setResult("hello");
		task.setResult("world");
		
		task = new EzyCallableFutureTask(new EzyResultCallback<String>() {
			@Override
			public void onResponse(String response) {
			}
			@Override
			public void onException(Exception e) {
				System.out.println("exception: " + e);
			}
		});
		task.cancel("test 1");
		task.cancel("test 2");
		
		EzyResultCallback<String> callback = r -> {};
		callback.onException(new Exception("test"));
	}
}
