package com.tvd12.ezyfox.binding.impl;

import java.util.List;

import com.tvd12.ezyfox.reflect.EzyClass;

public interface EzyElementsFetcher {

    List<Object> getElements(EzyClass clazz, int accessType);
}
