package com.tvd12.ezyfox.io;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.function.EzyToObject;
import com.tvd12.ezyfox.reflect.EzyClasses;
import com.tvd12.ezyfox.sercurity.EzyBase64;
import com.tvd12.ezyfox.util.EzyLoggable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.tvd12.ezyfox.io.EzyDataConverter.*;

public class EzySimpleOutputTransformer
    extends EzyLoggable
    implements EzyOutputTransformer {
    private static final long serialVersionUID = 8067491929651725682L;

    @SuppressWarnings("rawtypes")
    protected final Map<Class, EzyToObject> transformers = defaultTransformers();

    @SuppressWarnings("rawtypes")
    @Override
    public Object transform(Object value, Class type) {
        return value == null
            ? transformNullValue()
            : transformNonNullValue(value, type);
    }

    protected Object transformNullValue() {
        return null;
    }

    @SuppressWarnings("rawtypes")
    protected Object transformNonNullValue(Object value, Class type) {
        return transformNonNullValue(value, type, transformers);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Object transformNonNullValue(
        Object value, Class type, Map<Class, EzyToObject> transformers) {
        EzyToObject trans = transformers.get(type);
        if (trans != null) {
            return trans.transform(value);
        }
        if (type.isEnum()) {
            return Enum.valueOf(type, value.toString());
        }
        return value;
    }

    //tank
    @SuppressWarnings("rawtypes")
    private Map<Class, EzyToObject> defaultTransformers() {
        Map<Class, EzyToObject> answer = new ConcurrentHashMap<>();
        addOtherTransformers(answer);
        addEntityTransformers(answer);
        addWrapperTransformers(answer);
        addPrimitiveTransformers(answer);
        addWrapperArrayTransformers(answer);
        addPrimitiveArrayTransformers(answer);
        addTwoDimensionsWrapperArrayTransformers(answer);
        addTwoDimensionsPrimitiveArrayTransformers(answer);
        return answer;
    }

    protected EzyObject[] toObjectArray(EzyArray value) {
        EzyObject[] answer = new EzyObject[value.size()];
        for (int i = 0; i < value.size(); ++i) {
            answer[i] = value.get(i, EzyObject.class);
        }
        return answer;
    }

    @SuppressWarnings("rawtypes")
    protected void addPrimitiveTransformers(Map<Class, EzyToObject> answer) {
        answer.put(boolean.class, (EzyToObject<Boolean>) value -> value);
        answer.put(byte.class, (EzyToObject<Number>) Number::byteValue);
        answer.put(char.class, (EzyToObject<Object>) EzyNumbersConverter::objectToChar);
        answer.put(double.class, (EzyToObject<Number>) Number::doubleValue);
        answer.put(float.class, (EzyToObject<Number>) Number::floatValue);
        answer.put(int.class, (EzyToObject<Number>) Number::intValue);
        answer.put(long.class, (EzyToObject<Number>) Number::longValue);
        answer.put(short.class, (EzyToObject<Number>) Number::shortValue);
    }

    @SuppressWarnings("rawtypes")
    protected void addWrapperTransformers(Map<Class, EzyToObject> answer) {
        answer.put(Boolean.class, (EzyToObject<Boolean>) value -> value);
        answer.put(Byte.class, (EzyToObject<Number>) Number::byteValue);
        answer.put(Character.class, (EzyToObject<Object>) EzyNumbersConverter::objectToChar);
        answer.put(Double.class, (EzyToObject<Number>) Number::doubleValue);
        answer.put(Float.class, (EzyToObject<Number>) Number::floatValue);
        answer.put(Integer.class, (EzyToObject<Number>) Number::intValue);
        answer.put(Long.class, (EzyToObject<Number>) Number::longValue);
        answer.put(Short.class, (EzyToObject<Number>) Number::shortValue);
        answer.put(String.class, (EzyToObject<String>) value -> value);
    }

    @SuppressWarnings({"rawtypes", "unchecked", "MethodLength"})
    protected void addPrimitiveArrayTransformers(Map<Class, EzyToObject> answer) {
        answer.put(boolean[].class, (EzyToObject<Object>) value -> {
            if (value instanceof Collection) {
                return collectionToPrimitiveBoolArray((Collection) value);
            }
            return ((EzyArray) value).toArray(boolean.class);
        });
        answer.put(byte[].class, (EzyToObject<Object>) value -> {
            if (value instanceof byte[]) {
                return value;
            }
            if (value instanceof String) {
                return EzyBase64.decode((String) value);
            }
            return ((EzyArray) value).toArray(byte.class);
        });
        answer.put(char[].class, (EzyToObject<Object>) value -> {
            if (value instanceof byte[]) {
                return byteArrayToCharArray((byte[]) value);
            }
            if (value instanceof String) {
                return ((String) value).toCharArray();
            }
            return ((EzyArray) value).toArray(char.class);
        });
        answer.put(double[].class, (EzyToObject<Object>) value -> {
            if (value instanceof Collection) {
                return collectionToPrimitiveDoubleArray((Collection) value);
            }
            return ((EzyArray) value).toArray(double.class);
        });
        answer.put(float[].class, (EzyToObject<Object>) value -> {
            if (value instanceof Collection) {
                return collectionToPrimitiveFloatArray((Collection) value);
            }
            return ((EzyArray) value).toArray(float.class);
        });
        answer.put(int[].class, (EzyToObject<Object>) value -> {
            if (value instanceof Collection) {
                return collectionToPrimitiveIntArray((Collection) value);
            }
            return ((EzyArray) value).toArray(int.class);
        });
        answer.put(long[].class, (EzyToObject<Object>) value -> {
            if (value instanceof Collection) {
                return collectionToPrimitiveLongArray((Collection) value);
            }
            return ((EzyArray) value).toArray(long.class);
        });
        answer.put(short[].class, (EzyToObject<Object>) value -> {
            if (value instanceof Collection) {
                return collectionToPrimitiveShortArray((Collection) value);
            }
            return ((EzyArray) value).toArray(short.class);
        });
        answer.put(String[].class, (EzyToObject<Object>) value -> {
            if (value instanceof Collection) {
                return collectionToStringArray((Collection) value);
            }
            return ((EzyArray) value).toArray(String.class);
        });
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected void addWrapperArrayTransformers(Map<Class, EzyToObject> answer) {
        answer.put(Boolean[].class, (EzyToObject<Object>) value -> {
            if (value instanceof Collection) {
                return collectionToWrapperBoolArray((Collection) value);
            }
            return ((EzyArray) value).toArray(Boolean.class);
        });
        answer.put(Byte[].class, (EzyToObject<Object>) value -> {
            if (value instanceof byte[]) {
                return toByteWrapperArray((byte[]) value);
            }
            return ((EzyArray) value).toArray(Byte.class);
        });
        answer.put(Character[].class, (EzyToObject<Object>) value -> {
            if (value instanceof byte[]) {
                return toCharWrapperArray((byte[]) value);
            }
            return ((EzyArray) value).toArray(Character.class);
        });
        answer.put(Double[].class, (EzyToObject<Object>) value -> {
            if (value instanceof Collection) {
                return collectionToWrapperDoubleArray((Collection) value);
            }
            return ((EzyArray) value).toArray(Double.class);
        });
        answer.put(Float[].class, (EzyToObject<Object>) value -> {
            if (value instanceof Collection) {
                return collectionToWrapperFloatArray((Collection) value);
            }
            return ((EzyArray) value).toArray(Float.class);
        });
        answer.put(Integer[].class, (EzyToObject<Object>) value -> {
            if (value instanceof Collection) {
                return collectionToWrapperIntArray((Collection) value);
            }
            return ((EzyArray) value).toArray(Integer.class);
        });
        answer.put(Long[].class, (EzyToObject<Object>) value -> {
            if (value instanceof Collection) {
                return collectionToWrapperLongArray((Collection) value);
            }
            return ((EzyArray) value).toArray(Long.class);
        });
        answer.put(Short[].class, (EzyToObject<Object>) value -> {
            if (value instanceof Collection) {
                return collectionToWrapperShortArray((Collection) value);
            }
            return ((EzyArray) value).toArray(Short.class);
        });
    }

    @SuppressWarnings({"rawtypes", "MethodLength"})
    protected void addOtherTransformers(Map<Class, EzyToObject> answer) {
        answer.put(Date.class, (EzyToObject<Object>) value -> {
            try {
                return EzyDates.parseToDateOrNull(value);
            } catch (Exception e) {
                logger.info("Date value: {} is invalid", value, e);
            }
            return null;
        });

        answer.put(Instant.class, (EzyToObject<Object>) value -> {
            try {
                return EzyDates.parseToInstantOrNull(value);
            } catch (Exception e) {
                logger.info("Date value: {} is invalid", value, e);
            }
            return null;
        });

        answer.put(LocalDate.class, (EzyToObject<Object>) value -> {
            try {
                return EzyDates.parseToLocalDateOrNull(value);
            } catch (Exception e) {
                logger.info("LocalDate value: {} is invalid", value, e);
            }
            return null;
        });

        answer.put(LocalTime.class, (EzyToObject<Object>) value -> {
            try {
                return EzyDates.parseToLocalTimeOrNull(value);
            } catch (Exception e) {
                logger.info("LocalTime value: {} is invalid", value, e);
            }
            return null;
        });

        answer.put(LocalDateTime.class, (EzyToObject<Object>) value -> {
            try {
                return EzyDates.parseToLocalDateTimeOrNull(value);
            } catch (Exception e) {
                logger.info("LocalDateTime value : {} is invalid", value, e);
            }
            return null;
        });

        //other
        answer.put(Class.class, (EzyToObject<String>) value -> {
            try {
                return EzyClasses.getClass(value);
            } catch (Exception e) {
                logger.info("Class value: {} is invalid", value, e);
            }
            return null;
        });

        answer.put(BigInteger.class, (EzyToObject<Object>) value -> {
            if (value instanceof BigInteger) {
                return value;
            }
            return new BigInteger((String) value);
        });

        answer.put(BigDecimal.class, (EzyToObject<Object>) value -> {
            if (value instanceof BigDecimal) {
                return value;
            }
            return new BigDecimal((String) value);
        });

        answer.put(UUID.class, (EzyToObject<Object>) value -> {
            if (value instanceof UUID) {
                return value;
            }
            return UUID.fromString((String) value);
        });
    }

    @SuppressWarnings("rawtypes")
    protected void addEntityTransformers(Map<Class, EzyToObject> answer) {
        answer.put(EzyObject[].class, (EzyToObject<EzyArray>) this::toObjectArray);

        answer.put(EzyObject[][].class, (EzyToObject<EzyArray>) value -> {
            EzyObject[][] answer1 = new EzyObject[value.size()][];
            for (int i = 0; i < value.size(); ++i) {
                answer1[i] = toObjectArray(value.get(i, EzyArray.class));
            }
            return answer1;
        });
    }

    @SuppressWarnings("rawtypes")
    protected void addTwoDimensionsPrimitiveArrayTransformers(Map<Class, EzyToObject> answer) {
        answer.put(boolean[][].class, (EzyToObject<EzyArray>) value -> value.toArray(boolean[].class));
        answer.put(byte[][].class, (EzyToObject<EzyArray>) value -> value.toArray(byte[].class));
        answer.put(char[][].class, (EzyToObject<EzyArray>) value -> value.toArray(char[].class));
        answer.put(double[][].class, (EzyToObject<EzyArray>) value -> value.toArray(double[].class));
        answer.put(float[][].class, (EzyToObject<EzyArray>) value -> value.toArray(float[].class));
        answer.put(int[][].class, (EzyToObject<EzyArray>) value -> value.toArray(int[].class));
        answer.put(long[][].class, (EzyToObject<EzyArray>) value -> value.toArray(long[].class));
        answer.put(short[][].class, (EzyToObject<EzyArray>) value -> value.toArray(short[].class));
    }

    @SuppressWarnings("rawtypes")
    protected void addTwoDimensionsWrapperArrayTransformers(Map<Class, EzyToObject> answer) {
        answer.put(Boolean[][].class, (EzyToObject<EzyArray>) value -> value.toArray(Boolean[].class));
        answer.put(Byte[][].class, (EzyToObject<EzyArray>) value -> value.toArray(Byte[].class));
        answer.put(Character[][].class, (EzyToObject<EzyArray>) value -> value.toArray(Character[].class));
        answer.put(Double[][].class, (EzyToObject<EzyArray>) value -> value.toArray(Double[].class));
        answer.put(Float[][].class, (EzyToObject<EzyArray>) value -> value.toArray(Float[].class));
        answer.put(Integer[][].class, (EzyToObject<EzyArray>) value -> value.toArray(Integer[].class));
        answer.put(Long[][].class, (EzyToObject<EzyArray>) value -> value.toArray(Long[].class));
        answer.put(Short[][].class, (EzyToObject<EzyArray>) value -> value.toArray(Short[].class));
        answer.put(String[][].class, (EzyToObject<EzyArray>) value -> value.toArray(String[].class));
    }
}
