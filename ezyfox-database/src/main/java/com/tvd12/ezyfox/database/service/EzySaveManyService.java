package com.tvd12.ezyfox.database.service;

import com.tvd12.ezyfox.collect.Lists;

public interface EzySaveManyService<E> {

    void save(Iterable<E> entities);

    @SuppressWarnings("unchecked")
    default void save(E... entities) {
        save(Lists.newArrayList(entities));
    }
}
