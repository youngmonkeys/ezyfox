package com.tvd12.ezyfox.rabbitmq.endpoint;

import com.rabbitmq.client.Channel;
import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfox.util.EzyStartable;

@Deprecated
public abstract class EzyRabbitEndpoint 
		extends EzyLoggable 
		implements EzyStartable {

	protected String queue;
	protected Channel channel;
	
	protected EzyRabbitEndpoint(String queue, Channel channel) {
        this.queue = queue;
        this.channel = channel;
	}
	
	@SuppressWarnings("unchecked")
	public static abstract class Builder<B extends Builder<B>> 
			implements EzyBuilder<EzyRabbitEndpoint> {
		protected String queue;
		protected Channel channel;
		
		public B queue(String queue) {
			this.queue = queue;
			return (B)this;
		}
		
		public B channel(Channel channel) {
			this.channel = channel;
			return (B)this;
		}
		
	}
	
}
