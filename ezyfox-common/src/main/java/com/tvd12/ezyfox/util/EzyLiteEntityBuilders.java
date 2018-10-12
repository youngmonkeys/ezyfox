package com.tvd12.ezyfox.util;

import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyLiteEntityFactory;

public class EzyLiteEntityBuilders extends EzyLoggable {
	
	protected EzyArray newArray() {
		return EzyLiteEntityFactory.newArray();
	}

	protected EzyObject newObject() {
		return EzyLiteEntityFactory.newObject();
	}
	
	protected EzyArrayBuilder newArrayBuilder() {
		return EzyLiteEntityFactory.newArrayBuilder();
	}

	protected EzyObjectBuilder newObjectBuilder() {
		return EzyLiteEntityFactory.newObjectBuilder();
	}
	
}
