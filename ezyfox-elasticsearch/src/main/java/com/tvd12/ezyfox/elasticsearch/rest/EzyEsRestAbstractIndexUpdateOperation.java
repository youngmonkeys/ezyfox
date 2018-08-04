package com.tvd12.ezyfox.elasticsearch.rest;

import org.apache.http.Header;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.elasticsearch.EzyIndexedDataClasses;
import com.tvd12.ezyfox.elasticsearch.operation.EzyEsIndexUpdateOperation;
import com.tvd12.ezyfox.identifier.EzyIdFetchers;

import lombok.Setter;

@Setter
public abstract class EzyEsRestAbstractIndexUpdateOperation
		extends EzyEsRestAbstractOperation
		implements EzyEsIndexUpdateOperation {

	protected Header[] headers;
	protected EzyMarshaller marshaller;
	protected EzyIdFetchers idFetchers;
	protected EzyIndexedDataClasses indexedDataClasses;
	
}
