package com.tvd12.ezyfox.binding.testing;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyWriter;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

@SuppressWarnings("rawtypes")
public class EzyTestDataWriterImpl implements EzyWriter {

	@Override
	public EzyObject write(EzyMarshaller arg0, Object arg1) {
		EzyTestData object = ((EzyTestData)(arg1));
		EzyObjectBuilder builder = EzyEntityFactory.create(com.tvd12.ezyfox.builder.EzyObjectBuilder.class);
		builder.append("1", (Object)arg0.marshal(object.getData1()));
		builder.append("2", (Object)arg0.marshal(object.getData1()));
		builder.append("3", (Object)arg0.marshal(object.getData1()));
		return builder.build();
	}
	
}
