package com.tvd12.ezyfox.monitor.test;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.monitor.frame.EzyActionFrame;
import com.tvd12.test.assertion.Asserts;

public class EzyActionFrameTest {

    @Test
    public void testWithExpired() {
        // given
        EzyActionFrame sut = new EzyActionFrame(1, System.currentTimeMillis() - 1000) {
            @Override
            public EzyActionFrame nextFrame() {
                return null;
            }
            @Override
            protected int getExistsTime() {
                return 0;
            }
        };
        
        // when
        // then
        Asserts.assertTrue(sut.isExpired());
    }
}
