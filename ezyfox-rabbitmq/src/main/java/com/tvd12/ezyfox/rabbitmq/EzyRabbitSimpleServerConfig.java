package com.tvd12.ezyfox.rabbitmq;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.tvd12.ezyfox.builder.EzyBuilder;

import lombok.Getter;

@Getter
public class EzyRabbitSimpleServerConfig implements EzyRabbitServerConfig, Serializable {
	private static final long serialVersionUID = 7480071532492822225L;

	protected String queue;
	protected boolean autoAck;
	protected boolean noLocal;
	protected boolean exclusive;
	protected String consumerTag;
	protected Map<String, Object> arguments;
	
	public EzyRabbitSimpleServerConfig(Builder builder) {
		this.queue = builder.queue;
		this.autoAck = builder.autoAck;
		this.noLocal = builder.noLocal;
		this.exclusive = builder.exclusive;
		this.arguments = builder.arguments;
		this.consumerTag = builder.consumerTag;
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public static class Builder implements EzyBuilder<EzyRabbitServerConfig> {

		protected String queue = "queue";
		protected boolean autoAck = true;
		protected boolean noLocal = false;
		protected boolean exclusive = false;
		protected String consumerTag = "";
		protected Map<String, Object> arguments = new HashMap<>();

		public Builder queue(String queue) {
			this.queue = queue;
			return this;
		}

		public Builder autoAck(boolean autoAck) {
			this.autoAck = autoAck;
			return this;
		}

		public Builder noLocal(boolean noLocal) {
			this.noLocal = noLocal;
			return this;
		}

		public Builder exclusive(boolean exclusive) {
			this.exclusive = exclusive;
			return this;
		}

		public Builder consumerTag(String consumerTag) {
			this.consumerTag = consumerTag;
			return this;
		}

		public Builder arguments(Map<String, Object> arguments) {
			this.arguments.putAll(arguments);
			return this;
		}

		@Override
		public EzyRabbitServerConfig build() {
			return new EzyRabbitSimpleServerConfig(this);
		}
	}

}
