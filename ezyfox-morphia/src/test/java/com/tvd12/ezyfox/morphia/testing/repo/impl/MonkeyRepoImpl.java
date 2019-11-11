package com.tvd12.ezyfox.morphia.testing.repo.impl;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.morphia.repository.EzyDatastoreRepository;
import com.tvd12.ezyfox.morphia.testing.data.Monkey;
import com.tvd12.ezyfox.morphia.testing.repo.MonkeyRepo;

@EzySingleton
public class MonkeyRepoImpl
		extends EzyDatastoreRepository<Long, Monkey>
		implements MonkeyRepo {

	@Override
	public void save2Monkey(Monkey monkey1, Monkey monkey2) {
		datastore.save(Lists.newArrayList(monkey1, monkey2));
	}
	
}
