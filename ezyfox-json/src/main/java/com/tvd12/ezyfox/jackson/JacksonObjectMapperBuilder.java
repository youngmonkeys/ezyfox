package com.tvd12.ezyfox.jackson;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyArrayList;
import com.tvd12.ezyfox.entity.EzyHashMap;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.entity.EzyRoArray;
import com.tvd12.ezyfox.entity.EzyRoObject;

public class JacksonObjectMapperBuilder implements EzyBuilder<ObjectMapper> {

	public static JacksonObjectMapperBuilder newInstance() {
		return new JacksonObjectMapperBuilder();
	}
	
	@Override
	public ObjectMapper build() {
		return new ObjectMapper()
				.registerModule(newModule());
	}
	
	protected Module newModule() {
		JacksonArraySerializer arraySerializer = new JacksonArraySerializer();
		JacksonObjectSerializer objectSerializer = new JacksonObjectSerializer();
		SimpleModule module = new SimpleModule();
		module.addSerializer(EzyRoArray.class, arraySerializer);
		module.addSerializer(EzyArray.class, arraySerializer);
		module.addSerializer(EzyArrayList.class, arraySerializer);
		module.addSerializer(EzyRoObject.class, objectSerializer);
		module.addSerializer(EzyObject.class, objectSerializer);
		module.addSerializer(EzyHashMap.class, objectSerializer);
		return module;
	}
	
}
