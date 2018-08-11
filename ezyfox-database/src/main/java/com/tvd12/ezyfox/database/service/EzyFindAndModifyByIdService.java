package com.tvd12.ezyfox.database.service;

import com.tvd12.ezyfox.database.query.EzyFindAndModifyOptions;
import com.tvd12.ezyfox.database.query.EzyUpdateOperations;
import com.tvd12.ezyfox.function.EzyApply;

public interface EzyFindAndModifyByIdService<I,E> {
	
	E findAndModifyById(I id, EzyApply<EzyUpdateOperations<E>> operations);
	
	E findAndModifyById(I id, EzyApply<EzyUpdateOperations<E>> operations, EzyApply<EzyFindAndModifyOptions> options);
	
}
