package com.tvd12.ezyfox.testing.concurrent;

import java.util.concurrent.TimeoutException;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.concurrent.EzyFutureTask;
import com.tvd12.test.base.BaseTest;

public class EzyFutureTaskTest extends BaseTest {

	@Test
	public void test() throws Exception {
		EzyFutureTask task = new EzyFutureTask();
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
	
}
