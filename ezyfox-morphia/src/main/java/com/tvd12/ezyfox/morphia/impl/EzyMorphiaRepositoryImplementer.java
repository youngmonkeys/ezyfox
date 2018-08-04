package com.tvd12.ezyfox.morphia.impl;

import org.mongodb.morphia.Datastore;

import com.tvd12.ezyfox.mongodb.bean.EzySimpleRepositoryImplementer;
import com.tvd12.ezyfox.morphia.EzyDatastoreAware;
import com.tvd12.ezyfox.morphia.repository.EzyDatastoreRepository;

public class EzyMorphiaRepositoryImplementer extends EzySimpleRepositoryImplementer {

	public EzyMorphiaRepositoryImplementer(Class<?> clazz) {
		super(clazz);
	}

	@Override
	protected void setRepoComponent(Object repo, Object template) {
		Datastore datastore = (Datastore)template;
		EzyDatastoreAware datastoreAware = (EzyDatastoreAware)repo; 
		datastoreAware.setDatastore(datastore);
	}
	
	@Override
	protected Class<?> getSuperClass() {
		return EzyDatastoreRepository.class;
	}
	
}
