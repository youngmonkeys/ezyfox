package com.tvd12.ezyfox.pattern;

import static com.tvd12.ezyfox.util.EzyProcessor.processWithLogException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.concurrent.EzyExecutors;
import com.tvd12.ezyfox.exception.EzyNotImplementedException;
import com.tvd12.ezyfox.util.EzyDestroyable;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfox.util.EzyStartable;

public abstract class EzyObjectPool<T> 
		extends EzyLoggable 
		implements EzyStartable, EzyDestroyable {
	
	protected final int minObjects;
	protected final int maxObjects;
	protected final long validationDelay;
	protected final long validationInterval;
	protected final Queue<T> objectQueue;
	protected final Set<T> borrowedObjects;
	protected final EzyObjectFactory<T> objectFactory;
	protected final ScheduledExecutorService validationService;
	
	protected final Lock lock = new ReentrantLock();
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	protected EzyObjectPool(Builder builder) {
		this.minObjects = builder.minObjects;
		this.maxObjects = builder.maxObjects;
		this.objectFactory = builder.getObjectFactory();
		this.validationDelay = builder.validationDelay;
		this.validationInterval = builder.validationInterval;
		this.validationService = builder.getValidationService();
		this.objectQueue = newObjectQueue();
		this.borrowedObjects = newBorrowedObjects();
		this.initializeObjects();
	}
	
	protected Queue<T> newObjectQueue() {
		return new ConcurrentLinkedQueue<>();
	}
	
	protected Set<T> newBorrowedObjects() {
		return Collections.synchronizedSet(new HashSet<>());
	}
	
	protected void initializeObjects() {
		for(int i = 0 ; i < minObjects ; ++i) {
			T newObject = createObject();
			objectQueue.add(newObject);
		}
	}
	
	protected T createObject() {
		return objectFactory.newProduct();
	}
	
	protected void releaseObject(T object) {
	}
	
	protected List<T> getBorrowedObjects() {
        return new ArrayList<>(borrowedObjects);
    }
	
	protected List<T> getRemainObjects() {
		lock.lock();
		try {
			return new ArrayList<>(objectQueue);
		}
		finally {
			lock.unlock();
		}
	}
	
	@Override
	public void start() throws Exception {
		startValidationService();
	}
	
	protected void startValidationService() {
		List<T> buffer = new ArrayList<>();
		validationService.scheduleWithFixedDelay(newValidationTask(buffer), 
				validationDelay, validationInterval, TimeUnit.MILLISECONDS);
	}
	
	protected Runnable newValidationTask(List<T> buffer) {
		return new Runnable() {
			
			@Override
			public void run() {
				try {
					removeExcessiveObjects();
					buffer.addAll(borrowedObjects);
					removeStaleObjects(buffer);
				}
				catch(Exception e) {
					logger.error("object poll validation error", e);
				}
				finally {
					buffer.clear();
				}
			}
			
			private void removeExcessiveObjects() {
				lock.lock();
				try {
					int poolSize = objectQueue.size();
					if(poolSize > maxObjects)
						removeExcessiveObjects(poolSize - maxObjects);
				}
				finally {
					lock.unlock();
				}
			}
			
			private void removeExcessiveObjects(int size) {
				for(int i = 0 ; i < size ; ++i)
					releaseObject(objectQueue.poll());
				logger.info("object objectQueue: remove {} excessive objects, remain {}", size, objectQueue.size());
			}
		};
	}
	
	protected void removeStaleObjects(List<T> objects) {
		List<T> staleObjects = new ArrayList<>();
		for(T object : objects) {
			if(isStaleObject(object))
				staleObjects.add(object);
		}
		for(T object : objects)
			removeStaleObject(object);
	}
	
	protected void removeStaleObject(T object) {
	}
	
	protected boolean isStaleObject(T object) {
		return false;
	}
	
	protected List<T> getCanBeStaleObjects() {
		return getBorrowedObjects();
	}

	protected final T borrowObject() {
		T obj = borrowOrNewObject();
		borrowedObjects.add(obj);
		return obj;
	}
	
	protected final boolean returnObject(T object) {
		return object != null ? doReturnObject(object) : false;
    }
	
	private T borrowOrNewObject() {
		T rvalue = pollObject();
		if(rvalue == null)
			rvalue = createObject();
		return rvalue;
	}
	
	private T pollObject() {
		T object = null;
		lock.lock();
		try {
			object = objectQueue.poll();
		}
		finally {
			lock.unlock();
		}
		return object;
	}
	
	private boolean doReturnObject(T object) {
        borrowedObjects.remove(object);
        lock.lock();
        try {
        		return objectQueue.offer(object);
        }
        finally {
			lock.unlock();
		}
    }
	
	@Override
	public void destroy() {
		try {
			clearAll();
			shutdownAll();
		}
		catch(Exception e) {
			logger.error("{} error", getClass().getSimpleName(), e);
		}
	}
	
	protected void clearAll() {
		borrowedObjects.clear();
	}
	
	protected void shutdownAll() {
		processWithLogException(() -> validationService.shutdown());
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public static abstract class Builder<T, B extends Builder> 
			implements EzyBuilder<EzyObjectPool<T>> {
		
		protected Queue<T> pool;
		protected int minObjects = 300;
		protected int maxObjects = 300;
		protected long validationInterval = 100;
		protected long validationDelay = 3 * 1000;
		protected EzyObjectFactory<T> objectFactory;
		protected ScheduledExecutorService validationService;
		
		public B pool(Queue<T> pool) {
			this.pool = pool;
			return (B)this;
		}
		
		public B minObjects(int minObjects) {
			this.minObjects = minObjects;
			return (B)this;
		}
		
		public B maxObjects(int maxObjects) {
			this.maxObjects = maxObjects;
			return (B)this;
		}
		
		public B objectFactory(EzyObjectFactory<T> objectFactory) {
			this.objectFactory = objectFactory;
			return (B)this;
		}
		
		public B validationDelay(long validationDelay) {
			this.validationDelay = validationDelay;
			return (B)this;
		}
		
		public B validationInterval(long validationInterval) {
			this.validationInterval = validationInterval;
			return (B)this;
		}
		
		public B validationService(ScheduledExecutorService validationService) {
			this.validationService = validationService;
			return (B)this;
		}
		
		protected abstract String getValidationThreadPoolName();
		
		protected EzyObjectFactory<T> getObjectFactory() {
		    return objectFactory != null ? objectFactory : newObjectFactory(); 
		}
		
		protected EzyObjectFactory<T> newObjectFactory() {
			throw new EzyNotImplementedException("you must implement newObjectFactory method when objectFactory is null");
		}
		
		protected ScheduledExecutorService getValidationService() {
			return validationService != null ? validationService : newValidationService(); 
		}
		
		protected ScheduledExecutorService newValidationService() {
			ScheduledExecutorService service = EzyExecutors.newSingleThreadScheduledExecutor(newValidationThreadFactory());
			Runtime.getRuntime().addShutdownHook(new Thread(() -> service.shutdown()));
			return service;
		}
		
		protected ThreadFactory newValidationThreadFactory() {
			return EzyExecutors.newThreadFactory(getValidationThreadPoolName());
		}
		
	}
	
}
