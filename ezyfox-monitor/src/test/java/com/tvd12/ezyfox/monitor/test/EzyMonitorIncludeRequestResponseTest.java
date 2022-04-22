package com.tvd12.ezyfox.monitor.test;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.monitor.EzyMonitorIncludeRequestResponse;
import com.tvd12.test.assertion.Asserts;

public class EzyMonitorIncludeRequestResponseTest {

    @Test
    public void test() throws Exception {
        // given
        EzyMonitorIncludeRequestResponse sut = new EzyMonitorIncludeRequestResponse();
        
        // when
        sut.increaseRequestCount();
        sut.increaseResponseCount();
        
        // then
        Asserts.assertEquals(sut.getRequestCount(), 1L);
        Asserts.assertEquals(sut.getRequestPerSecond(), 1L);
        Asserts.assertEquals(sut.getResponsePerSecond(), 1L);
        Thread.sleep(1001);
        Asserts.assertEquals(sut.getRequestPerSecond(), 0L);
        Asserts.assertEquals(sut.getResponsePerSecond(), 0L);
        
        sut.increaseRequestCount();
        sut.increaseResponseCount();
        Asserts.assertEquals(sut.getRequestPerSecond(), 1L);
        Asserts.assertEquals(sut.getResponsePerSecond(), 1L);
    }}