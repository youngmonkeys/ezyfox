package com.tvd12.ezyfox.binding;

import com.tvd12.ezyfox.reflect.EzyGenerics;

public interface EzyWriter<T,R> {

    R write(EzyMarshaller marshaller, T object);

    default Class<?> getObjectType() {
        try {
            Class<?> writerClass = getClass();
            Class<?>[] args = EzyGenerics.getGenericInterfacesArguments(writerClass, EzyWriter.class, 2);
            return args[0];
        }
        catch(Exception e) {
            return null;
        }
    }}