package com.tvd12.ezyfox.elasticsearch.action;

import com.tvd12.ezyfox.elasticsearch.callback.EzyEsActionCallback;
import com.tvd12.ezyfox.elasticsearch.handler.EzyEsActionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@SuppressWarnings("rawtypes")
public class EzyEsSimpleActionWrapper implements EzyEsActionWrapper {

	protected final EzyEsAction action;
	protected final EzyEsActionHandler handler;
	protected final EzyEsActionCallback callback;

}
