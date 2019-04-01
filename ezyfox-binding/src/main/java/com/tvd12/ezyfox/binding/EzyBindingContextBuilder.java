package com.tvd12.ezyfox.binding;

import java.util.Collection;

import com.tvd12.ezyfox.binding.impl.EzySimpleBindingContext;

@SuppressWarnings("rawtypes")
public interface EzyBindingContextBuilder {

	EzyBindingContextBuilder scan(String packageName);

	EzyBindingContextBuilder scan(String... packageNames);

	EzyBindingContextBuilder scan(Iterable<String> packageNames);
	
	EzyBindingContextBuilder scan(Collection<String> packageNames);

	EzyBindingContextBuilder addClass(Class clazz);

	EzyBindingContextBuilder addObjectBindingClass(Class clazz);

	EzyBindingContextBuilder addArrayBindingClass(Class clazz);

	EzyBindingContextBuilder addClasses(Class... classes);

	EzyBindingContextBuilder addClasses(Iterable<Class> classes);
	
	EzyBindingContextBuilder addAllClasses(Object reflection);

	EzyBindingContextBuilder addTemplate(Object template);

	EzyBindingContextBuilder addTemplateClass(Class clazz);

	EzyBindingContextBuilder addTemplateClasses(Iterable<Class<?>> classes);

	EzyBindingContextBuilder addTemplate(Class type, Object template);

	EzySimpleBindingContext build();

}