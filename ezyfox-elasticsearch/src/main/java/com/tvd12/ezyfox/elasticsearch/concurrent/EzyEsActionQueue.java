package com.tvd12.ezyfox.elasticsearch.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.tvd12.ezyfox.elasticsearch.action.EzyEsActionWrapper;
import com.tvd12.ezyfox.elasticsearch.exception.EzyEsActionQueueFullException;

public class EzyEsActionQueue {

	protected final int capacity;
	protected final BlockingQueue<EzyEsActionWrapper> queue;
	
	public EzyEsActionQueue(int capacity) {
		this.capacity = capacity;
		this.queue = new LinkedBlockingQueue<>(capacity);
	}
	
	public void addAction(EzyEsActionWrapper wrapper) {
		boolean success = this.queue.offer(wrapper);
		if(!success)
			throw new EzyEsActionQueueFullException(capacity, queue.size());
	}
	
	public EzyEsActionWrapper takeAction() throws Exception {
		EzyEsActionWrapper wrapper = this.queue.take();
		return wrapper;
	}
}
