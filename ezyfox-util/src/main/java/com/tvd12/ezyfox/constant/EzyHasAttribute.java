package com.tvd12.ezyfox.constant;

public interface EzyHasAttribute {

    <T> T getAttribute(EzyAttribute<T> attr);
}