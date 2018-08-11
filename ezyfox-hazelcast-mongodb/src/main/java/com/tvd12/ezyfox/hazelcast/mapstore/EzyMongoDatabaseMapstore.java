/**
 * 
 */
package com.tvd12.ezyfox.hazelcast.mapstore;

import com.mongodb.client.MongoDatabase;
import com.tvd12.ezyfox.hazelcast.mapstore.EzyAbstractMapstore;
import com.tvd12.ezyfox.mongodb.EzyMongoDatabaseAware;

import lombok.Setter;

/**
 * @author tavandung12
 *
 */
public abstract class EzyMongoDatabaseMapstore<K,V>
		extends EzyAbstractMapstore<K, V> 
		implements EzyMongoDatabaseAware {

	@Setter
    protected MongoDatabase database;
    
}
