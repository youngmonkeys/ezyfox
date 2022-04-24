package com.tvd12.ezyfox.database.service;

import com.tvd12.ezyfox.database.query.EzyUpdateOperations;
import com.tvd12.ezyfox.function.EzyApply;

public interface EzyUpdateOneByIdService<I, E> {

    void updateOneById(I id, E entity);

    void updateOneById(I id, E entity, boolean upsert);

    void updateOneById(I id, EzyApply<EzyUpdateOperations<E>> operations);

    void updateOneById(I id, EzyApply<EzyUpdateOperations<E>> operations, boolean upsert);
}
