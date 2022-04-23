package com.tvd12.ezyfox.binding;

import java.util.Collection;
import java.util.Map;

@SuppressWarnings("rawtypes")
public interface EzyUnmarshaller {

    /**
     * check contains unwrapper or not.
     *
     * @param objectType the object type
     * @return contains or not
     */
    boolean containsUnwrapper(Class objectType);

    /**
     * unwrap a value to pojo.
     *
     * @param value  the value
     * @param output the output type
     */
    void unwrap(Object value, Object output);

    /**
     * unmarshal a value to pojo.
     *
     * @param <T>     output type
     * @param value   the value
     * @param outType the pojo type
     * @return a pojo
     */
    <T> T unmarshal(Object value, Class<T> outType);

    /**
     * unmarshal value to object.
     *
     * @param <T>         output type
     * @param readerClass the reader class
     * @param value       the value
     * @return a object
     */
    <T> T unmarshal(Class<? extends EzyReader> readerClass, Object value);

    /**
     * unmarshal a value to collection.
     *
     * @param <T>            output item type
     * @param value          the value
     * @param collectionType the collection type
     * @param itemType       the item type
     * @return a collection
     */
    <T> Collection<T> unmarshalCollection(
        Object value,
        Class collectionType,
        Class<T> itemType
    );

    /**
     * unmarshal value to map.
     *
     * @param <K>       the key type
     * @param <V>       the value type
     * @param value     the value
     * @param mapType   the map type
     * @param keyType   the key type
     * @param valueType the value type
     * @return the map
     */
    <K, V> Map<K, V> unmarshalMap(
        Object value,
        Class mapType,
        Class<K> keyType,
        Class<V> valueType
    );
}
