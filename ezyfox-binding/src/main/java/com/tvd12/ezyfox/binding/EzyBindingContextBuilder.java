package com.tvd12.ezyfox.binding;

import com.tvd12.ezyfox.binding.impl.EzySimpleBindingContext;

import java.util.Collection;

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

    EzyBindingContextBuilder addTemplate(Class type, Object template);

    EzyBindingContextBuilder addTemplateClass(Class clazz);

    EzyBindingContextBuilder addTemplateClasses(Iterable<Class<?>> classes);

    EzySimpleBindingContext build();
}
