package com.tvd12.ezyfox.database.service;

public interface EzyUpdateService<I,E> extends 
		EzyUpdateOneService<I, E>,
		EzyUpdateManyService<E> {
}
