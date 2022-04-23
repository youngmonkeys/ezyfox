package com.tvd12.ezyfox.binding.testing.scan1;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.annotation.EzyReaderImpl;
import com.tvd12.ezyfox.binding.impl.EzyAbstractReader;
import com.tvd12.ezyfox.entity.EzyArray;

@EzyReaderImpl
public class Scan1ClassCReaderImpl
        extends EzyAbstractReader<EzyArray, Scan1ClassC> {

    @Override
    public Scan1ClassC read(EzyUnmarshaller unmarshaller, EzyArray array) {
        Scan1ClassC answer = new Scan1ClassC();
        answer.setX(array.get(0, String.class));
        answer.setY(array.get(1, String.class));
        answer.setZ(array.get(2, String.class));
        return answer;
    }

    public EzyArray write(EzyMarshaller marshaller, Scan1ClassC object) {
        return newArrayBuilder()
                .append(object.getX())
                .append(object.getY())
                .append(object.getZ())
                .build();
    }
}
