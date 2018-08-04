/**
 * 
 */
package com.tvd12.ezyfox.hazelcast.mapstore;

import com.mongodb.MongoClient;
import com.tvd12.ezyfox.hazelcast.mapstore.EzyAbstractMapstore;
import com.tvd12.ezyfox.mongodb.EzyMongoClientAware;

import lombok.Setter;

/**
 * @author tavandung12
 *
 */
public abstract class EzyMongoMapstore<K,V>
		extends EzyAbstractMapstore<K,V> 
		implements EzyMongoClientAware {

	@Setter
    protected MongoClient mongoClient;
    
}
