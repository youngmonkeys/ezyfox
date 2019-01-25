package com.tvd12.ezyfox.rabbitmq.endpoint;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.RpcClient;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.utility.BlockingCell;
import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.rabbitmq.EzyCorrelationIdFactory;
import com.tvd12.ezyfox.rabbitmq.factory.EzySimpleCorrelationIdFactory;

public class EzyRabbitRpcClient extends RpcClient {

	protected EzyCorrelationIdFactory correlationIdFactory;
	
	public EzyRabbitRpcClient(
			Channel channel, 
			String exchange, 
			String routingKey,
			String replyQueueName, int timeout) throws IOException {
        this(channel, 
        		exchange, 
        		routingKey, 
        		replyQueueName,
        		timeout, new EzySimpleCorrelationIdFactory());
    }
	
	public EzyRabbitRpcClient(
			Channel channel, 
			String exchange, 
			String routingKey,
			String replyQueueName,
			int timeout, 
			EzyCorrelationIdFactory correlationIdFactory) throws IOException {
        super(channel, exchange, routingKey, replyQueueName, timeout);
        this.correlationIdFactory = correlationIdFactory;
    }
 
	public Response doCall(AMQP.BasicProperties props, byte[] message, int timeout)
	        throws IOException, ShutdownSignalException, TimeoutException {
        checkConsumer();
        BlockingCell<Object> k = new BlockingCell<Object>();
        Map<String, BlockingCell<Object>> continuationMap = getContinuationMap();
        String replyId = correlationIdFactory.newCorrelationId();
        props = ((props==null) ? new AMQP.BasicProperties.Builder() : props.builder())
                .correlationId(replyId).build();
        synchronized (continuationMap) {
            continuationMap.put(replyId, k);
        }
        publish(props, message);
        Object reply;
        try {
            reply = k.uninterruptibleGet(timeout);
        } catch (TimeoutException ex) {
            // Avoid potential leak.  This entry is no longer needed by caller.
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
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder implements EzyBuilder<EzyRabbitRpcClient> {
		protected int timeout;
		protected Channel channel; 
		protected String exchange; 
		protected String routingKey; 
		protected String replyQueueName;
		protected EzyCorrelationIdFactory correlationIdFactory;
		
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
		
		public Builder correlationIdFactory(EzyCorrelationIdFactory correlationIdFactory) {
			this.correlationIdFactory = correlationIdFactory;
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
						timeout, getCorrelationIdFactory());
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
