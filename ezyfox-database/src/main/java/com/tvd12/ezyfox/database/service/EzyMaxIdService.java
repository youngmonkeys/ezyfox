package com.tvd12.ezyfox.database.service;

public interface EzyMaxIdService {

    void loadAll();

    Long incrementAndGet(String key);

    Long incrementAndGet(String key, int delta);
}
