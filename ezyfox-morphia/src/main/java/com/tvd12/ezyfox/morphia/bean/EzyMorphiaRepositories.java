package com.tvd12.ezyfox.morphia.bean;

import com.tvd12.ezyfox.morphia.impl.EzyMorphiaRepositoriesImplementer;

public final class EzyMorphiaRepositories {

	private EzyMorphiaRepositories() {
	}
	
	public static EzyMorphiaRepositoriesImplementer newRepositoriesImplementer() {
		return new EzyMorphiaRepositoriesImplementer();
	}
	
}
