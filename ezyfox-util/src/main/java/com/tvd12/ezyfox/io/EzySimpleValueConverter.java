package com.tvd12.ezyfox.io;

import com.tvd12.ezyfox.function.EzyToObject;
import com.tvd12.ezyfox.reflect.EzyClasses;
import com.tvd12.ezyfox.util.EzyLoggable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.tvd12.ezyfox.io.EzyDataConverter.*;
import static com.tvd12.ezyfox.io.EzyNumbersConverter.*;
import static com.tvd12.ezyfox.io.EzyStringConveter.stringToChar;
import static com.tvd12.ezyfox.io.EzyStringConveter.*;

@SuppressWarnings({"rawtypes", "unchecked"})
public class EzySimpleValueConverter
    extends EzyLoggable
    implements EzyValueConverter {

    protected final Map<Class, EzyToObject> transformers;

    public EzySimpleValueConverter() {
        this.transformers = defaultTransformers();
    }

    public static EzySimpleValueConverter getSingleton() {
        return EzySingletonValueConverter.getInstance();
    }

    @Override
    public <T> T convert(Object value, Class<T> outType) {
        if (value == null) {
            return null;
        }
        EzyToObject transformer = transformers.get(outType);
        if (transformer != null) {
            return (T) transformer.transform(value);
        }
        if (outType.isEnum()) {
            return (T) Enum.valueOf((Class<Enum>) outType, value.toString());
        }
        return (T) value;
    }

    protected IllegalArgumentException
    newTransformerException(Class<?> type, Object value) {
        return new IllegalArgumentException(
            "can't transform: " + value + " to " + type.getSimpleName() + " value");
    }

    //tank
    private Map<Class, EzyToObject>
    defaultTransformers() {
        Map<Class, EzyToObject> answer = new HashMap<>();
        addOtherTransformers(answer);
        addWrapperTransformers(answer);
        addPrimitiveTransformers(answer);
        addWrapperArrayTransformers(answer);
        addPrimitiveArrayTransformers(answer);
        addTwoDimensionsWrapperArrayTransformers(answer);
        addTwoDimensionsPrimitiveArrayTransformers(answer);
        return answer;
    }

    protected void addPrimitiveTransformers(Map<Class, EzyToObject> answer) {
        answer.put(boolean.class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return Boolean.valueOf((String) value);
                }
                if (value instanceof Boolean) {
                    return (Boolean) value;
                }
                throw newTransformerException(boolean.class, value);
            }
        });
        answer.put(byte.class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return Byte.valueOf((String) value);
                }
                if (value instanceof Number) {
                    return ((Number) value).byteValue();
                }
                throw newTransformerException(byte.class, value);
            }
        });
        answer.put(char.class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof Character) {
                    return ((Character) value);
                }
                if (value instanceof Number) {
                    return (char) ((Number) value).byteValue();
                }
                if (value instanceof String) {
                    return stringToChar((String) value);
                }
                throw newTransformerException(char.class, value);
            }
        });
        answer.put(double.class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return Double.valueOf((String) value);
                }
                if (value instanceof Number) {
                    return ((Number) value).doubleValue();
                }
                throw newTransformerException(double.class, value);
            }
        });
        answer.put(float.class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return Float.valueOf((String) value);
                }
                if (value instanceof Number) {
                    return ((Number) value).floatValue();
                }
                throw newTransformerException(float.class, value);
            }
        });
        answer.put(int.class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return Integer.valueOf((String) value);
                }
                if (value instanceof Number) {
                    return ((Number) value).intValue();
                }
                throw newTransformerException(int.class, value);
            }
        });
        answer.put(long.class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return Long.valueOf((String) value);
                }
                if (value instanceof Number) {
                    return ((Number) value).longValue();
                }
                throw newTransformerException(long.class, value);
            }
        });
        answer.put(short.class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return Short.valueOf((String) value);
                }
                if (value instanceof Number) {
                    return ((Number) value).shortValue();
                }
                throw newTransformerException(short.class, value);
            }
        });
    }

    protected void addWrapperTransformers(Map<Class, EzyToObject> answer) {
        answer.put(Boolean.class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return Boolean.valueOf((String) value);
                }
                if (value instanceof Boolean) {
                    return (Boolean) value;
                }
                throw newTransformerException(Boolean.class, value);
            }
        });
        answer.put(Byte.class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return Byte.valueOf((String) value);
                }
                if (value instanceof Number) {
                    return ((Number) value).byteValue();
                }
                throw newTransformerException(Byte.class, value);
            }
        });
        answer.put(Character.class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof Character) {
                    return ((Character) value);
                }
                if (value instanceof Number) {
                    return (char) ((Number) value).byteValue();
                }
                if (value instanceof String) {
                    return stringToChar((String) value);
                }
                throw newTransformerException(Character.class, value);
            }
        });
        answer.put(Double.class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return Double.valueOf((String) value);
                }
                if (value instanceof Number) {
                    return ((Number) value).doubleValue();
                }
                throw newTransformerException(Double.class, value);
            }
        });
        answer.put(Float.class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return Float.valueOf((String) value);
                }
                if (value instanceof Number) {
                    return ((Number) value).floatValue();
                }
                throw newTransformerException(float.class, value);
            }
        });
        answer.put(Integer.class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return Integer.valueOf((String) value);
                }
                if (value instanceof Number) {
                    return ((Number) value).intValue();
                }
                throw newTransformerException(Integer.class, value);
            }
        });
        answer.put(Long.class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return Long.valueOf((String) value);
                }
                if (value instanceof Number) {
                    return ((Number) value).longValue();
                }
                throw newTransformerException(Long.class, value);
            }
        });
        answer.put(Short.class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return Short.valueOf((String) value);
                }
                if (value instanceof Number) {
                    return ((Number) value).shortValue();
                }
                throw newTransformerException(Short.class, value);
            }
        });

        answer.put(String.class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                return value.toString();
            }
        });
    }

    protected void addPrimitiveArrayTransformers(Map<Class, EzyToObject> answer) {
        answer.put(boolean[].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToPrimitiveBoolArray((String) value);
                }
                if (value instanceof boolean[]) {
                    return value;
                }
                if (value instanceof Boolean[]) {
                    return boolArrayWrapperToPrimitive((Boolean[]) value);
                }
                if (value instanceof Collection) {
                    return collectionToPrimitiveBoolArray((Collection<Boolean>) value);
                }
                throw newTransformerException(boolean[].class, value);
            }
        });
        answer.put(byte[].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToPrimitiveByteArray((String) value);
                }
                if (value instanceof byte[]) {
                    return value;
                }
                if (value instanceof Number[]) {
                    return numbersToPrimitiveBytes((Number[]) value);
                }
                if (value instanceof Collection) {
                    return numbersToPrimitiveBytes((Collection) value);
                }
                throw newTransformerException(byte[].class, value);
            }
        });
        answer.put(char[].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToPrimitiveCharArray((String) value);
                }
                if (value instanceof char[]) {
                    return value;
                }
                if (value instanceof Object[]) {
                    return numbersToPrimitiveChars((Object[]) value);
                }
                if (value instanceof Collection) {
                    return numbersToPrimitiveChars((Collection) value);
                }
                throw newTransformerException(char[].class, value);
            }
        });

        answer.put(double[].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToPrimitiveDoubleArray((String) value);
                }
                if (value instanceof double[]) {
                    return value;
                }
                if (value instanceof Number[]) {
                    return numbersToPrimitiveDoubles((Number[]) value);
                }
                if (value instanceof Collection) {
                    return numbersToPrimitiveDoubles((Collection) value);
                }
                throw newTransformerException(double[].class, value);
            }
        });
        answer.put(float[].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToPrimitiveFloatArray((String) value);
                }
                if (value instanceof float[]) {
                    return value;
                }
                if (value instanceof Number[]) {
                    return numbersToPrimitiveFloats((Number[]) value);
                }
                if (value instanceof Collection) {
                    return numbersToPrimitiveFloats((Collection) value);
                }
                throw newTransformerException(float[].class, value);
            }
        });
        answer.put(int[].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToPrimitiveIntArray((String) value);
                }
                if (value instanceof int[]) {
                    return value;
                }
                if (value instanceof Number[]) {
                    return numbersToPrimitiveInts((Number[]) value);
                }
                if (value instanceof Collection) {
                    return numbersToPrimitiveInts((Collection) value);
                }
                throw newTransformerException(int[].class, value);
            }
        });
        answer.put(long[].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToPrimitiveLongArray((String) value);
                }
                if (value instanceof long[]) {
                    return value;
                }
                if (value instanceof Number[]) {
                    return numbersToPrimitiveLongs((Number[]) value);
                }
                if (value instanceof Collection) {
                    return numbersToPrimitiveLongs((Collection) value);
                }
                throw newTransformerException(long[].class, value);
            }
        });
        answer.put(short[].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToPrimitiveShortArray((String) value);
                }
                if (value instanceof short[]) {
                    return value;
                }
                if (value instanceof Number[]) {
                    return numbersToPrimitiveShorts((Number[]) value);
                }
                if (value instanceof Collection) {
                    return numbersToPrimitiveShorts((Collection) value);
                }
                throw newTransformerException(short[].class, value);
            }
        });
    }

    protected void addWrapperArrayTransformers(Map<Class, EzyToObject> answer) {
        answer.put(Boolean[].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToWrapperBoolArray((String) value);
                }
                if (value instanceof Boolean[]) {
                    return value;
                }
                if (value instanceof boolean[]) {
                    return boolArrayPrimitiveToWrapper((boolean[]) value);
                }
                if (value instanceof Collection) {
                    return collectionToWrapperBoolArray((Collection<Boolean>) value);
                }
                throw newTransformerException(Boolean[].class, value);
            }
        });
        answer.put(Byte[].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToWrapperByteArray((String) value);
                }
                if (value instanceof Number[]) {
                    return numbersToWrapperBytes((Number[]) value);
                }
                if (value instanceof byte[]) {
                    return byteArrayPrimitiveToWrapper((byte[]) value);
                }
                if (value instanceof Collection) {
                    return numbersToWrapperBytes((Collection) value);
                }
                throw newTransformerException(Boolean[].class, value);
            }
        });
        answer.put(Character[].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToWrapperCharArray((String) value);
                }
                if (value instanceof Character[]) {
                    return value;
                }
                if (value instanceof char[]) {
                    return charArrayPrimitiveToWrapper((char[]) value);
                }
                if (value instanceof Collection) {
                    return numbersToWrapperChars((Collection) value);
                }
                throw newTransformerException(Character[].class, value);
            }
        });
        answer.put(Double[].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToWrapperDoubleArray((String) value);
                }
                if (value instanceof Number[]) {
                    return numbersToWrapperDoubles((Number[]) value);
                }
                if (value instanceof double[]) {
                    return doubleArrayPrimitiveToWrapper((double[]) value);
                }
                if (value instanceof Collection) {
                    return numbersToWrapperDoubles((Collection) value);
                }
                throw newTransformerException(Double[].class, value);
            }
        });
        answer.put(Float[].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToWrapperFloatArray((String) value);
                }
                if (value instanceof Number[]) {
                    return numbersToWrapperFloats((Number[]) value);
                }
                if (value instanceof float[]) {
                    return floatArrayPrimitiveToWrapper((float[]) value);
                }
                if (value instanceof Collection) {
                    return numbersToWrapperFloats((Collection) value);
                }
                throw newTransformerException(Float[].class, value);
            }
        });
        answer.put(Integer[].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToWrapperIntArray((String) value);
                }
                if (value instanceof Number[]) {
                    return numbersToWrapperInts((Number[]) value);
                }
                if (value instanceof int[]) {
                    return intArrayPrimitiveToWrapper((int[]) value);
                }
                if (value instanceof Collection) {
                    return numbersToWrapperInts((Collection) value);
                }
                throw newTransformerException(Integer[].class, value);
            }
        });
        answer.put(Long[].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToWrapperLongArray((String) value);
                }
                if (value instanceof Number[]) {
                    return numbersToWrapperLongs((Number[]) value);
                }
                if (value instanceof long[]) {
                    return longArrayPrimitiveToWrapper((long[]) value);
                }
                if (value instanceof Collection) {
                    return numbersToWrapperLongs((Collection) value);
                }
                throw newTransformerException(Long[].class, value);
            }
        });
        answer.put(Short[].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToWrapperShortArray((String) value);
                }
                if (value instanceof Number[]) {
                    return numbersToWrapperShorts((Number[]) value);
                }
                if (value instanceof short[]) {
                    return shortArrayPrimitiveToWrapper((short[]) value);
                }
                if (value instanceof Collection) {
                    return numbersToWrapperShorts((Collection) value);
                }
                throw newTransformerException(Short[].class, value);
            }
        });

        answer.put(String[].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return ((String) value).split(",");
                }
                if (value instanceof String[]) {
                    return value;
                }
                if (value instanceof Collection) {
                    return collectionToStringArray((Collection<String>) value);
                }
                throw newTransformerException(String[].class, value);
            }
        });
    }

    protected void addOtherTransformers(Map<Class, EzyToObject> answer) {
        answer.put(Date.class, new EzyToObject<String>() {
            @Override
            public Object transform(String value) {
                try {
                    return EzyDates.parse(value);
                } catch (Exception e) {
                    logger.info("value: {} is invalid", value, e);
                }
                return null;
            }
        });

        answer.put(LocalDate.class, new EzyToObject<String>() {
            @Override
            public Object transform(String value) {
                try {
                    return EzyDates.parseDate(value, "yyyy-MM-dd");
                } catch (Exception e) {
                    logger.info("value: {} is invalid", value, e);
                }
                return null;
            }
        });

        answer.put(LocalDateTime.class, new EzyToObject<String>() {
            @Override
            public Object transform(String value) {
                try {
                    return EzyDates.parseDateTime(value);
                } catch (Exception e) {
                    logger.info("value: {} is invalid", value, e);
                }
                return null;
            }
        });

        //other
        answer.put(Class.class, new EzyToObject<String>() {
            @Override
            public Object transform(String value) {
                try {
                    return EzyClasses.getClass(value);
                } catch (Exception e) {
                    logger.info("value: {} is invalid", value, e);
                }
                return null;
            }
        });
    }

    protected void addTwoDimensionsPrimitiveArrayTransformers(Map<Class, EzyToObject> answer) {
        answer.put(boolean[][].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToPrimitiveBoolArrays((String) value);
                }
                if (value instanceof boolean[][]) {
                    return value;
                }
                if (value instanceof Boolean[][]) {
                    return boolArraysWrapperToPrimitive((Boolean[][]) value);
                }
                throw newTransformerException(boolean[][].class, value);
            }
        });
        answer.put(byte[][].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToPrimitiveByteArrays((String) value);
                }
                if (value instanceof byte[][]) {
                    return value;
                }
                if (value instanceof Number[][]) {
                    return numbersToPrimitiveByteArrays((Number[][]) value);
                }
                throw newTransformerException(byte[][].class, value);
            }
        });
        answer.put(char[][].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToPrimitiveCharArrays((String) value);
                }
                if (value instanceof char[][]) {
                    return value;
                }
                if (value instanceof Object[][]) {
                    return numbersToPrimitiveCharArrays((Object[][]) value);
                }
                throw newTransformerException(char[][].class, value);
            }
        });
        answer.put(double[][].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToPrimitiveDoubleArrays((String) value);
                }
                if (value instanceof double[][]) {
                    return value;
                }
                if (value instanceof Number[][]) {
                    return numbersToPrimitiveDoubleArrays((Number[][]) value);
                }
                throw newTransformerException(double[][].class, value);
            }
        });
        answer.put(float[][].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToPrimitiveFloatArrays((String) value);
                }
                if (value instanceof float[][]) {
                    return value;
                }
                if (value instanceof Number[][]) {
                    return numbersToPrimitiveFloatArrays((Number[][]) value);
                }
                throw newTransformerException(float[][].class, value);
            }
        });
        answer.put(int[][].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToPrimitiveIntArrays((String) value);
                }
                if (value instanceof int[][]) {
                    return value;
                }
                if (value instanceof Number[][]) {
                    return numbersToPrimitiveIntArrays((Number[][]) value);
                }
                throw newTransformerException(boolean[][].class, value);
            }
        });
        answer.put(long[][].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToPrimitiveLongArrays((String) value);
                }
                if (value instanceof long[][]) {
                    return value;
                }
                if (value instanceof Number[][]) {
                    return numbersToPrimitiveLongArrays((Number[][]) value);
                }
                throw newTransformerException(long[][].class, value);
            }
        });
        answer.put(short[][].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToPrimitiveShortArrays((String) value);
                }
                if (value instanceof short[][]) {
                    return value;
                }
                if (value instanceof Number[][]) {
                    return numbersToPrimitiveShortArrays((Number[][]) value);
                }
                throw newTransformerException(short[][].class, value);
            }
        });
    }

    protected void addTwoDimensionsWrapperArrayTransformers(Map<Class, EzyToObject> answer) {
        answer.put(Boolean[][].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToWrapperBoolArrays((String) value);
                }
                if (value instanceof Boolean[][]) {
                    return value;
                }
                if (value instanceof boolean[][]) {
                    return boolArraysPrimitiveToWrapper((boolean[][]) value);
                }
                throw newTransformerException(Boolean[][].class, value);
            }
        });
        answer.put(Byte[][].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToWrapperByteArrays((String) value);
                }
                if (value instanceof Number[][]) {
                    return numbersToWrapperByteArrays((Number[][]) value);
                }
                if (value instanceof byte[][]) {
                    return byteArraysPrimitiveToWrapper((byte[][]) value);
                }
                throw newTransformerException(Byte[][].class, value);
            }
        });
        answer.put(Character[][].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToWrapperCharArrays((String) value);
                }
                if (value instanceof Object[][]) {
                    return numbersToWrapperCharArrays((Number[][]) value);
                }
                if (value instanceof char[][]) {
                    return charArraysPrimitiveToWrapper((char[][]) value);
                }
                throw newTransformerException(Character[][].class, value);
            }
        });
        answer.put(Double[][].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToWrapperDoubleArrays((String) value);
                }
                if (value instanceof Number[][]) {
                    return numbersToWrapperDoubleArrays((Number[][]) value);
                }
                if (value instanceof double[][]) {
                    return doubleArraysPrimitiveToWrapper((double[][]) value);
                }
                throw newTransformerException(Double[][].class, value);
            }
        });
        answer.put(Float[][].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToWrapperFloatArrays((String) value);
                }
                if (value instanceof Number[][]) {
                    return numbersToWrapperFloatArrays((Number[][]) value);
                }
                if (value instanceof float[][]) {
                    return floatArraysPrimitiveToWrapper((float[][]) value);
                }
                throw newTransformerException(Float[][].class, value);
            }
        });
        answer.put(Integer[][].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToWrapperIntArrays((String) value);
                }
                if (value instanceof Number[][]) {
                    return numbersToWrapperIntArrays((Number[][]) value);
                }
                if (value instanceof int[][]) {
                    return intArraysPrimitiveToWrapper((int[][]) value);
                }
                throw newTransformerException(Integer[][].class, value);
            }
        });
        answer.put(Long[][].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToWrapperLongArrays((String) value);
                }
                if (value instanceof Number[][]) {
                    return numbersToWrapperLongArrays((Number[][]) value);
                }
                if (value instanceof long[][]) {
                    return longArraysPrimitiveToWrapper((long[][]) value);
                }
                throw newTransformerException(Long[][].class, value);
            }
        });
        answer.put(Short[][].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToWrapperShortArrays((String) value);
                }
                if (value instanceof Number[][]) {
                    return numbersToWrapperShortArrays((Number[][]) value);
                }
                if (value instanceof short[][]) {
                    return shortArraysPrimitiveToWrapper((short[][]) value);
                }
                throw newTransformerException(Short[][].class, value);
            }
        });
        answer.put(String[][].class, new EzyToObject<Object>() {
            @Override
            public Object transform(Object value) {
                if (value instanceof String) {
                    return stringToStringArrays((String) value);
                }
                if (value instanceof String[][]) {
                    return value;
                }
                throw newTransformerException(String[][].class, value);
            }
        });
    }

    private static final class EzySingletonValueConverter extends EzySimpleValueConverter {
        private static final EzySingletonValueConverter INSTANCE =
            new EzySingletonValueConverter();

        private EzySingletonValueConverter() {}

        public static EzySingletonValueConverter getInstance() {
            return INSTANCE;
        }
    }
}
