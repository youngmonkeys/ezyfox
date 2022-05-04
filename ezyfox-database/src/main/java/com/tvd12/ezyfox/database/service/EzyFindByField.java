package com.tvd12.ezyfox.database.service;

import java.util.Optional;

public interface EzyFindByField<E> {

    E findByField(String field, Object value);

    default Optional<E> findByFieldOptional(String field, Object value) {
        return Optional.ofNullable(findByField(field, value));
    }

    default boolean containsByField(String field, Object value) {
        return findByField(field, value) != null;
    }
}
