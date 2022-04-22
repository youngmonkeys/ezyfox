package com.tvd12.ezyfox.binding.reader;

public class EzyPrimitiveCharArrayReader extends EzyPrimitiveArrayReader {

    private static final EzyPrimitiveCharArrayReader INSTANCE = new EzyPrimitiveCharArrayReader();

    private EzyPrimitiveCharArrayReader() {
    }

    public static EzyPrimitiveCharArrayReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected Class<?> getOutType() {
        return char.class;
    }

    @Override
    protected Object newArray(int length) {
        return new char[length];
    }

    @Override
    protected void setValue(Object array, int index, Object value) {
        ((char[])array)[index] = (Character)value;
    }

}
