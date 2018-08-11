package com.tvd12.ezyfox.bean.testing.combine.pack2;

import com.tvd12.ezyfox.bean.annotation.EzyConfiguration;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

@EzyConfiguration
public class ConfigurationPack2 {

	@EzySingleton
	public ISingleton10 sgt10 = new ISingleton10() {
	};
	
}
