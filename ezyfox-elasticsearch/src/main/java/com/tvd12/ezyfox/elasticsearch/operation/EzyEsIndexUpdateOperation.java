package com.tvd12.ezyfox.elasticsearch.operation;

import org.apache.http.Header;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.elasticsearch.EzyIndexedDataClasses;
import com.tvd12.ezyfox.identifier.EzyIdFetchersAware;

public interface EzyEsIndexUpdateOperation extends EzyEsOperation, EzyIdFetchersAware {
	
	void setHeaders(Header[] headers);
	
	void setMarshaller(EzyMarshaller marshaller);
	
	void setIndexedDataClasses(EzyIndexedDataClasses indexedDataClasses);
	
}
