package com.tvd12.ezyfox.kafka.handler;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public class EzyKafkaRequestHandlers {
	protected final Map<String, EzyKafkaRequestHandler> handlers = new HashMap<>();

    public void addHandler(String cmd, EzyKafkaRequestHandler handler) {
    		handlers.put(cmd, handler);
    }

    public EzyKafkaRequestHandler getHandler(String cmd) {
    		EzyKafkaRequestHandler handler = handlers.get(cmd);
        if (handler != null)
            return handlers.get(cmd);
        throw new IllegalArgumentException("has no handler with command: " + cmd);
    }

	public Object handle(String cmd, Object request) throws Exception {
        EzyKafkaRequestHandler handler = getHandler(cmd);
        Object answer = handler.handle(request);
        return answer;
    }
}
