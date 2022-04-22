package com.tvd12.ezyfox.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EzyLoggable {

    protected transient final Logger logger
            = LoggerFactory.getLogger(getClass());

    protected Logger getLogger() {
        return logger;
    }

}
