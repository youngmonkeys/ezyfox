package com.tvd12.ezyfox.morphia.testing.repo;

import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.ezyfox.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.morphia.testing.data.Chickend;

@EzyAutoImpl
public interface ChickendRepo extends EzyMongoRepository<Long, Chickend> {
	
}
