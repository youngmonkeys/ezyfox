package com.tvd12.ezyfox.elasticsearch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.data.EzyIndexedDataIdFetchers;
import com.tvd12.ezyfox.elasticsearch.action.EzyEsActionTypes;
import com.tvd12.ezyfox.elasticsearch.handler.EzyEsAbstractActionHandler;
import com.tvd12.ezyfox.elasticsearch.handler.EzyEsActionHandler;
import com.tvd12.ezyfox.elasticsearch.handler.EzyEsIndexActionHandler;
import com.tvd12.ezyfox.elasticsearch.handler.EzyEsSearchActionHandler;
import com.tvd12.ezyfox.identifier.EzyIdFetchers;
import com.tvd12.ezyfox.reflect.EzyReflection;
import com.tvd12.ezyfox.reflect.EzyReflectionProxy;

@SuppressWarnings({"rawtypes"})
public class EzyEsSimpleCallerBuilder implements EzyEsCallerBuilder {

	protected EzyEsClientProxy clientProxy;
	protected EzyMarshaller marshaller;
	protected EzyUnmarshaller unmarshaller;
	protected EzyIdFetchers idFetchers;
	protected EzyIndexedDataClasses indexedDataClasses;
	
	protected Set<String> indexedPackagesToScan = new HashSet<>();
	protected Map<String, EzyEsActionHandler> actionHandlers = new HashMap<>();
	
	public EzyEsSimpleCallerBuilder() {
		this.actionHandlers.put(EzyEsActionTypes.INDEX, new EzyEsIndexActionHandler());
		this.actionHandlers.put(EzyEsActionTypes.SEARCH, new EzyEsSearchActionHandler());
	}
	
	public EzyEsSimpleCallerBuilder scanIndexedClasses(String packageToScan) {
		this.indexedPackagesToScan.add(packageToScan);
		return this;
	}
	
	public EzyEsSimpleCallerBuilder clientProxy(EzyEsClientProxy clientProxy) {
		this.clientProxy = clientProxy;
		return this;
	}
	
	@Override
	public EzyEsSimpleCallerBuilder addActionHandler(String actionType, EzyEsActionHandler handler) {
		actionHandlers.put(actionType, handler);
		return this;
	}
	
	@Override
	public EzyEsCaller build() {
		this.scanIndexedClasses();
		this.resetupActionHandlers();
		return new EzyEsSimpleCaller(this);
	}
	
	protected void resetupActionHandlers() {
		for(EzyEsActionHandler handler : actionHandlers.values()) {
			if(handler instanceof EzyEsAbstractActionHandler) {
				EzyEsAbstractActionHandler abstractHandler = (EzyEsAbstractActionHandler)handler;
				abstractHandler.setClientProxy(clientProxy);
				abstractHandler.setIdFetchers(idFetchers);
				abstractHandler.setIndexedDataClasses(indexedDataClasses);
				abstractHandler.setMarshaller(marshaller);
				abstractHandler.setUnmarshaller(unmarshaller);
			}
		}
	}
	
	protected void scanIndexedClasses() {
		if(indexedPackagesToScan.isEmpty())
			return;
		EzyReflection reflection = new EzyReflectionProxy(indexedPackagesToScan);
		indexedDataClasses = newIndexedDataClasses(reflection);
		idFetchers = newDataIdFetchers(reflection);
		EzyBindingContext bindingContext = newBindingContext(reflection);
		marshaller = bindingContext.newMarshaller();
		unmarshaller = bindingContext.newUnmarshaller();
	}
	
	protected EzyIndexedDataClasses newIndexedDataClasses(EzyReflection reflection) {
		return EzyIndexedDataClasses.builder()
				.addIndexedDataClasses(reflection)
				.build();
	}
	
	protected EzyIdFetchers newDataIdFetchers(EzyReflection reflection) {
		return EzyIndexedDataIdFetchers.builder()
				.addClasses(reflection)
				.build();
	}
	
	protected EzyBindingContext newBindingContext(EzyReflection reflection) {
		return EzyBindingContext.builder()
				.addAllClasses(reflection)
				.build();
	}
}
