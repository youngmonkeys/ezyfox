package com.tvd12.ezyfox.elasticsearch;

import java.util.Map;

import com.tvd12.ezyfox.elasticsearch.action.EzyEsAction;
import com.tvd12.ezyfox.elasticsearch.handler.EzyEsActionHandler;
import com.tvd12.ezyfox.exception.EzyProxyException;
import com.tvd12.ezyfox.util.EzyLoggable;

import lombok.Getter;

@SuppressWarnings({"rawtypes", "unchecked"})
public class EzyEsSimpleCaller extends EzyLoggable implements EzyEsCaller {
	
	@Getter
	protected final EzyEsClientProxy clientProxy;
	protected final Map<String, EzyEsActionHandler> actionHandlers;
	
	protected EzyEsSimpleCaller(EzyEsSimpleCallerBuilder builder) {
		this.actionHandlers = builder.actionHandlers;
		this.clientProxy = builder.clientProxy;
	}
	
	public <T> T call(EzyEsAction action) {
		EzyEsActionHandler actionHandler = actionHandlers.get(action.getActionType());
		if(actionHandler == null)
			throw new IllegalArgumentException("has no action handler for type: " + action.getActionType());
		try {
			T result = (T)actionHandler.handle(action);
			return result;
		}
		catch(Exception e) {
			throw new EzyProxyException(e);
		}
	}
	
	public static EzyEsSimpleCallerBuilder builder() {
		return new EzyEsSimpleCallerBuilder();
	}
	
}
