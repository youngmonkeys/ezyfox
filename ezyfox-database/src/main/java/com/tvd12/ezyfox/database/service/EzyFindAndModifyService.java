package com.tvd12.ezyfox.database.service;

public interface EzyFindAndModifyService<I,E> extends 
		EzyFindAndModifyByIdService<I, E>,
		EzyFindAndModifyByFieldService<E> {
}
