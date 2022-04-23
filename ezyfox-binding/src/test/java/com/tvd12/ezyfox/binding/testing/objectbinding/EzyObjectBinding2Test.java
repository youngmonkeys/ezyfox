package com.tvd12.ezyfox.binding.testing.objectbinding;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.EzyWriter;
import com.tvd12.ezyfox.binding.impl.EzyObjectReaderBuilder;
import com.tvd12.ezyfox.binding.impl.EzyObjectWriterBuilder;
import com.tvd12.ezyfox.binding.impl.EzySimpleMarshaller;
import com.tvd12.ezyfox.binding.impl.EzySimpleUnmarshaller;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.reflect.EzyClass;

public class EzyObjectBinding2Test {

    @SuppressWarnings("unchecked")
    @Test
    public void test() {
        EzyObjectWriterBuilder.setDebug(true);
        EzyObjectReaderBuilder.setDebug(true);

        EzyMarshaller marshaller = new EzySimpleMarshaller();
        EzyUnmarshaller unmarshaller = new EzySimpleUnmarshaller();

        EzyObjectWriterBuilder writerBuilder
                = new EzyObjectWriterBuilder(new EzyClass(ClassB.class));
        EzyWriter<ClassB, EzyObject> writer = writerBuilder.build();
        EzyObject object = writer.write(marshaller, new ClassB());
        System.out.println(object);

        EzyObjectReaderBuilder readerBuilder
                = new EzyObjectReaderBuilder(new EzyClass(ClassB.class));
        EzyReader<EzyObject, ClassB> reader = readerBuilder.build();
        ClassB ClassB = reader.read(unmarshaller, object);
        System.out.println(ClassB);
    }
}
