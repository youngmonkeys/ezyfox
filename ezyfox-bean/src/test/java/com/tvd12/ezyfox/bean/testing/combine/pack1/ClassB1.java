package com.tvd12.ezyfox.bean.testing.combine.pack1;

import com.tvd12.ezyfox.annotation.EzyProperty;
import com.tvd12.ezyfox.bean.annotation.EzyPostInit;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.bean.testing.combine.EzyCombine0Ann;

import lombok.Getter;

@Getter
@EzyCombine0Ann
@EzyPrototype("b1")
public class ClassB1 {

	@EzyProperty("hello")
	private String hello;
	
	private String word;
	
	@EzyProperty
	private String foo;
	
	@EzyPostInit
	public void post() {
		System.out.println("\n\nI have hello = " + hello + ", and foo = " + foo + "\n\n");
	}
	
}
