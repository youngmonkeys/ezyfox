package com.tvd12.ezyfox.pattern;

import static com.tvd12.ezyfox.util.EzyProcessor.processWithLogException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

public abstract class EzyObjectProvider<T> 
		extends EzyLoggable 
		implements EzyStartable, EzyDestroyable {
	
	protected final long validationDelay;
	protected final long validationInterval;
	protected final Set<T> providedObjects;
	protected final EzyObjectFactory<T> objectFactory;
	protected final ScheduledExecutorService validationService;
	
	protected final Lock lock = new ReentrantLock();
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	protected EzyObjectProvider(Builder builder) {
		this.objectFactory = builder.getObjectFactory();
		this.validationDelay = builder.validationDelay;
		this.validationInterval = builder.validationInterval;
		this.validationService = builder.getValidationService();
		this.providedObjects = newProvidedObjects();
	}
	
	protected Set<T> newProvidedObjects() {
		return Collections.synchronizedSet(new HashSet<>());
	}
	
	protected T createObject() {
		return objectFactory.newProduct();
	}
	
	protected List<T> getProvidedObjects() {
		return new ArrayList<>(providedObjects);
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
					buffer.addAll(providedObjects);
					removeStaleObjects(buffer);
				}
				catch(Exception e) {
					logger.error("object provider validation error", e);
				}
				finally {
					buffer.clear();
				}
			}
			
		};
	}
	
	protected void removeStaleObjects(List<T> buffer) {}
	
	protected final T provideObject() {
		return provideObject0();
	}
	
	private final T provideObject0() {
		T object = createObject();
		providedObjects.add(object);
		return object;
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
		providedObjects.clear();
	}
	
	protected void shutdownAll() {
		processWithLogException(() -> validationService.shutdown());
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public static abstract class Builder<T, B extends Builder> 
			implements EzyBuilder<EzyObjectProvider<T>> {
		
		protected long validationInterval = 100;
		protected long validationDelay = 3 * 1000;
		protected EzyObjectFactory<T> objectFactory;
		protected ScheduledExecutorService validationService;
		
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
