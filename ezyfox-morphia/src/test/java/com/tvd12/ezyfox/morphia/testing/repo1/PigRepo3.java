package com.tvd12.ezyfox.morphia.testing.repo1;

import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.ezyfox.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.morphia.testing.Pig;

@EzyAutoImpl
abstract class PigRepo3 implements EzyMongoRepository<Long, Pig> {
	
}
