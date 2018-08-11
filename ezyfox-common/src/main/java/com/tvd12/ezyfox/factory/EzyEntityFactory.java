package com.tvd12.ezyfox.factory;

import com.tvd12.ezyfox.factory.EzyEntityBuilderCreator;
import com.tvd12.ezyfox.factory.EzySimpleEntityBuilderCreator;

public final class EzyEntityFactory {

	private static final EzyEntityBuilderCreator CREATOR 
			= new EzySimpleEntityBuilderCreator();
	
	private EzyEntityFactory() {
		// do nothing
	}
	
	public static <T> T create(Class<T> productType) {
		return CREATOR.create(productType);
	}
	
}
