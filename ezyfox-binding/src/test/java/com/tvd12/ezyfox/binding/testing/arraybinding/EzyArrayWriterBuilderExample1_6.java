package com.tvd12.ezyfox.binding.testing.arraybinding;

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
import org.testng.annotations.Test;

public class EzyArrayWriterBuilderExample1_6 {

    @Test
    @SuppressWarnings("unchecked")
    public void test() throws Exception {
        EzyArrayWriterBuilder.setDebug(true);
        EzyArrayReaderBuilder.setDebug(true);

        EzyMarshaller marshaller = new EzySimpleMarshaller();
        EzyUnmarshaller unmarshaller = new EzySimpleUnmarshaller();

        EzyArrayWriterBuilder writerBuilder
            = new EzyArrayWriterBuilder(new EzyClass(ClassA7.class));
        EzyWriter<ClassA7, EzyArray> writer = writerBuilder.build();
        EzyArray array = writer.write(marshaller, new ClassA7());
        System.out.println(array);

        EzyArrayReaderBuilder readerBuilder
            = new EzyArrayReaderBuilder(new EzyClass(ClassA7.class));
        EzyReader<EzyArray, ClassA7> reader = readerBuilder.build();
        ClassA7 classA = reader.read(unmarshaller, array);
        System.out.println(classA);
    }
}
