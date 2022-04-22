package com.tvd12.ezyfox.database.service;

public interface EzyDeleteService<I> extends
        EzyDeleteAllService,
        EzyDeleteByIdService<I>,
        EzyDeleteByIdsService<I> {
}