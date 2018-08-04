package com.tvd12.ezyfox.morphia.testing.repo;

import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.ezyfox.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.morphia.testing.data.Cat;

@EzyAutoImpl
public interface CatRepo extends EzyMongoRepository<Long, Cat> {
	
}
