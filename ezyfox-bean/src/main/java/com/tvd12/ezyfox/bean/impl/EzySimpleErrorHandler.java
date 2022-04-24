package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.bean.EzyErrorHandler;
import com.tvd12.ezyfox.util.EzyLoggable;

public class EzySimpleErrorHandler
    extends EzyLoggable
    implements EzyErrorHandler {

    @Override
    public void handle(Throwable error) {
        logger.warn("error", error);
    }
}
