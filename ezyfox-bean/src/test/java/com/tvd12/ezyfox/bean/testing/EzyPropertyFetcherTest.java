package com.tvd12.ezyfox.bean.testing;

import com.tvd12.ezyfox.bean.EzyPropertyFetcher;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.util.RandomUtil;
import org.testng.annotations.Test;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class EzyPropertyFetcherTest {

    @Test
    public void testNotDefault() {
        // given
        Object key = RandomUtil.randomShortAlphabetString();
        Long result = RandomUtil.randomLong();
        EzyPropertyFetcher sut = spy(A.class);
        when(sut.getProperty(key, Long.class)).thenReturn(result);

        // when

        Long actual = sut.getProperty(key, Long.class, 0L);

        // then
        Asserts.assertEquals(result, actual);
    }

    @Test
    public void testDefault() {
        // given
        Object key = RandomUtil.randomShortAlphabetString();
        EzyPropertyFetcher sut = spy(A.class);
        when(sut.getProperty(key, Long.class)).thenReturn(null);

        // when

        Long actual = sut.getProperty(key, Long.class, 0L);

        // then
        Asserts.assertEquals(0L, actual);
    }

    public static abstract class A implements EzyPropertyFetcher {}
}
