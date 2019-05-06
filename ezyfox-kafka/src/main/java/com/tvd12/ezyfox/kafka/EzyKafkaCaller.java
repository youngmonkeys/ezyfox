package com.tvd12.ezyfox.kafka;

import java.util.concurrent.TimeoutException;

import com.tvd12.ezyfox.codec.EzyEntityCodec;
import com.tvd12.ezyfox.exception.EzyTimeoutException;
import com.tvd12.ezyfox.exception.InternalServerErrorException;
import com.tvd12.ezyfox.kafka.endpoint.EzyKafkaClient;
import com.tvd12.ezyfox.message.EzyMessageTypeFetcher;
import com.tvd12.ezyfox.util.EzyLoggable;

public class EzyKafkaCaller extends EzyLoggable {

	protected final EzyKafkaClient client;
	protected final EzyEntityCodec entityCodec;

	public EzyKafkaCaller(
			EzyKafkaClient client, EzyEntityCodec entityCodec) {
        this.client = client;
        this.entityCodec = entityCodec;
    }
	
	public void send(Object data) {
        if (!(data instanceof EzyMessageTypeFetcher))
            throw new IllegalArgumentException("data class must implement 'EzyMessageTypeFetcher'");
        EzyMessageTypeFetcher mdata = (EzyMessageTypeFetcher)data;
        send(mdata.getMessageType(), data);
    }

    public void send(String cmd, Object data) {
        byte[] requestMessage = entityCodec.serialize(data);
        rawSend(cmd, requestMessage);
    }
	
    protected void rawSend(String cmd, byte[] requestMessage) {
    		try {
			client.send(cmd, requestMessage);
		} 
    		catch (TimeoutException e) {
			throw new EzyTimeoutException("call request: " + cmd + " timeout", e);
		}
    		catch (Exception e) {
    			throw new InternalServerErrorException(e.getMessage(), e);
		}
    }
    
}
