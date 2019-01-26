package com.tvd12.ezyfox.binding.writer;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyWriter;
import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

@SuppressWarnings("rawtypes")
public class EzyIterableWriter implements EzyWriter<Iterable, EzyArray> {

	private static final EzyIterableWriter INSTANCE = new EzyIterableWriter();
	
	private EzyIterableWriter() {
	}
	
	public static EzyIterableWriter getInstance() {
		return INSTANCE;
	}

	@Override
	public EzyArray write(EzyMarshaller marshaller, Iterable iterable) {
		EzyArrayBuilder builder = EzyEntityFactory.newArrayBuilder();
		for(Object value : iterable) {
			Object mvalue = marshaller.marshal(value);
			builder.append(mvalue);
		}
		return builder.build();
	}
	
	
}
