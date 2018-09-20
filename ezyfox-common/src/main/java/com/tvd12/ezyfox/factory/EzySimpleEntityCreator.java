package com.tvd12.ezyfox.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
import com.tvd12.ezyfox.io.EzySimpleCollectionConverter;
import com.tvd12.ezyfox.io.EzySimpleInputTransformer;
import com.tvd12.ezyfox.io.EzySimpleOutputTransformer;

public class EzySimpleEntityCreator implements EzyEntityCreator {

	private static final EzyInputTransformer INPUT_TRANSFORMER 
			= new EzySimpleInputTransformer();
	private static final EzyOutputTransformer OUTPUT_TRANSFORMER 
			= new EzySimpleOutputTransformer();
	private static final EzyCollectionConverter COLLECTION_CONVERTER 
			= new EzySimpleCollectionConverter();
	
	@SuppressWarnings("rawtypes")
	private final Map<Class, Supplier> suppliers = defaultSuppliers();
	
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
		return INPUT_TRANSFORMER;
	}
	
	protected EzyOutputTransformer getOutputTransformer() {
		return OUTPUT_TRANSFORMER;
	}
	
	protected EzyCollectionConverter getCollectionConverter() {
		return COLLECTION_CONVERTER;
	}
	
	@SuppressWarnings("rawtypes")
	private final Map<Class, Supplier> defaultSuppliers() {
		Map<Class, Supplier> answer = new ConcurrentHashMap<>();
		answer.put(EzyObject.class, this::newObject);
		answer.put(EzyArray.class, this::newArray);
		answer.put(EzyObjectBuilder.class, this::newObjectBuilder);
		answer.put(EzyArrayBuilder.class, this::newArrayBuilder);
		return answer;
	}

}
