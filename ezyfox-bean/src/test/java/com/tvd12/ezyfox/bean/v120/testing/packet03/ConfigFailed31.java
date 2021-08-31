package com.tvd12.ezyfox.bean.v120.testing.packet03;

import com.tvd12.ezyfox.bean.EzyBeanConfig;

public class ConfigFailed31 implements EzyBeanConfig {

	@Override
	public void config() {
		throw new RuntimeException("just test");
	}
}
