package com.tvd12.ezyfox.binding.testing;

import com.tvd12.ezyfox.binding.EzyWriter;
import com.tvd12.ezyfox.binding.impl.EzySimpleMarshaller;
import com.tvd12.ezyfox.entity.EzyArray;
import org.testng.annotations.Test;

public class EzyPointWriterExample4 {

    @Test
    @SuppressWarnings("unchecked")
    public void test() throws Exception {
        EzyWriter<Object, Point> pointWriter = new EzyPointWriterImpl();
        EzyWriter<Object, Point> dataWriter = new EzyTestDataWriterImpl();
        EzyWriter<EzyTestData1, EzyArray> data1Writer = new TestData1WriterImpl();

        EzySimpleMarshaller marshaller = new EzySimpleMarshaller();
        marshaller.addWriter(Point.class, pointWriter);
        marshaller.addWriter(EzyTestData.class, dataWriter);
        marshaller.addWriter(data1Writer);

        System.out.println(pointWriter.write(marshaller, new Point()));

    }
}
