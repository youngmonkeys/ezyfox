package com.tvd12.ezyfox.io;

import java.util.Collection;

import com.tvd12.ezyfox.entity.EzyArray;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@SuppressWarnings({"rawtypes"})
public class EzyLiteCollectionConverter extends EzySimpleCollectionConverter {

	private final EzyOutputTransformer outputTransformer;
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T toArray(Object array, Class type) {
		if(array instanceof EzyArray)
			return toArray(((EzyArray)array).toList(), type.getComponentType());
		if(array instanceof Collection)
			return toArray((Collection)array, type.getComponentType());
		return (T) outputTransformer.transform(array, type);
	}
	
}
