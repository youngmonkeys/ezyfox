package com.tvd12.ezyfox.testing;

import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.factory.EzyLiteEntityFactory;
import com.tvd12.test.base.BaseTest;

public class CommonBaseTest extends BaseTest {

	protected EzyObjectBuilder newObjectBuilder() {
		return EzyEntityFactory.create(EzyObjectBuilder.class);
	}
	
	protected EzyArrayBuilder newArrayBuilder() {
		return EzyEntityFactory.create(EzyArrayBuilder.class);
	}
	
	protected EzyArrayBuilder newLiteArrayBuilder() {
		return EzyLiteEntityFactory.create(EzyArrayBuilder.class);
	}
	
}
