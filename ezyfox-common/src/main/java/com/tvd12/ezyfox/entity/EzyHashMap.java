package com.tvd12.ezyfox.entity;

import com.tvd12.ezyfox.exception.EzyObjectGetException;
import com.tvd12.ezyfox.io.EzyInputTransformer;
import com.tvd12.ezyfox.io.EzyOutputTransformer;
import com.tvd12.ezyfox.util.EzyObjectToMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiFunction;

@SuppressWarnings({"unchecked", "rawtypes"})
public class EzyHashMap extends EzyTransformable implements EzyObject {
    private static final long serialVersionUID = 2273868568933801751L;

    protected final HashMap<Object, Object> map = new HashMap<>();

    public EzyHashMap(
        EzyInputTransformer inputTransformer,
        EzyOutputTransformer outputTransformer
    ) {
        super(inputTransformer, outputTransformer);
    }

    public EzyHashMap(
        Map map,
        EzyInputTransformer inputTransformer,
        EzyOutputTransformer outputTransformer
    ) {
        this(inputTransformer, outputTransformer);
        this.map.putAll(map);
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyObject#put(java.lang.Object, java.lang.Object)
     */
    @Override
    public <V> V put(Object key, Object value) {
        return (V) map.put(key, transformInput(value));
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyObject#putAll(java.util.Map)
     */
    @Override
    public void putAll(Map m) {
        for (Object key : m.keySet()) {
            put(key, m.get(key));
        }
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyRoObject#get(java.lang.Object, java.lang.Class)
     */
    @Override
    public <V> V get(Object key, Class<V> type) {
        return (V) getValue(key, type);
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyRoObject#get(java.lang.Object)
     */
    @Override
    public <V> V get(Object key) {
        return (V) map.get(key);
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyRoObject#getValue(java.lang.Object, java.lang.Class)
     */
    @Override
    public Object getValue(Object key, Class type) {
        Object value = map.get(key);
        try {
            return transformOutput(value, type);
        } catch (Exception e) {
            throw new EzyObjectGetException(key, value, type, e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyObject#remove(java.lang.Object)
     */
    @Override
    public <V> V remove(Object key) {
        return (V) map.remove(key);
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyObject#removeAll(java.util.Collection)
     */
    @Override
    public void removeAll(Collection keys) {
        for (Object key : keys) {
            map.remove(key);
        }
    }

    /*
     *
     */
    @Override
    public <V> V compute(Object key, BiFunction func) {
        return (V) map.compute(key, func);
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyRoObject#size()
     */
    @Override
    public int size() {
        return map.size();
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyRoObject#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyRoObject#containsKey(java.lang.Object)
     */
    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyRoObject#containsKeys(java.util.Collection)
     */
    @Override
    public boolean containsKeys(Collection keys) {
        for (Object key : keys) {
            if (!map.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyRoObject#isNotNullKey(java.lang.Object)
     */
    @Override
    public boolean isNotNullValue(Object key) {
        return map.get(key) != null;
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyRoObject#keySet()
     */
    @Override
    public Set<Object> keySet() {
        return map.keySet();
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyRoObject#entrySet()
     */
    @Override
    public Set<Entry<Object, Object>> entrySet() {
        return map.entrySet();
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyObject#clear()
     */
    @Override
    public void clear() {
        map.clear();
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyRoObject#toMap()
     */
    @Override
    public Map toMap() {
        EzyObjectToMap objectToMap = EzyObjectToMap.getInstance();
        return objectToMap.toMap(this);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public Object clone() throws CloneNotSupportedException {
        return new EzyHashMap(
            map,
            inputTransformer,
            outputTransformer
        );
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyObject#duplicate()
     */
    @Override
    public EzyObject duplicate() {
        try {
            return (EzyObject) clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public int compareTo(EzyObject o) {
        EzyHashMap other = (EzyHashMap) o;
        int result = this.map.size() - other.map.size();
        if (result != 0) {
            return result;
        }
        for (Object key : map.keySet()) {
            Object value = map.get(key);
            Object otherValue = other.map.get(key);
            if (value == null) {
                if (otherValue != null) {
                    return -1;
                }
            } else {
                if (otherValue == null) {
                    return 1;
                }
                if (value instanceof Comparable && otherValue instanceof Comparable) {
                    result = ((Comparable) value).compareTo(otherValue);
                    if (result != 0) {
                        return result;
                    }
                } else {
                    if (!(value instanceof Comparable)) {
                        throw new IllegalArgumentException(
                            "value: " + value.getClass().getName() +
                                "(" + value + ") is not comparable"
                        );
                    } else {
                        throw new IllegalArgumentException(
                            "value: " + otherValue.getClass().getName() +
                                "(" + otherValue + ") is not comparable"
                        );
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!other.getClass().equals(this.getClass())) {
            return false;
        }
        EzyHashMap t = (EzyHashMap) other;
        return t.map.equals(this.map);
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    protected Object transformInput(Object input) {
        return inputTransformer.transform(input);
    }

    protected Object transformOutput(Object output, Class type) {
        return outputTransformer.transform(output, type);
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
