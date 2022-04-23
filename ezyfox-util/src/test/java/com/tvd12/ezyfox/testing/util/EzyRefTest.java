package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.util.EzyRef;
import org.testng.annotations.Test;

public class EzyRefTest {

    @Test
    public void test() {
        EzyRef<String> ref = new EzyRef<>("1");
        assert ref.get().equals("1");
        ref.retain();
        assert !ref.isReleasable();
        ref.release();
        assert ref.isReleasable();
    }
}
