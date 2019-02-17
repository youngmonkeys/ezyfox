package com.tvd12.ezyfox.binding;

import com.tvd12.ezyfox.reflect.EzyGenerics;

public interface EzyTemplate<A,B> extends EzyReader<A,B>, EzyWriter<B,A> {
	
	@Override
	default Class<?> getObjectType() {
		try {
			Class<?> writerClass = getClass();
			Class<?>[] args = EzyGenerics.getGenericInterfacesArguments(writerClass, EzyTemplate.class, 2);
			return args[1];
		}
		catch(Exception e) {
			return null;
		}
	}
	
}
