package com.tvd12.ezyfox.binding.reader;

public class EzyPrimitiveByteArrayReader extends EzyPrimitiveArrayReader {

    private static final EzyPrimitiveByteArrayReader INSTANCE = new EzyPrimitiveByteArrayReader();

    private EzyPrimitiveByteArrayReader() {}

    public static EzyPrimitiveByteArrayReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected Class<?> getOutType() {
        return byte.class;
    }

    @Override
    protected Object newArray(int length) {
        return new byte[length];
    }

    @Override
    protected void setValue(Object array, int index, Object value) {
        ((byte[]) array)[index] = ((Number) value).byteValue();
    }
}
