package com.tvd12.ezyfox.bean.testing.singleton;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;

import lombok.Getter;

@Getter
@EzySingleton("b")
public class ClassB {

	private String value2 = "2";
	
}
