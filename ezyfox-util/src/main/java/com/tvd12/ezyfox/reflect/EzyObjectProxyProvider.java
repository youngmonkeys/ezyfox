package com.tvd12.ezyfox.reflect;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class EzyObjectProxyProvider {

	protected final Map<Class, EzyObjectProxy> objectProxies;
	
	public EzyObjectProxyProvider() {
		this.objectProxies = new ConcurrentHashMap<>();
	}
	
	public EzyObjectProxy getObjectProxy(Class<?> objectType) {
		return objectProxies.computeIfAbsent(
				objectType, k -> newObjectProxy(k));
	}
	
	protected EzyObjectProxy newObjectProxy(Class<?> objectType) {
		EzyClass clazz = new EzyClass(objectType);
		Collection<EzyField> fields = getFields(clazz);
		Map<String, Function> getters = new HashMap<>();
		Map<String, BiConsumer> setters = new HashMap<>();
		Map<String, Class<?>> propertyTypes = new HashMap<>();
		for(EzyField field : fields) {
			getters.put(field.getName(), newGetter(field));
			setters.put(field.getName(), newSetter(field));
			propertyTypes.put(field.getName(), field.getType());
		}
		Map<String, String> fieldKeys = getFieldKeys(fields);
		EzyObjectProxy.Builder builder = newObjectProxyBuilder(clazz)
				.propertyKey(fieldKeys)
				.addSetters((Map)setters)
				.addGetters((Map)getters)
				.addPropertyTypes(propertyTypes);
		preBuildObjectProxy(clazz, builder);
		return builder.build();
	}
	
	protected void preBuildObjectProxy(
			EzyClass clazz, EzyObjectProxy.Builder builder) {
	}
	
	protected Collection<EzyField> getFields(EzyClass clazz) {
		return clazz.getFields(f -> isSettableField(f));
	}
	
	protected boolean isSettableField(EzyField field) {
		return  field.isWritable() && 
				!Modifier.isStatic(field.getField().getModifiers());
	}
	
	protected Function newGetter(EzyField field) {
		return new EzyGetterBuilder()
				.field(field)
				.build();
	}
	
	protected BiConsumer newSetter(EzyField field) {
		return new EzySetterBuilder()
				.field(field)
				.build();
	}
	
	protected EzyObjectProxy.Builder newObjectProxyBuilder(EzyClass clazz) {
		return EzyObjectProxy.builder();
	}
	
	protected Map<String, String> getFieldKeys(Collection<EzyField> fields) {
		return Collections.emptyMap();
	}
	
}
