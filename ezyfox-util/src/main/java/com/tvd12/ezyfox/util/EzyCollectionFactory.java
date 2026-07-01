package com.tvd12.ezyfox.util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Supplier;

public class EzyCollectionFactory {

    @SuppressWarnings("rawtypes")
    protected final Map<Class, Supplier<Collection>> suppliers = defaultSuppliers();

    @SuppressWarnings({"rawtypes", "unchecked"})
    public <T extends Collection> T newCollection(Class collectionType) {
        if (suppliers.containsKey(collectionType)) {
            return (T) suppliers.get(collectionType).get();
        }
        throw new IllegalArgumentException("unknown implementation of " + collectionType);
    }

    @SuppressWarnings("rawtypes")
    private Map<Class, Supplier<Collection>> defaultSuppliers() {
        Map<Class, Supplier<Collection>> map = new ConcurrentHashMap<>();
        map.put(Collection.class, ArrayList::new);
        map.put(List.class, ArrayList::new);
        map.put(ArrayList.class, ArrayList::new);
        map.put(LinkedList.class, LinkedList::new);
        map.put(CopyOnWriteArrayList.class, CopyOnWriteArrayList::new);
        map.put(Set.class, HashSet::new);
        map.put(HashSet.class, HashSet::new);
        map.put(LinkedHashSet.class, LinkedHashSet::new);
        map.put(CopyOnWriteArraySet.class, CopyOnWriteArraySet::new);
        map.put(Vector.class, Vector::new);
        map.put(Queue.class, LinkedList::new);
        map.put(Stack.class, Stack::new);
        map.put(Deque.class, ArrayDeque::new);
        return map;
    }
}
