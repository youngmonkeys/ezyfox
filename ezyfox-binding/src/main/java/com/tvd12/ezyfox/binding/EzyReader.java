package com.tvd12.ezyfox.binding;

import com.tvd12.ezyfox.binding.annotation.EzyTemplateImpl;
import com.tvd12.ezyfox.reflect.EzyGenerics;

public interface EzyReader<T,R> {
	
	R read(EzyUnmarshaller unmarshaller, T value);
	
	default Class<?> getObjectType() {
		try {
			Class<?> readerClass = getClass();
			if(readerClass.equals(EzyTemplateImpl.class)) {
				Class<?>[] args = EzyGenerics.getGenericInterfacesArguments(readerClass, EzyTemplate.class, 2);
				return args[1];
			}
			Class<?>[] args = EzyGenerics.getGenericInterfacesArguments(readerClass, EzyReader.class, 2);
			return args[1];
		}
		catch(Exception e) {
			return null;
		}
	}
	
}
