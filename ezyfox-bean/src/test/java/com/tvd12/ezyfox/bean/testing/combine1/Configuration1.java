package com.tvd12.ezyfox.bean.testing.combine1;

import com.tvd12.ezyfox.bean.annotation.EzyConfiguration;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;

@EzyConfiguration
public class Configuration1 extends EzyLoggable {

	@EzySingleton
	public Singleton0 newSingleton0() {
		logger.debug("\nnew singleton 0 call\n");
		return new Singleton0();
	}
	
	@EzySingleton
	public Singleton1 newSingleton1() {
		logger.debug("\nnew singleton 1 call\n");
		return new Singleton1();
	}
	
	@EzySingleton
	public Singleton2 newSingleton2() {
		logger.debug("\nnew singleton 2 call\n");
		return new Singleton2();
	}
	
}
