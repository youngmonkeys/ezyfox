package com.tvd12.ezyfox.testing.util;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.util.EzyNext;
import com.tvd12.ezyfox.util.Next;

public class EzyNextTest {

    @Test
    public void test() {
        Next next = Next.fromSkipLimit(0, 10);
        assert next.getSkip() == 0;
        assert next.getLimit() == 10;
        next = Next.fromPageSize(-1, 10);
        assert next.getSkip() == 0;
        assert next.getLimit() == 10;
        next = Next.fromPageSize(0, 10);
        assert next.getSkip() == 0;
        assert next.getLimit() == 10;
        next = Next.fromPageSize(1, 10);
        assert next.getSkip() == 10;
        assert next.getLimit() == 10;

        next = EzyNext.fromSkipLimit(0, 10);
        assert next.getSkip() == 0;
        assert next.getLimit() == 10;
        next = EzyNext.fromPageSize(-1, 10);
        assert next.getSkip() == 0;
        assert next.getLimit() == 10;
        next = EzyNext.fromPageSize(0, 10);
        assert next.getSkip() == 0;
        assert next.getLimit() == 10;
        next = EzyNext.fromPageSize(1, 10);
        assert next.getSkip() == 10;
        assert next.getLimit() == 10;

        next = EzyNext.skipLimit(1, 10);
        assert next.getSkip() == 1;
        assert next.getLimit() == 10;
        
        next = EzyNext.fromLimit(10);
        assert next.getSkip() == 0;
        assert next.getLimit() == 10;
        
        next = EzyNext.limit(10);
        assert next.getSkip() == 0;
        assert next.getLimit() == 10;
        
        next = Next.skipLimit(1, 10);
        assert next.getSkip() == 1;
        assert next.getLimit() == 10;
        
        next = Next.fromLimit(10);
        assert next.getSkip() == 0;
        assert next.getLimit() == 10;
        
        next = Next.limit(10);
        assert next.getSkip() == 0;
        assert next.getLimit() == 10;
    }

}
