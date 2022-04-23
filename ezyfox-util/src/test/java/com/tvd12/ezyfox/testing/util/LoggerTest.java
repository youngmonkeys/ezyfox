package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.util.EzyLoggable;
import org.testng.annotations.Test;

import java.util.Date;

public class LoggerTest extends EzyLoggable {

    @Test
    public void test() {
        try {
            a();
        } catch (Exception e) {
            getLogger().info("error at: {}", new Date(), e);
        }
    }

    public void a() {
        throw new IllegalArgumentException("i'm exception");
    }
}
