package com.tvd12.ezyfox.rabbitmq;

import com.tvd12.ezyfox.rabbitmq.message.EzyRabbitMessage;
import com.tvd12.ezyfox.util.EzyShutdownable;

public interface EzyRabbitClient extends EzyShutdownable {
	
	void send(EzyRabbitMessage message);
	
}
