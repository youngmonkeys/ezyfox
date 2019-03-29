package com.tvd12.ezyfox.elasticsearch;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.data.EzyIndexedDataIdFetchers;
import com.tvd12.ezyfox.data.annotation.EzyIndexedData;
import com.tvd12.ezyfox.elasticsearch.handler.EzyEsAbstractActionHandler;
import com.tvd12.ezyfox.elasticsearch.handler.EzyEsActionHandler;
import com.tvd12.ezyfox.identifier.EzyIdFetchers;
import com.tvd12.ezyfox.message.annotation.EzyMessage;
import com.tvd12.ezyfox.reflect.EzyReflection;
import com.tvd12.ezyfox.reflect.EzyReflectionProxy;

@SuppressWarnings({"rawtypes", "unchecked"})
public class EzyEsSimpleCallerBuilder implements EzyEsCallerBuilder {

	protected EzyEsClientProxy clientProxy;
	protected EzyMarshaller marshaller;
	protected EzyUnmarshaller unmarshaller;
	protected EzyIdFetchers idFetchers;
	protected EzyIndexedDataClasses indexedDataClasses;
	
	protected Set<String> indexedPackagesToScan = new HashSet<>();
	protected Map<String, EzyEsActionHandler> actionHandlers = new HashMap<>();
	
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
		Set annotatedClasses = reflection.getAnnotatedClasses(EzyIndexedData.class);
		return EzyIndexedDataClasses.builder()
				.addIndexedDataClasses(annotatedClasses)
				.build();
	}
	
	protected EzyIdFetchers newDataIdFetchers(EzyReflection reflection) {
		return EzyIndexedDataIdFetchers.builder()
				.addClasses((Collection)reflection.getAnnotatedClasses(EzyMessage.class))
				.addClasses((Collection)reflection.getAnnotatedClasses(EzyIndexedData.class))
				.build();
	}
	
	protected EzyBindingContext newBindingContext(EzyReflection reflection) {
		return EzyBindingContext.builder()
				.addAllClasses(reflection)
				.build();
	}
}
