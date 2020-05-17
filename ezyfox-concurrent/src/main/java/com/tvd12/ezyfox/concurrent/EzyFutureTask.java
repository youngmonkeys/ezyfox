package com.tvd12.ezyfox.concurrent;

import java.util.concurrent.TimeoutException;

import com.tvd12.ezyfox.concurrent.exception.EzyFutureTaskCancelledException;

public class EzyFutureTask implements EzyFuture {

	protected Object result;
	protected Exception exception;
	protected volatile boolean done;
	
	protected final static long NO_TIMEOUT = -1L;
	
	@Override
	public void setResult(Object result) {
		if(result == null)
			throw new NullPointerException("result is null");
		synchronized (this) {
			if(this.result == null)
				this.result = result;
			notify();
		}
	}
	
	@Override
	public void setException(Exception exception) {
		if(exception == null)
			throw new NullPointerException("exception is null");
		synchronized (this) {
			if(this.exception == null)
				this.exception = exception;
			notify();
		}
	}
	
	@Override
	public void cancel(String message) {
		setException(new EzyFutureTaskCancelledException(message));
	}
	
	@Override
	public <V> V get() throws Exception {
		V v = get(NO_TIMEOUT);
		return v;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <V> V get(long timeout) throws Exception {
		synchronized (this) {
			while(hasNoData()) {
				if(timeout > 0) {
					wait(timeout);
					if(hasNoData())
						exception = new TimeoutException("timeout: " + timeout + "ms");
				}
				else { 
					wait();
				}
				break;
			}
			this.done = true;
			if(result != null)
				return (V)result;
			throw exception;
		}
	}
	
	@Override
	public boolean isDone() {
		synchronized (this) {
			return done;
		}
	}
	
	protected boolean hasNoData() {
		return result == null && exception == null;
	}
	
}
