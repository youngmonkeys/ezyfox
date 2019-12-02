package com.tvd12.ezyfox.mongodb.testing.bean;

import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.ezyfox.mongodb.EzyMongoRepository;

@EzyAutoImpl
interface PersonRepo3 extends EzyMongoRepository<Integer, Person> {
}
