package com.tvd12.ezyfox.bean.testing.combine.pack0;

import java.util.Properties;

import com.tvd12.ezyfox.annotation.EzyPackagesToScan;
import com.tvd12.ezyfox.bean.EzyBeanConfig;
import com.tvd12.ezyfox.bean.annotation.EzyBeanPackagesToScan;
import com.tvd12.ezyfox.bean.annotation.EzyConfiguration;
import com.tvd12.ezyfox.bean.annotation.EzyPropertiesBean;
import com.tvd12.ezyfox.util.EzyPropertiesAware;

@EzyConfiguration
@EzyPackagesToScan({"com.tvd12.ezyfox.bean.testing.combine.pack3"})
@EzyBeanPackagesToScan({"com.tvd12.ezyfox.bean.testing.combine.pack3"})
@EzyPropertiesBean(prefix = "pack1", value = PropertiesPack1.class)
public class Configuration1 implements EzyBeanConfig, EzyPropertiesAware {

	@Override
	public void setProperties(Properties properties) {
	}
	
	@Override
	public void config() {
		
	}
	
}
