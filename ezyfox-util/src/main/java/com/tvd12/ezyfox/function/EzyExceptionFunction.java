package com.tvd12.ezyfox.function;

public interface EzyExceptionFunction<T,R> {

    R apply(T t) throws Exception;

}
