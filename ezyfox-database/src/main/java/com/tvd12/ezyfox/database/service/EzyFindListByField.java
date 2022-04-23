package com.tvd12.ezyfox.database.service;

import java.util.List;

public interface EzyFindListByField<E> {

    List<E> findListByField(String field, Object value);

    List<E> findListByField(String field, Object value, int skip, int limit);
}
