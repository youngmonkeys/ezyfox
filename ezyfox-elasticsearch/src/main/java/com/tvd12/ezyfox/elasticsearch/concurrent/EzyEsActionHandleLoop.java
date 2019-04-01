package com.tvd12.ezyfox.elasticsearch.concurrent;

import java.util.concurrent.ExecutorService;

import com.tvd12.ezyfox.concurrent.EzyExecutors;
import com.tvd12.ezyfox.elasticsearch.action.EzyEsAction;
import com.tvd12.ezyfox.elasticsearch.action.EzyEsActionWrapper;
import com.tvd12.ezyfox.elasticsearch.callback.EzyEsActionCallback;
import com.tvd12.ezyfox.elasticsearch.handler.EzyEsActionHandler;
import com.tvd12.ezyfox.elasticsearch.handler.EzyEsUncaughtExceptionHandler;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfox.util.EzyStartable;
import com.tvd12.ezyfox.util.EzyStoppable;

public class EzyEsActionHandleLoop 
		extends EzyLoggable 
		implements EzyStartable, EzyStoppable {

	protected volatile boolean active;
	protected final int threadPoolSize;
	protected final EzyEsActionQueue actionQueue;
	protected final EzyEsUncaughtExceptionHandler uncaughtExceptionHandler;
	protected ExecutorService executorService;
	protected static final String THREAD_NAME = "elasticsearch-action-handling";
	
	public EzyEsActionHandleLoop(
			int threadPoolSize, 
			EzyEsActionQueue actionQueue,
			EzyEsUncaughtExceptionHandler uncaughtExceptionHandler) {
		this.actionQueue = actionQueue;
		this.threadPoolSize = threadPoolSize;
		this.uncaughtExceptionHandler = uncaughtExceptionHandler;
	}
	
	@Override
	public void start() throws Exception {
		this.executorService = newExecutorService();
		this.executorService.execute(this::loop);
	}
	
	protected void loop() {
		this.active = true;
		while(active) {
			this.handleActions();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void handleActions() {
		Object response = null;
		Exception exception = null;
		EzyEsAction action = null;
		EzyEsActionCallback callback = null;
		try {
			EzyEsActionWrapper wrapper = actionQueue.takeAction();
			EzyEsActionHandler handler = wrapper.getHandler();
			action = wrapper.getAction();
			callback = wrapper.getCallback();
			response = handler.handle(action);
		}
		catch(Exception e) {
			exception = e;
		}
		finally {
			if(response != null && callback != null)
				callback.onSuccess(action, response);
			if(exception != null) {
				if(callback != null)
					callback.onError(action, exception);
				else if(uncaughtExceptionHandler != null)
					uncaughtExceptionHandler.uncaughtException(action, exception);
				else
					logger.error("call action (" + action.getActionType() + "): " + action + " error", exception);
			}
		}
	}
	
	protected ExecutorService newExecutorService() {
		ExecutorService executorService = EzyExecutors.newFixedThreadPool(
				threadPoolSize, THREAD_NAME);
		Runtime.getRuntime().addShutdownHook(new Thread(() -> executorService.shutdown()));
		return executorService;
	}
	
	@Override
	public void stop() {
		this.active = false;
	}

}
