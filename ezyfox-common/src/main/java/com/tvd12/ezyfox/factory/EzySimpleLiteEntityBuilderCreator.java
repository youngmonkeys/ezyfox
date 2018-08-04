package com.tvd12.ezyfox.factory;

import com.tvd12.ezyfox.factory.EzySimpleEntityBuilderCreator;
import com.tvd12.ezyfox.io.EzyCollectionConverter;
import com.tvd12.ezyfox.io.EzyLiteCollectionConverter;
import com.tvd12.ezyfox.io.EzyLiteOutputTransformer;
import com.tvd12.ezyfox.io.EzyOutputTransformer;

public class EzySimpleLiteEntityBuilderCreator extends EzySimpleEntityBuilderCreator {

	private static final EzyOutputTransformer OUTPUT_TRANSFORMER 
			= new EzyLiteOutputTransformer();
	private static final EzyCollectionConverter COLLECTION_CONVERTER 
			= new EzyLiteCollectionConverter(OUTPUT_TRANSFORMER);

	@Override
	protected EzyOutputTransformer getOutputTransformer() {
		return OUTPUT_TRANSFORMER;
	}

	@Override
	protected EzyCollectionConverter getCollectionConverter() {
		return COLLECTION_CONVERTER;
	}

}
