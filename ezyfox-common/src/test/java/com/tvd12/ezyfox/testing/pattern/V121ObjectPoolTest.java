package com.tvd12.ezyfox.testing.pattern;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.pattern.EzyObjectPool;
import com.tvd12.test.assertion.Asserts;

import lombok.AllArgsConstructor;

public class V121ObjectPoolTest {

	@Test
	public void inspectTest() throws Exception {
		// given
		ObjectPool sut = (ObjectPool) new ObjectPool.Builder()
				.validationInterval(100)
				.validationDelay(100)
				.objectFactory(() -> new StateObject(true))
				.build();
		sut.addObject(new StateObject(false));
		sut.addObject(new StateObject(false));
		sut.addObject(new StateObject(false));
		
		Thread[] threads = new Thread[10];
		for(int i = 0 ; i < threads.length ; ++i) {
			threads[i] = new Thread(() -> {
				long start = System.currentTimeMillis();
				long elapsedTime = 0;
				while(elapsedTime < 100) {
					sut.addObject();
					elapsedTime = System.currentTimeMillis() - start;
				}
			});
		}
		
		// when
		sut.start();
		
		for(int i = 0 ; i < threads.length ; ++i) {
			threads[i].start();
		}
		Thread.sleep(1000);
		
		// then
		Asserts.assertEquals(3, sut.size());
		sut.destroy();
	}
	
	@AllArgsConstructor
	private static class StateObject {
		boolean shouldRemove;
	}
	
	private static class ObjectPool extends EzyObjectPool<StateObject> {
		
		private ObjectPool(Builder builder) {
			super(builder);
		}
		
		public void addObject() {
			borrowObject();
		}
		
		public void addObject(StateObject object) {
			borrowedObjects.add(object);
		}
		
		public int size() {
			return borrowedObjects.size();
		}
		
		@Override
		protected void removeStaleObjects(List<StateObject> buffer) {
			List<StateObject> staleItems = new ArrayList<>();
			for(StateObject item : buffer) {
				if(item.shouldRemove) {
					staleItems.add(item);
				}
			}
			borrowedObjects.removeAll(staleItems);
		}
		
		private static class Builder extends EzyObjectPool.Builder<StateObject, Builder> {
			
			@Override
			public EzyObjectPool<StateObject> build() {
				return new ObjectPool(this);
			}

			@Override
			protected String getValidationThreadPoolName() {
				return "test";
			}
			
		}
	}
}
