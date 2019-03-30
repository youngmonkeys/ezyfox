package com.tvd12.ezyfox.elasticsearch.handler;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.elasticsearch.EzyEsClientProxy;
import com.tvd12.ezyfox.elasticsearch.EzyIndexedDataClasses;
import com.tvd12.ezyfox.elasticsearch.action.EzyEsAction;
import com.tvd12.ezyfox.identifier.EzyIdFetchers;
import com.tvd12.ezyfox.util.EzyLoggable;

import lombok.Setter;

public abstract class EzyEsAbstractActionHandler<A extends EzyEsAction, R>
		extends EzyLoggable
		implements EzyEsActionHandler<A, R> {

	@Setter
	protected EzyEsClientProxy clientProxy;
	@Setter
	protected EzyIdFetchers idFetchers;
	@Setter
	protected EzyIndexedDataClasses indexedDataClasses;
	@Setter
	protected EzyMarshaller marshaller;
	@Setter
	protected EzyUnmarshaller unmarshaller;
	
}
