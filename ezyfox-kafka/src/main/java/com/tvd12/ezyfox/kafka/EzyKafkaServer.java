package com.tvd12.ezyfox.kafka;

import com.tvd12.ezyfox.util.EzyDataHandler;
import com.tvd12.ezyfox.util.EzyExceptionHandler;
import com.tvd12.ezyfox.util.EzyShutdownable;
import com.tvd12.ezyfox.util.EzyStartable;

@SuppressWarnings("rawtypes")
public interface EzyKafkaServer extends EzyStartable, EzyShutdownable {
	
	void addDataHandler(EzyDataHandler dataHandler);
	
	void addExceptionHandler(EzyExceptionHandler exceptionHandler);
	
}
