package com.tvd12.ezyfox.io;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

@SuppressWarnings({ "unchecked", "rawtypes" })
public final class EzyJsons {

	public static final Function TO_STRING_FUNC = t -> t.toString();
	public static final Function QUOTE_FUNC = t -> EzyStrings.quote(t);
	
    private EzyJsons() {
    }

    public static String parse(Collection items) {
        return parse(items, TO_STRING_FUNC);
    }

    public static <T> String parse(
    			Collection<T> items, Function<T, String> func) {
        StringBuilder builder = new StringBuilder()
            .append("[");
        int size = items.size();
        int index = 0;
        for (T item : items) {
            builder.append(func.apply(item));
            if ((++ index) < size)
                builder.append(",");
        }
        return builder.append("]").toString();
    }

	public static String parse(Map dict) {
        return parse(dict, TO_STRING_FUNC);
    }

    public static <K, V> String parse(
    			Map<K, V> dict, Function<V, String> valueFunction) {
        return parse(dict, QUOTE_FUNC, valueFunction);
    }

    public static <K, V> String parse(Map<K, V> dict,
    			Function<K, String> keyFunction,
    			Function<V, String> valueFunction) {
    		StringBuilder builder = new StringBuilder("{");
    		int size = dict.size();
        int index = 0;
        for (K key : dict.keySet()) {
        		V value = dict.get(key);
            builder.append(keyFunction.apply(key))
                   .append(":")
                   .append(valueFunction.apply(value));
            if ((++ index) < size)
                builder.append(",");
        }
        return builder.append("}").toString();
    }
}
