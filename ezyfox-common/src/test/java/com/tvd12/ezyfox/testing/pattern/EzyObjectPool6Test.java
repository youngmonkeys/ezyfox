package com.tvd12.ezyfox.testing.pattern;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.concurrent.EzyExecutors;
import com.tvd12.ezyfox.exception.EzyNotImplementedException;
import com.tvd12.ezyfox.pattern.EzyObjectFactory;
import com.tvd12.ezyfox.pattern.EzyObjectPool;

public class EzyObjectPool6Test {

	@Test
	public void test() throws Exception {
		TestObjectPool provider = TestObjectPool.builder()
				.maxObjects(2)
				.validationDelay(100)
				.validationInterval(100)
				.objectFactory(new StringFactory())
				.build();
		provider.start();
		provider.newString();
		Thread.sleep(500);
		provider.newString();
		provider.newString();
		Thread.sleep(500);
//		provider.getAddedStrings();
		provider.destroy();
		
	}
	
	@Test
	public void test1() throws Exception {
		new TestObjectPool.Builder2()
			.validationInterval(100)
			.validationService(EzyExecutors.newScheduledThreadPool(1, "test"))
			.build();
	}
	
	@Test(expectedExceptions = {EzyNotImplementedException.class})
	public void test2() throws Exception {
		TestObjectPool.builder()
			.validationInterval(100)
			.validationService(EzyExecutors.newScheduledThreadPool(1, "test"))
			.build();
	}
	
	@Test
	public void test3() throws Exception {
		TestObjectPool provider = new TestObjectPool2.Builder()
				.validationDelay(100)
				.validationInterval(100)
				.objectFactory(new StringFactory())
				.build();
		provider.start();
		Thread.sleep(500);
		try {
			provider.newString();
		}
		catch(Exception e) {
		}
		provider.newString();
		Thread.sleep(500);
//		provider.getAddedStrings();
		provider.destroy();
		
	}
	
	private static class StringFactory implements EzyObjectFactory<String> {

		@Override
		public String newProduct() {
			return "default";
		}
		
	}
	
	private static class TestObjectPool2 extends TestObjectPool {

		private volatile boolean createObjectPassed = false;
		private volatile boolean clearAllPassed = false;
		private volatile boolean removeStaleObjectsPassed = false;
		
		public TestObjectPool2(Builder builder) {
			super(builder);
		}
		
		@Override
		protected void removeStaleObjects() {
			if(!removeStaleObjectsPassed) {
				removeStaleObjectsPassed = true;
				throw new IllegalStateException();
			}
		}
		
		@Override
		protected void clearAll() {
			if(!clearAllPassed) {
				clearAllPassed = true;
				throw new IllegalStateException();
			}
		}
		
		@Override
		protected String createObject() {
			if(!createObjectPassed ) {
				createObjectPassed = true;
				throw new IllegalStateException();
			}
			return super.createObject();
		}
		
		public static Builder builder() {
			return new Builder();
		}
		
		public static class Builder extends TestObjectPool.Builder {
			@Override
			public TestObjectPool build() {
				return new TestObjectPool2(this);
			}
		}
		
	}
	
	private static class TestObjectPool extends EzyObjectPool<String> {
		
		public TestObjectPool(Builder builder) {
			super(builder);
		}
		
		public String newString() {
			return borrowObject();
		}
		
		public static Builder builder() {
			return new Builder();
		}
		
		public static class Builder extends EzyObjectPool.Builder<String, Builder> {

			@Override
			public TestObjectPool build() {
				return new TestObjectPool(this);
			}

			@Override
			protected String getValidationThreadPoolName() {
				return "test";
			}

		}
		
		public static class Builder2 extends Builder {
			
			@Override
			protected EzyObjectFactory<String> newObjectFactory() {
				return new StringFactory();
			}
		}
		
	}
	
}
