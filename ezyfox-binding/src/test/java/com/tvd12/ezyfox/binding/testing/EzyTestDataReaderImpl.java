package com.tvd12.ezyfox.binding.testing;

import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.impl.EzyAbstractReader;
import com.tvd12.ezyfox.entity.EzyObject;

@SuppressWarnings("rawtypes")
public class EzyTestDataReaderImpl extends EzyAbstractReader {

    @Override
    public Object read(EzyUnmarshaller arg0, Object arg1) {
        EzyObject value = ((EzyObject) arg1);
        EzyTestData data = new EzyTestData();
        if (value.isNotNullValue("1")) {
            data.setData1(arg0.unmarshal(value.getValue("1", int.class), int.class));
        }
        if (value.isNotNullValue("2")) {
            data.setData2(arg0.unmarshal(value.getValue("2", int.class), int.class));
        }
        if (value.isNotNullValue("3")) {
            data.setData2(arg0.unmarshal(value.getValue("3", int.class), int.class));
        }
        return data;
    }
}
