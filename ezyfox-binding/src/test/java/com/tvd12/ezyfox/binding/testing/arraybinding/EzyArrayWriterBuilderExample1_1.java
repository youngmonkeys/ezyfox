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

public class EzyArrayWriterBuilderExample1_1 {

    @Test
    @SuppressWarnings("unchecked")
    public void test() throws Exception {
        EzyMarshaller marshaller = new EzySimpleMarshaller();
        EzyArrayWriterBuilder.setDebug(true);
        EzyArrayWriterBuilder writerBuilder
                = new EzyArrayWriterBuilder(new EzyClass(ClassA2.class));
        EzyWriter<ClassA2, EzyArray> writer = writerBuilder.build();
        EzyArray array = writer.write(marshaller, new ClassA2());
        System.out.println(array);

        EzyUnmarshaller unmarshaller = new EzySimpleUnmarshaller();
        EzyArrayReaderBuilder.setDebug(true);
        EzyArrayReaderBuilder readerBuilder
                = new EzyArrayReaderBuilder(new EzyClass(ClassA2.class));
        EzyReader<EzyArray, ClassA2> reader = readerBuilder.build();
        ClassA2 classA = reader.read(unmarshaller, array);
        System.out.println(classA);
    }}