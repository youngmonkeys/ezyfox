package com.tvd12.ezyfox.elasticsearch;

import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.elasticsearch.handler.EzyEsActionHandler;

@SuppressWarnings("rawtypes")
public interface EzyEsCallerBuilder extends EzyBuilder<EzyEsCaller> {

	EzyEsCallerBuilder addActionHandler(String actionType, EzyEsActionHandler handler);

}
