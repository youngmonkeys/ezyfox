package com.tvd12.ezyfox.util;

import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.factory.EzyLiteEntityFactory;
import com.tvd12.ezyfox.util.EzyLoggable;

public class EzyLiteEntityBuilders extends EzyLoggable {
	
	protected EzyArrayBuilder newArrayBuilder() {
		return EzyLiteEntityFactory.create(EzyArrayBuilder.class);
	}

	protected EzyObjectBuilder newObjectBuilder() {
		return EzyLiteEntityFactory.create(EzyObjectBuilder.class);
	}
	
}
