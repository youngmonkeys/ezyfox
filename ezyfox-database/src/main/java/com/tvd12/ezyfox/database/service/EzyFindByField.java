package com.tvd12.ezyfox.database.service;

public interface EzyFindByField<E> {

	E findByField(String field, Object value);
	
}
