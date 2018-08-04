package com.tvd12.ezyfox.factory;

import com.tvd12.ezyfox.factory.EzyEntityBuilderCreator;
import com.tvd12.ezyfox.factory.EzySimpleLiteEntityBuilderCreator;

public final class EzyLiteEntityFactory {

	private static final EzyEntityBuilderCreator CREATOR 
			= new EzySimpleLiteEntityBuilderCreator();
	
	private EzyLiteEntityFactory() {
	}
	
	public static <T> T create(Class<T> productType) {
		return CREATOR.create(productType);
	}
	
}