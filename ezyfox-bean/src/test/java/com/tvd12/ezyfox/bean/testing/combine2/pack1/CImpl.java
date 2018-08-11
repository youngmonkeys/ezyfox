package com.tvd12.ezyfox.bean.testing.combine2.pack1;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPostInit;

import lombok.Setter;

//@EzyCombine0Ann
public class CImpl implements C {

	@Setter
	@EzyAutoBind
	private F f;
	
	@EzyPostInit
	public void done() {
		System.out.println("\n\ni'm C and i'm done, my f = " + f + "\n\n");
	}
	
}
