package com.tvd12.ezyfox.binding.testing;

import com.tvd12.ezyfox.binding.EzyWriter;
import com.tvd12.ezyfox.binding.impl.EzyObjectWriterBuilder;
import com.tvd12.ezyfox.binding.impl.EzySimpleMarshaller;
import com.tvd12.ezyfox.reflect.EzyClass;
import org.testng.annotations.Test;

public class EzyPointWriterExample3 {

    @Test
    @SuppressWarnings("unchecked")
    public void test() throws Exception {
        EzyClass pointClazz = new EzyClass(Point.class);
        EzyClass dataClazz = new EzyClass(EzyTestData.class);
        EzyObjectWriterBuilder pointWriterBuilder = new EzyObjectWriterBuilder(pointClazz);
        EzyObjectWriterBuilder dataWriterBuilder = new EzyObjectWriterBuilder(dataClazz);
        EzyWriter<Object, Point> pointWriter = pointWriterBuilder.build();
        EzyWriter<Object, Point> dataWriter = dataWriterBuilder.build();

        EzySimpleMarshaller marshaller = new EzySimpleMarshaller();
        marshaller.addWriter(Point.class, pointWriter);
        marshaller.addWriter(EzyTestData.class, dataWriter);
        marshaller.addWriter(new TestData1WriterImpl());

        System.out.println(pointWriter.write(marshaller, new Point()));
    }
}
