package com.tvd12.ezyfox.binding.testing;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyWriter;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

@SuppressWarnings("rawtypes")
public class EzyPointWriterImpl implements EzyWriter {

	public EzyObject write(EzyMarshaller arg0, Object arg1) {
		Point object = ((Point)(arg1));
		EzyObjectBuilder builder = EzyEntityFactory.create(com.tvd12.ezyfox.builder.EzyObjectBuilder.class);
		builder.append("a1", (Object)arg0.marshal(object.a1));
		builder.append("xY", (Object)arg0.marshal(object.getXY()));
		builder.append("3", (Object)arg0.marshal(object.getX()));
		builder.append("x", (Object)arg0.marshal(object.getX()));
		builder.append("y", (Object)arg0.marshal(object.getY()));
		builder.append("b", (Object)arg0.marshal(object.getB()));
		builder.append("point", (Object)arg0.marshal(object.getPoint()));
		builder.append("data", (Object)arg0.marshal(object.getData()));
		builder.append("date", (Object)arg0.marshal(object.getDate()));
		builder.append("localDate", (Object)arg0.marshal(object.getLocalDate()));
		builder.append("localDateTime", (Object)arg0.marshal(object.getLocalDateTime()));
		builder.append("clazz", (Object)arg0.marshal(object.getClazz()));
		builder.append("data1", (Object)arg0.marshal(TestData1WriterImpl.class, object.getData1()));
		return builder.build();
	}

}
