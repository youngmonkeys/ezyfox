/**
 * 
 */
package com.tvd12.ezyfox.hazelcast.service;

/**
 * @author tavandung12
 *
 */
public interface EzyMaxIdService {

	void loadAll();
	
    Long incrementAndGet(String key);
    
}
