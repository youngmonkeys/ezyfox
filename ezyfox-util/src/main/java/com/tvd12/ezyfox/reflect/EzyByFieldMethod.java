package com.tvd12.ezyfox.reflect;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

public abstract class EzyByFieldMethod 
		extends EzyMethod 
		implements EzyGenericElement, EzyKnownTypeElement {

	public EzyByFieldMethod(Method method) {
		super(method);
	}
	
	public String getFieldName() {
		return EzyMethods.getFieldName(method, 3);
	}
	
	public boolean isMapType() {
		return Map.class.isAssignableFrom(getType());
	}
	
	public boolean isCollection() {
		return Collection.class.isAssignableFrom(getType());
	}

}
