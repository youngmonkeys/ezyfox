package com.tvd12.ezyfox.rabbitmq.endpoint;

import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.RpcClient.Response;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.utility.BlockingCell;
import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.rabbitmq.factory.EzyCorrelationIdFactory;
import com.tvd12.ezyfox.rabbitmq.factory.EzySimpleCorrelationIdFactory;
import com.tvd12.ezyfox.rabbitmq.handler.EzyRabbitResponseConsumer;
import com.tvd12.ezyfox.util.EzyLoggable;

public class EzyRabbitRpcClient extends EzyLoggable {

    protected final Channel channel;
    protected final String exchange;
    protected final String requestRoutingKey;
    protected final int defaultTimeout;
	protected final String replyQueueName;
	protected final String replyRoutingKey;
	protected final EzyCorrelationIdFactory correlationIdFactory;
	protected final Map<String, BlockingCell<Object>> continuationMap;
	protected final EzyRabbitResponseConsumer unconsumedResponseConsumer;
	private DefaultConsumer consumer;
	
	protected final static int NO_TIMEOUT = -1;
	
	public EzyRabbitRpcClient(
			Channel channel, 
			String exchange, 
			String routingKey,
			String replyQueueName,
			String replyRoutingKey, 
			int timeout,
			EzyRabbitResponseConsumer unconsumedResponseConsumer) throws IOException {
        this(channel, 
        		exchange, 
        		routingKey, 
        		replyQueueName,
        		replyRoutingKey,
        		timeout, 
        		new EzySimpleCorrelationIdFactory(),
        		unconsumedResponseConsumer);
    }
	
	public EzyRabbitRpcClient(
			Channel channel, 
			String exchange, 
			String requestRoutingKey,
			String replyQueueName,
			String replyRoutingKey,
			int defaultTimeout, 
			EzyCorrelationIdFactory correlationIdFactory,
			EzyRabbitResponseConsumer unconsumedResponseConsumer) throws IOException {
        this.channel = channel;
        this.exchange = exchange;
        this.requestRoutingKey = requestRoutingKey;
        this.replyQueueName = replyQueueName;
        this.replyRoutingKey = replyRoutingKey;
        this.defaultTimeout = defaultTimeout;
        this.correlationIdFactory = correlationIdFactory;
        this.continuationMap = new HashMap<>(); 
        this.unconsumedResponseConsumer = unconsumedResponseConsumer;
        this.consumer = setupConsumer();
    }
	
	protected DefaultConsumer setupConsumer() throws IOException {
        DefaultConsumer newConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleShutdownSignal(String consumerTag,
                                             ShutdownSignalException signal) {
                synchronized (continuationMap) {
                    for (Entry<String, BlockingCell<Object>> entry : continuationMap.entrySet()) {
                        entry.getValue().set(signal);
                    }
                }
                consumer = null;
            }

            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body)
                    throws IOException {
                synchronized (continuationMap) {
                    String replyId = properties.getCorrelationId();
                    BlockingCell<Object> blocker = continuationMap.remove(replyId);
                    if (blocker == null) {
                    		if(unconsumedResponseConsumer != null) {
                    			unconsumedResponseConsumer.consume(properties, body);
                    		}
                    		else { 
                    			logger.warn("No outstanding request for correlation ID {}", replyId);
                    		}
                    } else {
                        blocker.set(new Response(consumerTag, envelope, properties, body));
                    }
                }
            }
        };
        channel.basicConsume(replyQueueName, true, newConsumer);
        return newConsumer;
    }
	
	public Response doCall(AMQP.BasicProperties props, byte[] message)
	        throws IOException, TimeoutException {
	        return doCall(props, message, defaultTimeout);
	}
 
	public Response doCall(AMQP.BasicProperties props, byte[] message, int timeout)
	        throws IOException, ShutdownSignalException, TimeoutException {
        checkConsumer();
        String replyId = correlationIdFactory.newCorrelationId();
        AMQP.BasicProperties.Builder propertiesBuilder = (props != null) 
        			? props.builder() 
        			: new AMQP.BasicProperties.Builder();
        	AMQP.BasicProperties newProperties = propertiesBuilder
        			.correlationId(replyId)
        			.replyTo(replyRoutingKey)
        			.build();
        	BlockingCell<Object> k = new BlockingCell<Object>();
        synchronized (continuationMap) {
            continuationMap.put(replyId, k);
        }
        publish(newProperties, message);
        Object reply;
        try {
            reply = k.uninterruptibleGet(timeout);
        } catch (TimeoutException ex) {
        		synchronized (continuationMap) {
        			continuationMap.remove(replyId);
        		}
            throw ex;
        }
        if (reply instanceof ShutdownSignalException) {
            ShutdownSignalException sig = (ShutdownSignalException) reply;
            ShutdownSignalException wrapper =
                new ShutdownSignalException(sig.isHardError(),
                                            sig.isInitiatedByApplication(),
                                            sig.getReason(),
                                            sig.getReference());
            wrapper.initCause(sig);
            throw wrapper;
        } else {
            return (Response) reply;
        }
	}
	
	protected void publish(AMQP.BasicProperties props, byte[] message) 
			throws IOException {
		channel.basicPublish(exchange, requestRoutingKey, props, message);
	}
	
	public void checkConsumer() throws IOException {
		if (consumer == null) {
            throw new EOFException("RpcClient is closed");
        }
	}
	
	public void close() throws IOException {
        if (consumer != null) {
            channel.basicCancel(consumer.getConsumerTag());
            consumer = null;
        }
    }
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder implements EzyBuilder<EzyRabbitRpcClient> {
		protected int timeout;
		protected Channel channel; 
		protected String exchange; 
		protected String routingKey; 
		protected String replyQueueName;
		protected String replyRoutingKey; 
		protected EzyCorrelationIdFactory correlationIdFactory;
		protected EzyRabbitResponseConsumer unconsumedResponseConsumer;
		
		public Builder timeout(int timeout) {
			this.timeout = timeout;
			return this;
		}
		
		public Builder channel(Channel channel) {
			this.channel = channel;
			return this;
		}
		
		public Builder exchange(String exchange) {
			this.exchange = exchange;
			return this;
		}
		
		public Builder routingKey(String routingKey) {
			this.routingKey = routingKey;
			return this;
		}
		
		public Builder replyQueueName(String replyQueueName) {
			this.replyQueueName = replyQueueName;
			return this;
		}
		
		public Builder replyRoutingKey(String replyRoutingKey) {
			this.replyRoutingKey = replyRoutingKey;
			return this;
		}
		
		public Builder correlationIdFactory(EzyCorrelationIdFactory correlationIdFactory) {
			this.correlationIdFactory = correlationIdFactory;
			return this;
		}
		
		public Builder unconsumedResponseConsumer(EzyRabbitResponseConsumer unconsumedResponseConsumer) {
			this.unconsumedResponseConsumer = unconsumedResponseConsumer;
			return this;
		}
		
		@Override
		public EzyRabbitRpcClient build() {
			try {
				return new EzyRabbitRpcClient(
						channel, 
						exchange, 
						routingKey, 
						replyQueueName, 
						replyRoutingKey,
						timeout, 
						getCorrelationIdFactory(),
						unconsumedResponseConsumer);
			}
			catch(Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		private EzyCorrelationIdFactory getCorrelationIdFactory() {
			if(correlationIdFactory != null)
				return correlationIdFactory;
			return new EzySimpleCorrelationIdFactory();
		}
	}
	
}
