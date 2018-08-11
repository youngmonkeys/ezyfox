package com.tvd12.ezyfox.constant;

import com.tvd12.ezyfox.constant.EzyAttribute;

public interface EzyHasAttribute {

	<T> T getAttribute(EzyAttribute<T> attr);
	
}
