package com.tvd12.ezyfox.binding.reader;

import java.util.Collection;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.entity.EzyArray;

@SuppressWarnings("rawtypes")
public abstract class EzyWrapperArrayReader implements EzyReader<Object, Object> {

	@Override
	public Object read(EzyUnmarshaller unmarshaller, Object value) {
		if(value instanceof EzyArray)
			return readArray(unmarshaller, (EzyArray)value);
		if(value instanceof Collection)
			return readCollection(unmarshaller, (Collection) value);
		return value;
	}
	
	protected Object readArray(EzyUnmarshaller unmarshaller, EzyArray array) {
		int length = array.size();
		Class<?> outType = getOutType();
		Object[] answer = newArray(length);
		for(int i = 0 ; i < length ; ++i)
			answer[i] = unmarshaller.unmarshal((Object)array.get(i), outType);
		return answer;
	}
	
	protected Object readCollection(EzyUnmarshaller unmarshaller, Collection collection) {
		int length = collection.size();
		Class<?> outType = getOutType();
		Object[] answer = newArray(length);
		int index = 0;
		for(Object item : collection)
			answer[index ++] = unmarshaller.unmarshal(item, outType);
		return answer;
	}
	
	protected abstract Class<?> getOutType();
	protected abstract Object[] newArray(int length);
	
}
