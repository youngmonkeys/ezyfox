package com.tvd12.ezyfox.binding.testing;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.entity.EzyObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@SuppressWarnings("rawtypes")
public class EzyPointReaderImpl implements EzyReader {

    @Override
    public Object read(EzyUnmarshaller arg0, Object arg1) {
        EzyObject value = (EzyObject) arg1;
        Point object = new Point();
        if (value.isNotNullValue("a1")) {
            object.a1 = arg0.unmarshal(value.getValue("a1", String.class), String.class);
        }
        if (value.isNotNullValue("3")) {
            object.setX(arg0.unmarshal(value.getValue("3", int.class), int.class));
        }
        if (value.isNotNullValue("x")) {
            object.setX(arg0.unmarshal(value.getValue("x", int.class), int.class));
        }
        if (value.isNotNullValue("y")) {
            object.setY(arg0.unmarshal(value.getValue("y", int.class), int.class));
        }
        if (value.isNotNullValue("point")) {
            object.setPoint(arg0.unmarshal(value.getValue("point", Point.class), Point.class));
        }
        if (value.isNotNullValue("data")) {
            object.setData(arg0.unmarshal(value.getValue("data", EzyTestData.class), EzyTestData.class));
        }
        if (value.isNotNullValue("date")) {
            object.setDate(arg0.unmarshal(value.getValue("date", Date.class), Date.class));
        }
        if (value.isNotNullValue("localDate")) {
            object.setLocalDate(arg0.unmarshal(value.getValue("localDate", LocalDate.class), LocalDate.class));
        }
        if (value.isNotNullValue("localDateTime")) {
            object.setLocalDateTime(arg0.unmarshal(value.getValue("localDateTime", LocalDateTime.class), LocalDateTime.class));
        }
        if (value.isNotNullValue("clazz")) {
            object.setClazz(arg0.unmarshal(value.getValue("clazz", Class.class), Class.class));
        }
        if (value.isNotNullValue("data1")) {
            object.setData1(arg0.unmarshal(TestData1ReaderImpl.class, value.getValue("data1", EzyTestData1.class)));
        }
        return object;
    }
}
