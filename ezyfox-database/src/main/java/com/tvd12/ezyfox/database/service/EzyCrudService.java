package com.tvd12.ezyfox.database.service;

public interface EzyCrudService<I,E> extends
		EzyCountSerivce,
		EzySaveService<E>, 
		EzyFindService<I, E>,
		EzyDeleteService<I> {

}
