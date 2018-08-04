package com.tvd12.ezyfox.hazelcast.testing;

import org.testng.annotations.Test;

import com.hazelcast.config.Config;
import com.tvd12.ezyfox.hazelcast.EzySimpleConfigCustomizer;
import com.tvd12.test.base.BaseTest;

public class EzySimpleConfigCustomizerTest extends BaseTest {

	@Test
	public void test() {
		new EzySimpleConfigCustomizer().customize(new Config());
	}
	
}
