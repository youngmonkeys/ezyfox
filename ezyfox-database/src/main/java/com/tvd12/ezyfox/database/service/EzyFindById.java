package com.tvd12.ezyfox.database.service;

public interface EzyFindById<I,E> {

	E findById(I id);
	
}
