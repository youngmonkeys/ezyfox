package com.tvd12.ezyfox.binding.impl;

import com.tvd12.ezyfox.binding.EzyWriter;
import com.tvd12.ezyfox.util.EzyEntityBuilders;

public abstract class EzyAbstractWriter<T,R> 
        extends EzyEntityBuilders
        implements EzyWriter<T, R> {}