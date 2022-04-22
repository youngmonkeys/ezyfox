package com.tvd12.ezyfox.data;

import java.lang.annotation.Annotation;
import java.util.Set;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.data.annotation.EzyIndexedData;
import com.tvd12.ezyfox.data.annotation.IndexedData;

@SuppressWarnings("rawtypes")
public class EzySimpleIndexedDataClassesFetcher
        extends EzyDataClassesFetcher<EzyIndexedDataClassesFetcher>
        implements EzyIndexedDataClassesFetcher {

    @Override
    public EzyIndexedDataClassesFetcher addIndexedDataClass(Class clazz) {
        return super.addDataClass(clazz);
    }

    @Override
    public EzyIndexedDataClassesFetcher addIndexedDataClasses(Class... classes) {
        return super.addDataClasses(classes);
    }

    @Override
    public EzyIndexedDataClassesFetcher addIndexedDataClasses(Iterable<Class> classes) {
        return super.addDataClasses(classes);
    }

    @Override
    public EzyIndexedDataClassesFetcher addIndexedDataClasses(Object reflection) {
        return super.addDataClasses(reflection);
    }

    @Override
    public Set<Class> getIndexedDataClasses() {
        return super.getDataClasses();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Set<Class<? extends Annotation>> getAnnotationClasses() {
        return Sets.newHashSet(EzyIndexedData.class, IndexedData.class);
    }

}
