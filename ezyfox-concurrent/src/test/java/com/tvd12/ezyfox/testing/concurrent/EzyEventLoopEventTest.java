package com.tvd12.ezyfox.testing.concurrent;

import com.tvd12.ezyfox.concurrent.EzyEventLoopEvent;
import org.testng.annotations.Test;

public class EzyEventLoopEventTest {

    @Test
    public void onFinishTest() {
        // given
        EzyEventLoopEvent instance = () -> false;

        // when
        // then
        instance.onFinished();
    }
}
