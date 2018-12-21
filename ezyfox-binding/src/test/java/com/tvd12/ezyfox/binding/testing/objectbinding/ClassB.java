package com.tvd12.ezyfox.binding.testing.objectbinding;

import java.util.List;

import com.tvd12.ezyfox.binding.EzyAccessType;
import com.tvd12.ezyfox.binding.annotation.EzyIgnore;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.collect.Lists;

@EzyObjectBinding(accessType = EzyAccessType.ALL)
public class ClassB {

	private List<?> a = Lists.newArrayList(1, 2, 3);

	public List<?> getA() {
		return a;
	}

	public void setA(List<?> a) {
		this.a = a;
	}
	
	public List<?> getB() {
		return Lists.newArrayList(1, 2, 3);
	}
	
	public void setB(List<?> b) {
		
	}
	
	@EzyIgnore
	public void setC(String c) {
		
	}
	
	@EzyIgnore
	public String getC() {
		return "c";
	}
	
	protected void setD(String d) {
	}
	
	protected String getD() {
		return "d";
	}
	
}
