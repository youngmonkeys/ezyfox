package com.tvd12.ezyfox.io;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.function.EzyToObject;
import com.tvd12.ezyfox.security.EzyBase64;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings({"rawtypes", "unchecked"})
public class EzySimpleCollectionConverter implements EzyCollectionConverter {
    private static final long serialVersionUID = -6609590648831856878L;

    protected final EzyOutputTransformer outputTransformer;
    protected final Map<Class, EzyToObject> converters = defaultConverters();

    public EzySimpleCollectionConverter(EzyOutputTransformer outputTransformer) {
        this.outputTransformer = outputTransformer;
    }

    protected <T> T convert(Object value, Class type) {
        EzyToObject converter = converters.get(type);
        if (converter != null) {
            return (T) converter.transform(value);
        }
        throw new IllegalArgumentException("has no converter with: " + type);
    }

    @Override
    public <T> T toArray(Collection coll, Class type) {
        return convert(coll, type);
    }

    protected <T> T toArray(Object array, Class type) {
        if (array instanceof EzyArray) {
            return toArray(((EzyArray) array).toList(), type.getComponentType());
        }
        if (array instanceof Collection) {
            return toArray((Collection) array, type.getComponentType());
        }
        Object answer = outputTransformer.transform(array, type);
        return (T) answer;
    }

    private <T> T[] toArray(Iterable iterable, T[] array) {
        int count = 0;
        Class arrayType = array.getClass().getComponentType();
        for (Object obj : iterable) {
            array[count++] = toArray(obj, arrayType);
        }
        return array;
    }

    private char[] toPrimitiveCharArray(Object value) {
        if (value instanceof byte[]) {
            return EzyDataConverter.byteArrayToCharArray((byte[]) value);
        } else if (value instanceof EzyArray) {
            return ((EzyArray) value).toArray(char.class);
        } else {
            return EzyNumbersConverter.numbersToPrimitiveChars((Collection) value);
        }
    }

    private Character[] toWrapperCharArray(Object value) {
        if (value instanceof byte[]) {
            return EzyDataConverter.toCharWrapperArray((byte[]) value);
        } else if (value instanceof EzyArray) {
            return ((EzyArray) value).toArray(Character.class);
        } else {
            return EzyNumbersConverter.numbersToWrapperChars((Collection) value);
        }
    }

    private byte[] toPrimitiveByteArray(Object value) {
        if (value instanceof byte[]) {
            return (byte[]) value;
        } else if (value instanceof EzyArray) {
            return ((EzyArray) value).toArray(byte.class);
        } else if (value instanceof String) {
            return EzyBase64.decode((String) value);
        }
        return EzyDataConverter.collectionToPrimitiveByteArray(
            (Collection<Byte>) value
        );
    }

    private Byte[] toWrapperByteArray(Object value) {
        if (value instanceof byte[]) {
            return EzyDataConverter.toByteWrapperArray((byte[]) value);
        } else if (value instanceof EzyArray) {
            return ((EzyArray) value).toArray(Byte.class);
        } else if (value instanceof String) {
            return EzyDataConverter.toByteWrapperArray(
                EzyBase64.decode((String) value)
            );
        }
        return EzyDataConverter.collectionToWrapperByteArray(
            (Collection<Byte>) value
        );
    }

    private Map<Class, EzyToObject> defaultConverters() {
        Map<Class, EzyToObject> converters = new ConcurrentHashMap<>();
        addDefaultConverters(converters);
        addCustomConverters(converters);
        return converters;
    }

    @SuppressWarnings("MethodLength")
    private void addDefaultConverters(Map<Class, EzyToObject> converters) {
        converters.put(
            boolean.class,
            (EzyToObject<Collection>) EzyDataConverter::collectionToPrimitiveBoolArray
        );
        converters.put(
            byte.class,
            (EzyToObject<Collection>) EzyNumbersConverter::numbersToPrimitiveBytes
        );
        converters.put(
            char.class,
            (EzyToObject<Collection>) EzyNumbersConverter::numbersToPrimitiveChars
        );
        converters.put(
            double.class,
            (EzyToObject<Collection>) EzyNumbersConverter::numbersToPrimitiveDoubles
        );
        converters.put(
            float.class,
            (EzyToObject<Collection>) EzyNumbersConverter::numbersToPrimitiveFloats
        );
        converters.put(
            int.class,
            (EzyToObject<Collection>) EzyNumbersConverter::numbersToPrimitiveInts
        );
        converters.put(
            long.class,
            (EzyToObject<Collection>) EzyNumbersConverter::numbersToPrimitiveLongs
        );
        converters.put(
            short.class,
            (EzyToObject<Collection>) EzyNumbersConverter::numbersToPrimitiveShorts
        );
        converters.put(
            String.class,
            (EzyToObject<Collection>) EzyDataConverter::collectionToStringArray
        );

        // wrapper
        converters.put(
            Boolean.class,
            (EzyToObject<Collection>) EzyDataConverter::collectionToWrapperBoolArray
        );
        converters.put(
            Byte.class,
            (EzyToObject<Collection>) EzyNumbersConverter::numbersToWrapperBytes
        );
        converters.put(
            Character.class,
            (EzyToObject<Collection>) EzyNumbersConverter::numbersToWrapperChars
        );
        converters.put(
            Double.class,
            (EzyToObject<Collection>) EzyNumbersConverter::numbersToWrapperDoubles
        );
        converters.put(
            Float.class,
            (EzyToObject<Collection>) EzyNumbersConverter::numbersToWrapperFloats
        );
        converters.put(
            Integer.class,
            (EzyToObject<Collection>) EzyNumbersConverter::numbersToWrapperInts
        );
        converters.put(
            Long.class,
            (EzyToObject<Collection>) EzyNumbersConverter::numbersToWrapperLongs
        );
        converters.put(
            Short.class,
            (EzyToObject<Collection>) EzyNumbersConverter::numbersToWrapperShorts
        );

        //entity
        converters.put(
            EzyObject.class,
            (EzyToObject<Collection>) input -> EzyCollections.toArray(input, EzyObject[]::new)
        );
    }

    //==============================
    @SuppressWarnings("MethodLength")
    private void addCustomConverters(Map<Class, EzyToObject> converters) {
        converters.put(
            boolean[].class,
            (EzyToObject<Collection>) input -> toArray(input, new boolean[input.size()][])
        );
        converters.put(byte[].class, (EzyToObject<Collection>) input -> {
            int count = 0;
            byte[][] answer = new byte[input.size()][];
            for (Object item : input) {
                answer[count++] = toPrimitiveByteArray(item);
            }
            return answer;
        });
        converters.put(char[].class, (EzyToObject<Collection>) input -> {
            int count = 0;
            char[][] answer = new char[input.size()][];
            for (Object item : input) {
                answer[count++] = toPrimitiveCharArray(item);
            }
            return answer;
        });
        converters.put(
            double[].class,
            (EzyToObject<Collection>) input -> toArray(input, new double[input.size()][])
        );
        converters.put(
            float[].class,
            (EzyToObject<Collection>) input -> toArray(input, new float[input.size()][])
        );
        converters.put(
            int[].class,
            (EzyToObject<Collection>) input -> toArray(input, new int[input.size()][])
        );
        converters.put(
            long[].class,
            (EzyToObject<Collection>) input -> toArray(input, new long[input.size()][])
        );
        converters.put(
            short[].class,
            (EzyToObject<Collection>) input -> toArray(input, new short[input.size()][])
        );
        converters.put(
            String[].class,
            (EzyToObject<Collection>) input -> toArray(input, new String[input.size()][])
        );

        // wrapper
        converters.put(
            Boolean[].class,
            (EzyToObject<Collection>) input -> toArray(input, new Boolean[input.size()][])
        );
        converters.put(Byte[].class, (EzyToObject<Collection>) input -> {
            int count = 0;
            Byte[][] answer = new Byte[input.size()][];
            for (Object item : input) {
                answer[count++] = toWrapperByteArray(item);
            }
            return answer;
        });
        converters.put(Character[].class, (EzyToObject<Collection>) input -> {
            int count = 0;
            Character[][] answer = new Character[input.size()][];
            for (Object item : input) {
                answer[count++] = toWrapperCharArray(item);
            }
            return answer;
        });
        converters.put(
            Double[].class,
            (EzyToObject<Collection>) input -> toArray(input, new Double[input.size()][])
        );
        converters.put(
            Float[].class,
            (EzyToObject<Collection>) input -> toArray(input, new Float[input.size()][])
        );
        converters.put(
            Integer[].class,
            (EzyToObject<Collection>) input -> toArray(input, new Integer[input.size()][])
        );
        converters.put(
            Long[].class,
            (EzyToObject<Collection>) input -> toArray(input, new Long[input.size()][])
        );
        converters.put(
            Short[].class,
            (EzyToObject<Collection>) input -> toArray(input, new Short[input.size()][])
        );

    }
}
