package com.tvd12.ezyfox.data;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tvd12.ezyfox.reflect.EzyReflection;
import com.tvd12.ezyfox.reflect.EzyReflectionProxy;
import com.tvd12.ezyfox.util.EzyLoggable;

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class EzyDataClassesFetcher<T> extends EzyLoggable {

    protected final Set<Class> classes;
    protected final Set<String> packagesToScan;
    protected final Set<Class<? extends Annotation>> annotationClasses;

    protected EzyDataClassesFetcher() {
        this.classes = new HashSet<>();
        this.packagesToScan = new HashSet<>();
        this.annotationClasses = getAnnotationClasses();
    }

    public T scan(String packageName) {
        packagesToScan.add(packageName);
        return (T)this;
    }

    public T scan(String... packageNames) {
        return scan(Arrays.asList(packageNames));
    }

    public T scan(Iterable<String> packageNames) {
        for(String packageName : packageNames)
            this.scan(packageName);
        return (T)this;
    }

    public T addDataClass(Class clazz) {
        this.classes.add(clazz);
        return (T)this;
    }

    public T addDataClasses(Class... classes) {
        return addDataClasses(Arrays.asList(classes));
    }

    public T addDataClasses(Iterable<Class> classes) {
        for(Class clazz : classes)
            this.addDataClass(clazz);
        return (T)this;
    }

    public T addDataClasses(Object reflection) {
        if(reflection instanceof EzyReflection) {
            EzyReflection ref = (EzyReflection)reflection;
            Set<Class<?>> annClasses = ref.getAnnotatedClasses(annotationClasses);
            this.classes.addAll(annClasses);
        }
        return (T)this;
    }

    public Set<Class> getDataClasses() {
        addAnnotatedClasses();
        return classes;
    }

    private void addAnnotatedClasses() {
        if(packagesToScan.isEmpty())
            return;
        EzyReflection reflection = new EzyReflectionProxy(packagesToScan);
        addDataClasses(reflection);
    }

    protected abstract Set<Class<? extends Annotation>> getAnnotationClasses();
}