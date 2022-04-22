package com.tvd12.ezyfox.binding.reader;

public class EzyPrimitiveLongArrayReader extends EzyPrimitiveArrayReader {

    private static final EzyPrimitiveLongArrayReader INSTANCE = new EzyPrimitiveLongArrayReader();
    
    private EzyPrimitiveLongArrayReader() {
    }
    
    public static EzyPrimitiveLongArrayReader getInstance() {
        return INSTANCE;
    }
    
    @Override
    protected Class<?> getOutType() {
        return long.class;
    }

    @Override
    protected Object newArray(int length) {
        return new long[length];
    }
    
    @Override
    protected void setValue(Object array, int index, Object value) {
        ((long[])array)[index] = ((Number)value).longValue();
    }

}
