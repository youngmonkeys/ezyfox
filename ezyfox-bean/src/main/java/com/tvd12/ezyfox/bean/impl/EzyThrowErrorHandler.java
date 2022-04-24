package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.bean.EzyErrorHandler;
import com.tvd12.ezyfox.util.EzyLoggable;

public class EzyThrowErrorHandler
    extends EzyLoggable
    implements EzyErrorHandler {

    @Override
    public void handle(Throwable error) {
        throw new IllegalStateException(error);
    }
}
