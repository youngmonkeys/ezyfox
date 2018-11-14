/**
 * 
 */
package com.tvd12.ezyfox.hazelcast.mapstore;

import com.tvd12.ezyfox.morphia.EzyDatastoreAware;

import lombok.Setter;
import xyz.morphia.Datastore;

/**
 * @author tavandung12
 *
 */
public abstract class EzyMongoDatastoreMapstore<K,V> 
		extends EzyAbstractMapstore<K,V>
		implements EzyDatastoreAware {

	@Setter
    protected Datastore datastore;
    
}
