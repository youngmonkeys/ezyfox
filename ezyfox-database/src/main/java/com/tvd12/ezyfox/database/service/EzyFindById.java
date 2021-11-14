package com.tvd12.ezyfox.database.service;

import java.util.Optional;

public interface EzyFindById<I,E> {

	E findById(I id);
	
	default Optional<E> findByIdOptional(I id) {
	    return Optional.ofNullable(findById(id));
	}
	
}
