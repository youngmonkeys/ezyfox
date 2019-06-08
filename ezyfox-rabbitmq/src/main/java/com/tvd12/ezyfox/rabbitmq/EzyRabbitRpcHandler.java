package com.tvd12.ezyfox.rabbitmq;

import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.exception.BadRequestException;
import com.tvd12.ezyfox.exception.NotFoundException;
import com.tvd12.ezyfox.rabbitmq.codec.EzyRabbitDataCodec;
import com.tvd12.ezyfox.rabbitmq.constant.EzyRabbitErrorCodes;
import com.tvd12.ezyfox.rabbitmq.constant.EzyRabbitKeys;
import com.tvd12.ezyfox.rabbitmq.constant.EzyRabbitStatusCodes;
import com.tvd12.ezyfox.rabbitmq.endpoint.EzyRabbitRpcServer;
import com.tvd12.ezyfox.rabbitmq.handler.EzyRabbitRequestHandlers;
import com.tvd12.ezyfox.rabbitmq.handler.EzyRabbitRpcCallHandler;
import com.tvd12.ezyfox.rabbitmq.handler.EzyRabbitActionInterceptor;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfox.util.EzyStartable;
import com.tvd12.ezyfox.util.EzyStoppable;

import lombok.Setter;

public class EzyRabbitRpcHandler
		extends EzyLoggable
		implements EzyRabbitRpcCallHandler, EzyStartable, EzyStoppable {

	@Setter
	protected EzyRabbitActionInterceptor actionInterceptor;

	protected final EzyRabbitRpcServer server;
	protected final EzyRabbitDataCodec dataCodec;
	protected final EzyRabbitRequestHandlers requestHandlers;
	
	public EzyRabbitRpcHandler(
			EzyRabbitRpcServer server,
			EzyRabbitDataCodec dataCodec,
			EzyRabbitRequestHandlers requestHandlers) {
		this.server = server;
		this.server.setCallHandler(this);
		this.dataCodec = dataCodec;
		this.requestHandlers = requestHandlers;
	}
	
	@Override
	public void start() throws Exception {
		server.mainloop();
	}
	
	@Override
	public void stop() {
		try {
			server.stop();
		} catch (Exception e) {
			logger.error("stop rpc server error", e);
		}
	}
	
	@Override
	public void handleFire(BasicProperties requestProperties, byte[] requestBody) {
		String cmd = requestProperties.getType();
        Object requestEntity = null;
        Object responseEntity = null;
        try
        {
            requestEntity = dataCodec.deserialize(cmd, requestBody);
            if (actionInterceptor != null)
                actionInterceptor.intercept(cmd, requestEntity);
            responseEntity = requestHandlers.handle(cmd, requestEntity);
            if (actionInterceptor != null)
                actionInterceptor.intercept(cmd, requestEntity, responseEntity);
        }
        catch (Exception e) {
        		if (actionInterceptor != null)
                actionInterceptor.intercept(cmd, requestEntity, e);
        }
	}
	
	@Override
	public byte[] handleCall(
			BasicProperties requestProperties,
			byte[] requestBody, 
			BasicProperties.Builder replyPropertiesBuilder) {
        String cmd = requestProperties.getType();
        Object requestEntity = null;
        byte[] responseBytes = null;
        Object responseEntity = null;
        try
        {
            requestEntity = dataCodec.deserialize(cmd, requestBody);
            if (actionInterceptor != null)
                actionInterceptor.intercept(cmd, requestEntity);
            responseEntity = requestHandlers.handle(cmd, requestEntity);
            responseBytes = dataCodec.serialize(responseEntity);
            if (actionInterceptor != null)
                actionInterceptor.intercept(cmd, requestEntity, responseEntity);
        }
        catch (Exception e) {
            responseBytes = new byte[0];
            Map<String, Object> responseHeaders = new HashMap<String, Object>();
            if (e instanceof NotFoundException) {
            		responseHeaders.put(EzyRabbitKeys.STATUS, EzyRabbitStatusCodes.NOT_FOUND);
            }
            else if (e instanceof BadRequestException) {
            		BadRequestException badEx = (BadRequestException)e;
                responseHeaders.put(EzyRabbitKeys.STATUS, EzyRabbitStatusCodes.BAD_REQUEST);
                responseHeaders.put(EzyRabbitKeys.ERROR_CODE, badEx.getCode());
            }
            else if (e instanceof IllegalArgumentException) {
            		responseHeaders.put(EzyRabbitKeys.STATUS, EzyRabbitStatusCodes.BAD_REQUEST);
            		responseHeaders.put(EzyRabbitKeys.ERROR_CODE, EzyRabbitErrorCodes.INVALID_ARGUMENT);
            }
            else if(e instanceof UnsupportedOperationException) {
            		responseHeaders.put(EzyRabbitKeys.STATUS, EzyRabbitStatusCodes.BAD_REQUEST);
            		responseHeaders.put(EzyRabbitKeys.ERROR_CODE, EzyRabbitErrorCodes.UNSUPPORTED_OPERATION);
            }
            else {
            		responseHeaders.put(EzyRabbitKeys.STATUS, EzyRabbitStatusCodes.INTERNAL_SERVER_ERROR);
            }
            
            String errorMessage = e.getMessage();
            if(errorMessage == null)
            		errorMessage = e.toString();
            responseHeaders.put(EzyRabbitKeys.MESSAGE, errorMessage);
            replyPropertiesBuilder.headers(responseHeaders);

            if (actionInterceptor != null)
                actionInterceptor.intercept(cmd, requestEntity, e);
        }
        return responseBytes;
	}
	
	protected BasicProperties newReplyProps(BasicProperties rev) {
		return new BasicProperties.Builder()
	        .correlationId(rev.getCorrelationId())
	        .build();
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder implements EzyBuilder<EzyRabbitRpcHandler> {
		protected EzyRabbitRpcServer server;
		protected EzyRabbitDataCodec dataCodec;
		protected EzyRabbitRequestHandlers requestHandlers;
		protected EzyRabbitActionInterceptor actionInterceptor;
		
		public Builder server(EzyRabbitRpcServer server) {
			this.server = server;
			return this;
		}
		
		public Builder dataCodec(EzyRabbitDataCodec dataCodec) {
			this.dataCodec = dataCodec;
			return this;
		}
		
		public Builder requestHandlers(EzyRabbitRequestHandlers requestHandlers) {
			this.requestHandlers = requestHandlers;
			return this;
		}
		
		public Builder actionInterceptor(EzyRabbitActionInterceptor actionInterceptor) {
			this.actionInterceptor = actionInterceptor;
			return this;
		}
		
		@Override
		public EzyRabbitRpcHandler build() {
			EzyRabbitRpcHandler product = new EzyRabbitRpcHandler(
					server,
					dataCodec,
					requestHandlers);
			if(actionInterceptor != null)
				product.setActionInterceptor(actionInterceptor);
			return product;
		}
	}
	
}
