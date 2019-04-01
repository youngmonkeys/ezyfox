package com.tvd12.ezyfox.elasticsearch.exception;

public class EzyEsActionQueueFullException extends RuntimeException {
	private static final long serialVersionUID = 4362207870303307340L;
	
	public EzyEsActionQueueFullException(int capacity, int currentSize) {
		super("queue is full, capacity: " + capacity + " current size: " + currentSize);
	}

}
