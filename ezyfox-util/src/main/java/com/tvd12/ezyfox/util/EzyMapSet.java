package com.tvd12.ezyfox.util;

import java.util.Set;

public interface EzyMapSet<K,E> extends EzyMapCollection<K, E, Set<E>> {

    Set<E> getItems(K key);
}
