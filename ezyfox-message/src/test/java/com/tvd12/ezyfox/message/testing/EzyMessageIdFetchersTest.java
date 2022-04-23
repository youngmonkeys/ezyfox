package com.tvd12.ezyfox.message.testing;

import java.util.Map;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.identifier.EzyIdFetcher;
import com.tvd12.ezyfox.identifier.EzySimpleIdFetcherImplementer;
import com.tvd12.ezyfox.message.EzyMessageIdFetchers;
import com.tvd12.ezyfox.message.testing.entity.Message2;
import com.tvd12.ezyfox.message.testing.entity.Message3;
import com.tvd12.test.base.BaseTest;

public class EzyMessageIdFetchersTest extends BaseTest {

    @Test
    public void test() {
        EzySimpleIdFetcherImplementer.setDebug(true);
        Map<Class<?>, EzyIdFetcher> idFetchers = EzyMessageIdFetchers.builder()
            .scan("com.tvd12.ezyfox.message.testing.entity")
            .addIdFetcher(Message3.class, m3 -> ((Message3)m3).getId())
            .build()
            .getIdFetchers();
        EzyIdFetcher idFetcher = idFetchers.get(Message2.class);
        assert idFetcher.getId(new Message2(100L, "hello")).equals(100L);
    }
}
