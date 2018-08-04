package com.tvd12.ezyfox.database.service;

public interface EzyCrudService<I,E> extends
		EzyCountSerivce,
		EzySaveService<E>, 
		EzyFindService<I, E>,
		EzyUpdateService<I, E>, 
		EzyDeleteService<I>,
		EzyFindAndModifyService<I,E> {

}
