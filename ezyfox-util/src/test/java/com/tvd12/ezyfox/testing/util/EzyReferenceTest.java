package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.util.EzyReference;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyReferenceTest extends BaseTest {

    @SuppressWarnings("ALL")
    @Test
    public void test() {
        assert (new EzyReference() {

            @Override
            public void retain() {
            }

            @Override
            public void release() {
            }

            @Override
            public int getReferenceCount() {
                return 0;
            }
        }.releasable());

        assert (new EzyReference() {

            @Override
            public void retain() {
            }

            @Override
            public void release() {
            }

            @Override
            public int getReferenceCount() {
                return 1;
            }
        }.releasable()) == false;
    }
}
