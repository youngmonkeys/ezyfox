package com.tvd12.ezyfox.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.builder.EzySimpleArrayBuilder;
import com.tvd12.ezyfox.builder.EzySimpleObjectBuilder;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyArrayList;
import com.tvd12.ezyfox.entity.EzyHashMap;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.io.EzyCollectionConverter;
import com.tvd12.ezyfox.io.EzyInputTransformer;
import com.tvd12.ezyfox.io.EzyOutputTransformer;
import com.tvd12.ezyfox.io.EzySingletonCollectionConverter;
import com.tvd12.ezyfox.io.EzySingletonInputTransformer;
import com.tvd12.ezyfox.io.EzySingletonOutputTransformer;

public final class EzySimpleEntityCreator implements EzyEntityCreator {

	@SuppressWarnings("rawtypes")
	private final Map<Class, Supplier> suppliers;
	private static final EzySimpleEntityCreator INSTANCE = new EzySimpleEntityCreator();
	
	private EzySimpleEntityCreator() {
		this.suppliers = defaultSuppliers();
	}
	
	public static EzySimpleEntityCreator getInstance() {
		return INSTANCE;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T create(Class<T> productType) {
		Supplier<T> supplier = suppliers.get(productType);
		T answer = supplier.get();
		return answer;
	}
	
	public EzyObject newObject() {
		return new EzyHashMap(
				getInputTransformer(), 
				getOutputTransformer());
	}
	
	public EzyArray newArray() {
		return new EzyArrayList(
				getInputTransformer(),
				getOutputTransformer(),
				getCollectionConverter());
	}
	
	public EzyObjectBuilder newObjectBuilder() {
		return new EzySimpleObjectBuilder(
				getInputTransformer(),
				getOutputTransformer());
	}
	
	public EzyArrayBuilder newArrayBuilder() {
		return new EzySimpleArrayBuilder(
				getInputTransformer(),
				getOutputTransformer(),
				getCollectionConverter());
	}
	
	protected EzyInputTransformer getInputTransformer() {
		return EzySingletonInputTransformer.getInstance();
	}
	
	protected EzyOutputTransformer getOutputTransformer() {
		return EzySingletonOutputTransformer.getInstance();
	}
	
	protected EzyCollectionConverter getCollectionConverter() {
		return EzySingletonCollectionConverter.getInstance();
	}
	
	@SuppressWarnings("rawtypes")
	private final Map<Class, Supplier> defaultSuppliers() {
		Map<Class, Supplier> answer = new HashMap<>();
		answer.put(EzyObject.class, () -> newObject());
		answer.put(EzyArray.class, () -> newArray());
		answer.put(EzyObjectBuilder.class, () -> newObjectBuilder());
		answer.put(EzyArrayBuilder.class, () -> newArrayBuilder());
		return answer;
	}

}
