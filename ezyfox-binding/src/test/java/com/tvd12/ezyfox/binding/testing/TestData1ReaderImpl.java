package com.tvd12.ezyfox.binding.testing;

import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.annotation.EzyReaderImpl;
import com.tvd12.ezyfox.binding.impl.EzyAbstractReader;
import com.tvd12.ezyfox.entity.EzyArray;

@EzyReaderImpl
public class TestData1ReaderImpl extends EzyAbstractReader<EzyArray, EzyTestData1> {

    @Override
    public EzyTestData1 read(EzyUnmarshaller arg0, EzyArray array) {
        EzyTestData1 data = new EzyTestData1();
        data.setData1(arg0.unmarshal(array.getValue(0, int.class), int.class));
        data.setData2(arg0.unmarshal(array.getValue(1, int.class), int.class));
        data.setData3(arg0.unmarshal(array.getValue(2, int.class), int.class));
        return data;
    }
}
