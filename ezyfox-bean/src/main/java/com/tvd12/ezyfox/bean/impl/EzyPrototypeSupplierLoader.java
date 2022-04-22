package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.bean.EzyPrototypeFactory;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;

public interface EzyPrototypeSupplierLoader {

    EzyPrototypeSupplier load(EzyPrototypeFactory factory);
}