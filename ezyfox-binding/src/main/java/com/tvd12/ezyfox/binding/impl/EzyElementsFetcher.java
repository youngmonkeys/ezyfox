package com.tvd12.ezyfox.binding.impl;

import com.tvd12.ezyfox.reflect.EzyClass;

import java.util.List;

public interface EzyElementsFetcher {

    List<Object> getElements(EzyClass clazz, int accessType);
}
