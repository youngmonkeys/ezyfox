package com.tvd12.ezyfox.bean.testing.combine.pack0;

import com.tvd12.ezyfox.bean.EzyBeanConfig;
import com.tvd12.ezyfox.bean.annotation.EzyConfiguration;
import com.tvd12.ezyfox.bean.annotation.EzyPackagesScan;
import com.tvd12.ezyfox.util.EzyPropertiesAware;

import java.util.Properties;

@EzyConfiguration
@EzyPackagesScan({"com.tvd12.ezyfox.bean.testing.combine.pack3"})
public class Configuration1 implements EzyBeanConfig, EzyPropertiesAware {

	@Override
	public void setProperties(Properties properties) {
	}
	
	@Override
	public void config() {
		
	}
	
}
