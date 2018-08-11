package com.tvd12.ezyfox.database.service;

import com.tvd12.ezyfox.database.query.EzyUpdateOperations;
import com.tvd12.ezyfox.function.EzyApply;

public interface EzyUpdateOneByFieldService<E> {

	void updateOneByField(String field, Object value, E entity);
	
	void updateOneByField(String field, Object value, E entity, boolean upsert);
	
	void updateOneByField(String field, Object value, EzyApply<EzyUpdateOperations<E>> operations);
	
	void updateOneByField(String field, Object value, EzyApply<EzyUpdateOperations<E>> operations, boolean upsert);
	
}
