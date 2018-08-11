package com.tvd12.ezyfox.rabbitmq.factory;

import java.util.concurrent.atomic.AtomicLong;

import com.tvd12.ezyfox.util.EzyLoggable;

public class EzySimpleCorrelationIdFactory
		extends EzyLoggable
		implements EzyCorrelationIdFactory {

	private AtomicLong generator = new AtomicLong();
	
	@Override
	public String newCorrelationId() {
		return String.valueOf(generator.incrementAndGet());
	}
	
}
