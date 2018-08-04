package com.tvd12.ezyfox.binding.template;

import java.util.Map;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyWriter;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.util.EzyEntityBuilders;

@SuppressWarnings("rawtypes")
public class EzyMapObjectWriter
		extends EzyEntityBuilders
		implements EzyWriter<Map, EzyObject> {

	@Override
	public EzyObject write(EzyMarshaller marshaller, Map map) {
		EzyObjectBuilder builder = newObjectBuilder();
		for(Object key : map.keySet()) {
			Object tkey = marshaller.marshal(key);
			Object tvalue = marshaller.marshal(map.get(key));
			builder.append(tkey, tvalue);
		}
		return builder.build();
	}

}
