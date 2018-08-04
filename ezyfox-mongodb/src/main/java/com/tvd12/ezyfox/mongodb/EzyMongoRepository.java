package com.tvd12.ezyfox.mongodb;

import com.tvd12.ezyfox.database.repository.EzyEmptyRepository;
import com.tvd12.ezyfox.database.service.EzyCrudService;

public interface EzyMongoRepository<I,E> 
		extends EzyEmptyRepository<I, E>, EzyCrudService<I, E> {

}
