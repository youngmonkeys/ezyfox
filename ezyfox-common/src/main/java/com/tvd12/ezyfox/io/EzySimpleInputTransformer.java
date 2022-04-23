package com.tvd12.ezyfox.io;

import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.function.EzyToObject;
import com.tvd12.ezyfox.util.EzyEntityBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EzySimpleInputTransformer
    extends EzyEntityBuilders
    implements EzyInputTransformer {
    private static final long serialVersionUID = 5436415615070699119L;

    @SuppressWarnings("rawtypes")
    protected final Map<Class, EzyToObject> transformers = defaultTransformers();

    @Override
    public Object transform(Object value) {
        return value == null
            ? transformNullValue()
            : transformNonNullValue(value);
    }

    protected Object transformNullValue() {
        return null;
    }

    protected Object transformNonNullValue(Object value) {
        return transformNonNullValue(value, transformers);
    }

    @SuppressWarnings("rawtypes")
    protected Object transformNonNullValue(
        Object value, Map<Class, EzyToObject> transformers) {
        return transformNonNullValue(value, value.getClass(), transformers);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Object transformNonNullValue(
        Object value, Class type, Map<Class, EzyToObject> transformers) {
        EzyToObject trans = transformers.get(type);
        if (trans != null) {
            return trans.transform(value);
        } else if (type.isEnum()) {
            return value.toString();
        } else if (value instanceof Map) {
            return transformMap(value);
        } else if (value instanceof EzyBuilder) {
            return transformBuilder(value);
        }
        return value;
    }

    @SuppressWarnings("rawtypes")
    protected Object transformMap(Object value) {
        EzyObject object = newObject();
        object.putAll((Map) value);
        return object;
    }

    @SuppressWarnings("rawtypes")
    protected Object transformBuilder(Object value) {
        return transformNonNullValue(((EzyBuilder) value).build());
    }

    @SuppressWarnings("rawtypes")
    private Map<Class, EzyToObject> defaultTransformers() {
        Map<Class, EzyToObject> answer = new ConcurrentHashMap<>();
        addOtherTransformers(answer);
        addEntityTransformers(answer);
        addWrapperTransformers(answer);
        addWrapperArrayTransformers(answer);
        addPrimitiveArrayTransformers(answer);
        addTwoDimensionsWrapperArrayTransformers(answer);
        addTwoDimensionsPrimitiveArrayTransformers(answer);
        return answer;
    }

    @SuppressWarnings("rawtypes")
    protected void addWrapperTransformers(Map<Class, EzyToObject> answer) {
        answer.put(Boolean.class, (EzyToObject<Boolean>) value -> value);
        answer.put(Byte.class, (EzyToObject<Byte>) value -> value);
        answer.put(Character.class, (EzyToObject<Character>) value -> (byte) value.charValue());
        answer.put(Double.class, (EzyToObject<Double>) value -> value);
        answer.put(Float.class, (EzyToObject<Float>) value -> value);
        answer.put(Integer.class, (EzyToObject<Integer>) value -> value);
        answer.put(Long.class, (EzyToObject<Long>) value -> value);
        answer.put(Short.class, (EzyToObject<Short>) value -> value);
        answer.put(String.class, (EzyToObject<String>) value -> value);
    }

    @SuppressWarnings("rawtypes")
    protected void addPrimitiveArrayTransformers(Map<Class, EzyToObject> answer) {
        answer.put(boolean[].class, (EzyToObject<boolean[]>) EzyDataConverter::arrayToList);
        answer.put(byte[].class, (EzyToObject<byte[]>) value -> value);
        answer.put(char[].class, (EzyToObject<char[]>) EzyDataConverter::charArrayToByteArray);
        answer.put(double[].class, (EzyToObject<double[]>) EzyDataConverter::arrayToList);
        answer.put(float[].class, (EzyToObject<float[]>) EzyDataConverter::arrayToList);
        answer.put(int[].class, (EzyToObject<int[]>) EzyDataConverter::arrayToList);
        answer.put(long[].class, (EzyToObject<long[]>) EzyDataConverter::arrayToList);
        answer.put(short[].class, (EzyToObject<short[]>) EzyDataConverter::arrayToList);
        answer.put(String[].class, (EzyToObject<String[]>) EzyDataConverter::arrayToList);
    }

    @SuppressWarnings("rawtypes")
    protected void addWrapperArrayTransformers(Map<Class, EzyToObject> answer) {
        answer.put(Boolean[].class, (EzyToObject<Boolean[]>) EzyDataConverter::arrayToList);
        answer.put(Byte[].class, (EzyToObject<Byte[]>) EzyDataConverter::toPrimitiveByteArray);
        answer.put(
            Character[].class,
            (EzyToObject<Character[]>) EzyDataConverter::charWrapperArrayToPrimitiveByteArray
        );
        answer.put(Double[].class, (EzyToObject<Double[]>) EzyDataConverter::arrayToList);
        answer.put(Float[].class, (EzyToObject<Float[]>) EzyDataConverter::arrayToList);
        answer.put(Integer[].class, (EzyToObject<Integer[]>) EzyDataConverter::arrayToList);
        answer.put(Long[].class, (EzyToObject<Long[]>) EzyDataConverter::arrayToList);
        answer.put(Short[].class, (EzyToObject<Short[]>) EzyDataConverter::arrayToList);
    }

    //primitive two dimensions array
    @SuppressWarnings("rawtypes")
    protected void addTwoDimensionsPrimitiveArrayTransformers(Map<Class, EzyToObject> answer) {
        answer.put(boolean[][].class, (EzyToObject<boolean[][]>) value -> newArrayBuilder().append(value).build());
        answer.put(byte[][].class, (EzyToObject<byte[][]>) value -> newArrayBuilder().append(value).build());
        answer.put(char[][].class, (EzyToObject<char[][]>) value -> newArrayBuilder().append(value).build());
        answer.put(double[][].class, (EzyToObject<double[][]>) value -> newArrayBuilder().append(value).build());
        answer.put(float[][].class, (EzyToObject<float[][]>) value -> newArrayBuilder().append(value).build());
        answer.put(int[][].class, (EzyToObject<int[][]>) value -> newArrayBuilder().append(value).build());
        answer.put(long[][].class, (EzyToObject<long[][]>) value -> newArrayBuilder().append(value).build());
        answer.put(short[][].class, (EzyToObject<short[][]>) value -> newArrayBuilder().append(value).build());
    }

    //wrapper two dimensions array
    @SuppressWarnings("rawtypes")
    protected void addTwoDimensionsWrapperArrayTransformers(Map<Class, EzyToObject> answer) {
        answer.put(Boolean[][].class, (EzyToObject<Boolean[][]>) value -> newArrayBuilder().append(value).build());
        answer.put(Byte[][].class, (EzyToObject<Byte[][]>) value -> newArrayBuilder().append(value).build());
        answer.put(Character[][].class, (EzyToObject<Character[][]>) value -> newArrayBuilder().append(value).build());
        answer.put(Double[][].class, (EzyToObject<Double[][]>) value -> newArrayBuilder().append(value).build());
        answer.put(Float[][].class, (EzyToObject<Float[][]>) value -> newArrayBuilder().append(value).build());
        answer.put(Integer[][].class, (EzyToObject<Integer[][]>) value -> newArrayBuilder().append(value).build());
        answer.put(Long[][].class, (EzyToObject<Long[][]>) value -> newArrayBuilder().append(value).build());
        answer.put(Short[][].class, (EzyToObject<Short[][]>) value -> newArrayBuilder().append(value).build());
        answer.put(String[][].class, (EzyToObject<String[][]>) value -> newArrayBuilder().append(value).build());
    }

    //entity
    @SuppressWarnings("rawtypes")
    protected void addEntityTransformers(Map<Class, EzyToObject> answer) {
        answer.put(EzyObject[].class, (EzyToObject<EzyObject[]>) value -> newArrayBuilder().append(value).build());
        answer.put(EzyObject[][].class, (EzyToObject<EzyObject[][]>) value -> newArrayBuilder().append(value).build());
    }

    //other
    @SuppressWarnings("rawtypes")
    protected void addOtherTransformers(Map<Class, EzyToObject> answer) {
        answer.put(Date.class, (EzyToObject<Date>) EzyDates::format);

        answer.put(LocalDate.class, (EzyToObject<LocalDate>) value -> EzyDates.format(value, EzyDates.DATE_PATTERN));

        answer.put(LocalTime.class, (EzyToObject<LocalTime>) value -> EzyDates.format(value, EzyDates.TIME_PATTERN));

        answer.put(LocalDateTime.class, (EzyToObject<LocalDateTime>) EzyDates::format);

        answer.put(Class.class, (EzyToObject<Class>) Class::getName);

    }
}
