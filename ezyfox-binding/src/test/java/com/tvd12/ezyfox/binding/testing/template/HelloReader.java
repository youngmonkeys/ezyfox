package com.tvd12.ezyfox.binding.testing.template;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.annotation.EzyReaderImpl;

@EzyReaderImpl
public class HelloReader implements EzyReader<String, Hello> {

	@Override
	public Hello read(EzyUnmarshaller unmarshaller, String value) {
		return new Hello(value);
	}

}
