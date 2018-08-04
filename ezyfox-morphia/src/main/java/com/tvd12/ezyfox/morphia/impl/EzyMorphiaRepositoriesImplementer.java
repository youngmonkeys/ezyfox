package com.tvd12.ezyfox.morphia.impl;

import com.tvd12.ezyfox.mongodb.bean.EzySimpleRepositoriesImplementer;
import com.tvd12.ezyfox.mongodb.bean.EzySimpleRepositoryImplementer;

public class EzyMorphiaRepositoriesImplementer
		extends EzySimpleRepositoriesImplementer {
	
	@Override
	protected EzySimpleRepositoryImplementer newRepoImplementer(Class<?> itf) {
		return new EzyMorphiaRepositoryImplementer(itf);
	}
	
}
