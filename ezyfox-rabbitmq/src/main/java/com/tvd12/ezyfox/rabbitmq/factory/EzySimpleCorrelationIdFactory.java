package com.tvd12.ezyfox.rabbitmq.factory;

import java.util.concurrent.atomic.AtomicLong;

import com.tvd12.ezyfox.util.EzyLoggable;

public class EzySimpleCorrelationIdFactory
		extends EzyLoggable
		implements EzyCorrelationIdFactory {

	private final String prefix;
	private final AtomicLong generator = new AtomicLong();
	
	public EzySimpleCorrelationIdFactory() {
		this("");
	}
	
	public EzySimpleCorrelationIdFactory(String prefix) {
		this.prefix = prefix;
	}
	
	@Override
	public String newCorrelationId() {
		return new StringBuilder()
				.append(prefix)
				.append(generator.incrementAndGet())
				.toString();
	}
	
}
