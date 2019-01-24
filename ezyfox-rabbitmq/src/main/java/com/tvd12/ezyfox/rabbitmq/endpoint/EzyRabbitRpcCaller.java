package com.tvd12.ezyfox.rabbitmq.endpoint;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.RpcClient.Response;
import com.tvd12.ezyfox.codec.EzyEntityCodec;
import com.tvd12.ezyfox.exception.BadRequestException;
import com.tvd12.ezyfox.exception.EzyTimeoutException;
import com.tvd12.ezyfox.exception.InternalServerErrorException;
import com.tvd12.ezyfox.exception.NotFoundException;
import com.tvd12.ezyfox.message.EzyMessageTypeFetcher;
import com.tvd12.ezyfox.rabbitmq.constant.RabbitKeys;
import com.tvd12.ezyfox.rabbitmq.constant.RabbitStatusCodes;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfox.util.EzyStoppable;

public class EzyRabbitRpcCaller extends EzyLoggable implements EzyStoppable {
	
	protected EzyRabbitRpcClient client;
	protected EzyEntityCodec entityCodec;

	public EzyRabbitRpcCaller(
			EzyRabbitRpcClient client, EzyEntityCodec entityCodec) {
        this.client = client;
        this.entityCodec = entityCodec;
    }

	@Override
	public void stop() {
		try {
			client.close();
		} catch (IOException e) {
			logger.error("stop rpc client error", e);
		}
	}
	
	public <T> T call(Object data, Class<T> returnType) {
        if (!(data instanceof EzyMessageTypeFetcher))
            throw new IllegalArgumentException("data class must implement 'EzyMessageTypeFetcher'");
        EzyMessageTypeFetcher mdata = (EzyMessageTypeFetcher)data;
        return call(mdata.getMessageType(), data, returnType);
    }

    public <T> T call(String cmd, Object data, Class<T> returnType) {
        BasicProperties requestProperties = new BasicProperties.Builder()
        		.type(cmd)
        		.build();
        byte[] requestMessage = entityCodec.serialize(data);
        Response responseData = rawCall(requestProperties, requestMessage);
        BasicProperties reponseProperties = responseData.getProperties();
        Map<String, Object> responseHeaders = reponseProperties.getHeaders();
        processResponseHeaders(responseHeaders);
        byte[] responseBody = responseData.getBody();
        T responseEntity = entityCodec.deserialize(responseBody, returnType);
        return responseEntity;
    }
    
    protected void processResponseHeaders(Map<String, Object> responseHeaders) {
    		if(responseHeaders == null)
    			return;
    		if(!responseHeaders.containsKey(RabbitKeys.STATUS))
    			return;
    		int status = (int)responseHeaders.get(RabbitKeys.STATUS);
        String message = (String)responseHeaders.get(RabbitKeys.MESSAGE);
        int code = (int) responseHeaders.get(RabbitKeys.ERROR_CODE);
        if (status == RabbitStatusCodes.NOT_FOUND)
            throw new NotFoundException(message);
        if (status == RabbitStatusCodes.BAD_REQUEST)
            throw new BadRequestException(code, message);
        if (status == RabbitStatusCodes.INTERNAL_SERVER_ERROR)
            throw new InternalServerErrorException(message);
    }
    
    protected Response rawCall(
    		BasicProperties requestProperties, byte[] requestMessage) {
    		try {
			Response responseData = client.doCall(requestProperties, requestMessage);
			return responseData;
		} 
    		catch (IOException e) {
    			throw new InternalServerErrorException(e.getMessage(), e);
		}
    		catch (TimeoutException e) {
			throw new EzyTimeoutException("call request: " + requestProperties.getType() + " timeout", e);
		}
    }

}
