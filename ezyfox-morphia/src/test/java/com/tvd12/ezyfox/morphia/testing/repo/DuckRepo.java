package com.tvd12.ezyfox.morphia.testing.repo;

import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.ezyfox.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.morphia.testing.Duck;

@EzyAutoImpl
public interface DuckRepo extends EzyMongoRepository<Long, Duck> {
	
}
