package com.tvd12.ezyfox.bean.testing.combine;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@EzySingleton
@AllArgsConstructor
public class SingletonX4 {

	private final SingletonX1 singletonX1;
	private final SingletonX2 singletonX2;
	private final SingletonX3 singletonX3;
	
	@EzyAutoBind
	private final Singleton12 singleton12 = new Singleton12();
	
}
