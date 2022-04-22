package com.tvd12.ezyfox.binding.reader;

public class EzyPrimitiveFloatArrayReader extends EzyPrimitiveArrayReader {

    private static final EzyPrimitiveFloatArrayReader INSTANCE = new EzyPrimitiveFloatArrayReader();

    private EzyPrimitiveFloatArrayReader() {
    }

    public static EzyPrimitiveFloatArrayReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected Class<?> getOutType() {
        return float.class;
    }

    @Override
    protected Object newArray(int length) {
        return new float[length];
    }

    @Override
    protected void setValue(Object array, int index, Object value) {
        ((float[])array)[index] = ((Number)value).floatValue();
    }

}
