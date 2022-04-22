/**
 * 
 */
package com.tvd12.ezyfox.database.service;

/**
 * @author tavandung12
 *
 */
public interface EzyMaxIdService {

    void loadAll();

    Long incrementAndGet(String key);
    
    Long incrementAndGet(String key, int delta);
    
}
