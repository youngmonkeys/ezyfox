package com.tvd12.ezyfox.monitor.test;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.monitor.frame.EzyActionFrame;
import com.tvd12.ezyfox.monitor.frame.EzyActionFrameSecond;
import com.tvd12.test.assertion.Asserts;

public class EzyActionFrameSecondTest {

    @Test
    public void test() {
        // given
        EzyActionFrameSecond sut = new EzyActionFrameSecond(1);
        
        // when
        // then
        Asserts.assertFalse(sut.addActions(1));
        Asserts.assertFalse(sut.isExpired());
        Asserts.assertFalse(sut.isInvalid());
        Asserts.assertTrue(sut.addActions(10));
        System.out.println(sut);
    }
    
    @Test
    public void testGetters() {
        // given
        long now = System.currentTimeMillis();
        EzyActionFrame sut = new EzyActionFrameSecond(1, now);
        
        // when
        // then
        Asserts.assertFalse(sut.addActions(1));
        Asserts.assertFalse(sut.isExpired());
        Asserts.assertFalse(sut.isInvalid());
        Asserts.assertTrue(sut.addActions(10));
        Asserts.assertTrue(sut.isInvalid());
        Asserts.assertEquals(sut.getActions(), 11L);
        Asserts.assertEquals(sut.getStartTime(), now);
        Asserts.assertEquals(sut.getEndTime(), now + 1000);
        Asserts.assertEquals(sut.getMaxActions(), 1L);
    }
    
    @Test
    public void testWithNextFrame() {
        // given
        long now = System.currentTimeMillis();
        EzyActionFrame sut = new EzyActionFrameSecond(1, now);
        sut = sut.nextFrame();
        
        // when
        // then
        Asserts.assertFalse(sut.addActions(1));
        Asserts.assertFalse(sut.isExpired());
        Asserts.assertFalse(sut.isInvalid());
        Asserts.assertTrue(sut.addActions(10));
        Asserts.assertTrue(sut.isInvalid());
        Asserts.assertEquals(sut.getMaxActions(), 1L);
    }
    
    @Test
    public void testWithDefault() {
        // given
        EzyActionFrame sut = new EzyActionFrameSecond();
        
        // when
        // then
        Asserts.assertFalse(sut.addActions(1));
        Asserts.assertFalse(sut.isExpired());
        Asserts.assertFalse(sut.isInvalid());
        Asserts.assertFalse(sut.addActions(10));
    }
}
