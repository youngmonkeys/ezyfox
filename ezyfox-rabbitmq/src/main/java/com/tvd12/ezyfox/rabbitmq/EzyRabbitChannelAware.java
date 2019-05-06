package com.tvd12.ezyfox.rabbitmq;

import com.rabbitmq.client.Channel;

@Deprecated
public interface EzyRabbitChannelAware {

	void setChannel(Channel channel);
	
}
