package com.tvd12.ezyfox.data.testing;

import com.tvd12.ezyfox.data.EzyIndexedDataIdFetchers;
import com.tvd12.ezyfox.data.testing.index_data.ClassA;
import com.tvd12.ezyfox.identifier.EzyIdFetchers;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyIndexedDataIdFetchersTest extends BaseTest {

    @Test
    public void test() {
        EzyIdFetchers fetchers = EzyIndexedDataIdFetchers.builder()
            .scan("com.tvd12.ezyfox.data.testing.index_data")
            .build();
        assert fetchers.getIdFetchers().size() == 3;
        assert fetchers.getIdFetcher(ClassA.class) != null;
    }
}
