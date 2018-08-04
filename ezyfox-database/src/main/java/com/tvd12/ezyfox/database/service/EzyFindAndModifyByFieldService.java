package com.tvd12.ezyfox.database.service;

import com.tvd12.ezyfox.database.query.EzyFindAndModifyOptions;
import com.tvd12.ezyfox.database.query.EzyUpdateOperations;
import com.tvd12.ezyfox.function.EzyApply;

public interface EzyFindAndModifyByFieldService<E> {
	
	E findAndModifyByField(String field, Object value, EzyApply<EzyUpdateOperations<E>> operations);
	
	E findAndModifyByField(String field, Object value, EzyApply<EzyUpdateOperations<E>> operations, EzyApply<EzyFindAndModifyOptions> options);
	
}
