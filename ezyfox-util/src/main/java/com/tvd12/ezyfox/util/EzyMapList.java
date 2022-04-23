package com.tvd12.ezyfox.util;

import java.util.List;

public interface EzyMapList<K,E> extends EzyMapCollection<K, E, List<E>> {

    List<E> getItems(K key);
}
