package com.tvd12.ezyfox.database.service;

public interface EzyUpdateOneService<I,E> extends
		EzyUpdateOneByIdService<I, E>,
		EzyUpdateOneByFieldService<E> {
}
