package com.tvd12.ezyfox.morphia;

import dev.morphia.Datastore;

public interface EzyDatastoreAware {

	void setDatastore(Datastore datastore);
	
}
