package com.tvd12.ezyfox.identifier;

import java.util.Map;

public interface EzyIdFetchers {

    EzyIdFetcher getIdFetcher(Class<?> clazz);

    Map<Class<?>, EzyIdFetcher> getIdFetchers();

}
