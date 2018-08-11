package com.tvd12.ezyfox.util;

import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.util.EzyLoggable;

public class EzyEntityBuilders extends EzyLoggable {
	
	protected EzyArrayBuilder newArrayBuilder() {
		return EzyEntityFactory.create(EzyArrayBuilder.class);
	}

	protected EzyObjectBuilder newObjectBuilder() {
		return EzyEntityFactory.create(EzyObjectBuilder.class);
	}
	
}
