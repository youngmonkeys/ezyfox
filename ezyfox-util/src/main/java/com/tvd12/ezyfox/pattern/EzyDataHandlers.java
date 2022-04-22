package com.tvd12.ezyfox.pattern;

@SuppressWarnings("rawtypes")
public interface EzyDataHandlers {

    EzyDataHandler getHandler(Object dataType);

    void addHandler(Object dataType, EzyDataHandler handler);
}