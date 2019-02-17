package com.tvd12.ezyfox.binding;

import com.tvd12.ezyfox.binding.annotation.EzyTemplateImpl;
import com.tvd12.ezyfox.reflect.EzyGenerics;

public interface EzyWriter<T,R> {
	
	R write(EzyMarshaller marshaller, T object);
	
	default Class<?> getObjectType() {
		try {
			Class<?> writerClass = getClass();
			if(writerClass.isAnnotationPresent(EzyTemplateImpl.class)) {
				Class<?>[] args = EzyGenerics.getGenericInterfacesArguments(writerClass, EzyTemplate.class, 2);
				return args[1];
			}
			Class<?>[] args = EzyGenerics.getGenericInterfacesArguments(writerClass, EzyWriter.class, 2);
			return args[0];
		}
		catch(Exception e) {
			return null;
		}
	}
}
