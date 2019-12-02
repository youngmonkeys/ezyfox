package com.tvd12.ezyfox.mongodb.testing.bean;

import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.ezyfox.mongodb.EzyMongoRepository;

@EzyAutoImpl
public interface PersonRepo extends EzyMongoRepository<Integer, Person> {
}
