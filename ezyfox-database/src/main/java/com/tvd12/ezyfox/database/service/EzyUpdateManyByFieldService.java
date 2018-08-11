package com.tvd12.ezyfox.database.service;

import com.tvd12.ezyfox.database.query.EzyUpdateOperations;
import com.tvd12.ezyfox.function.EzyApply;

public interface EzyUpdateManyByFieldService<E> {

	void updateManyByField(String field, Object value, EzyApply<EzyUpdateOperations<E>> operations);
	
}
