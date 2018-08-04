package com.tvd12.ezyfox.binding.testing.arraybinding;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.EzyWriter;
import com.tvd12.ezyfox.binding.impl.EzyArrayReaderBuilder;
import com.tvd12.ezyfox.binding.impl.EzyArrayWriterBuilder;
import com.tvd12.ezyfox.binding.impl.EzySimpleMarshaller;
import com.tvd12.ezyfox.binding.impl.EzySimpleUnmarshaller;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.reflect.EzyClass;

public class EzyArrayWriterBuilderExample1 {

	@Test
	@SuppressWarnings("unchecked")
	public void test() throws Exception {
		EzyMarshaller marshaller = new EzySimpleMarshaller();
		EzyArrayWriterBuilder.setDebug(true);
		EzyArrayWriterBuilder writerBuilder 
				= new EzyArrayWriterBuilder(new EzyClass(ClassA.class));
		EzyWriter<ClassA, EzyArray> writer = writerBuilder.build();
		EzyArray array = writer.write(marshaller, new ClassA());
		System.out.println(array);
		
		EzyUnmarshaller unmarshaller = new EzySimpleUnmarshaller();
		EzyArrayReaderBuilder.setDebug(true);
		EzyArrayReaderBuilder readerBuilder 
				= new EzyArrayReaderBuilder(new EzyClass(ClassA.class));
		EzyReader<EzyArray, ClassA> reader = readerBuilder.build();
		ClassA classA = reader.read(unmarshaller, array);
		System.out.println(classA);
	}
}
