package com.tvd12.ezyfox.binding.impl;

import java.lang.reflect.Type;
import java.util.List;

import com.tvd12.ezyfox.binding.annotation.EzyValue;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.reflect.EzyAnnotatedElement;
import com.tvd12.ezyfox.reflect.EzyByFieldMethod;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzyGenericElement;
import com.tvd12.ezyfox.reflect.EzyKnownTypeElement;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.ezyfox.reflect.EzyReflectElement;
import com.tvd12.ezyfox.util.EzyLoggable;

public abstract class EzyAbstractBuilder<M extends EzyMethod> extends EzyLoggable  {

	protected final EzyClass clazz;
	protected final EzyElementsFetcher elementsFetcher;
	protected final int accessType;
	
	public EzyAbstractBuilder(EzyClass clazz) {
		this.clazz = clazz;
		this.elementsFetcher = newElementsFetcher();
		this.accessType = getAccessType(clazz);
	}
	
	protected abstract int getAccessType(EzyClass clazz);
	protected abstract EzyElementsFetcher newElementsFetcher();
	
	protected List<Object> getElements() {
		return elementsFetcher.getElements(clazz, accessType);
	}
	
	protected String getFieldName(EzyReflectElement element) {
		if(element instanceof EzyField)
			return ((EzyField)element).getName();
		return ((EzyByFieldMethod)element).getFieldName();
	}
	
	protected String getFieldKey(EzyField field) {
		if(field.isAnnotated(EzyValue.class))
			return getAnnotatedElementKey(field, field.getName());
		return field.getName();
	}
	
	protected String getMethodKey(M method) {
		if(method.isAnnotated(EzyValue.class))
			return getAnnotatedElementKey(method, getFieldName(method));
		String fieldName = getFieldName(method);
		EzyField field = clazz.getField(fieldName);
		return field != null ? getFieldKey(field) : fieldName;
	}
	
	@SuppressWarnings("unchecked")
	protected String getKey(EzyReflectElement element) {
		if(element instanceof EzyField)
			return getFieldKey((EzyField) element);
		return getMethodKey((M) element); 
	}
	
	protected String getAnnotatedElementKey(
			EzyAnnotatedElement element, String elementName) {
		EzyValue kv = element.getAnnotation(EzyValue.class);
		String value = kv.value();
		return EzyStrings.isNoContent(value) ? elementName : value;
	}
	
	@SuppressWarnings("rawtypes")
	protected Class getElementType(Object element) {
		if(element == null)
			return Object.class;
		return ((EzyKnownTypeElement)element).getType();
	}
	
	protected Type getElementGenericType(Object element) {
		return ((EzyGenericElement)element).getGenericType();
	}
	
}
