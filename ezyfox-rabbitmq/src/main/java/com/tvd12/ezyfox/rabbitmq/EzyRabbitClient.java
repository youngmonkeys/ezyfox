package com.tvd12.ezyfox.rabbitmq;

import com.tvd12.ezyfox.util.EzyShutdownable;

public interface EzyRabbitClient extends EzyShutdownable {
	
	void send(String cmd, Object data);
	
}
