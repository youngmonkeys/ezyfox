package com.tvd12.ezyfox.data;

import java.util.Set;

@SuppressWarnings("rawtypes")
public interface EzyIndexedDataClassesFetcher {

    EzyIndexedDataClassesFetcher scan(String packageName);

    EzyIndexedDataClassesFetcher scan(String... packageNames);

    EzyIndexedDataClassesFetcher scan(Iterable<String> packageNames);

    EzyIndexedDataClassesFetcher addIndexedDataClass(Class clazz);

    EzyIndexedDataClassesFetcher addIndexedDataClasses(Class... classes);

    EzyIndexedDataClassesFetcher addIndexedDataClasses(Iterable<Class> classes);

    EzyIndexedDataClassesFetcher addIndexedDataClasses(Object reflection);

    Set<Class> getIndexedDataClasses();
}
