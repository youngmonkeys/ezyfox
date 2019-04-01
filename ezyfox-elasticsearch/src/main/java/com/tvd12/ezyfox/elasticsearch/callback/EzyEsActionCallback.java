package com.tvd12.ezyfox.elasticsearch.callback;

import com.tvd12.ezyfox.elasticsearch.action.EzyEsAction;

public interface EzyEsActionCallback<T> {

	default void onSuccess(EzyEsAction action, T response) {
	}
	
	default void onError(EzyEsAction action, Throwable exception) {
	}
	
}
