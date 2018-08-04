package com.tvd12.ezyfox.morphia;

import org.mongodb.morphia.Datastore;

public interface EzyDatastoreAware {

	void setDatastore(Datastore datastore);
	
}
