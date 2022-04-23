package com.tvd12.ezyfox.testing.collect;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class ListsTest extends BaseTest {

    @Override
    public Class<?> getTestClass() {
        return Lists.class;
    }

    @Test
    public void test() {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        assert Lists.toList(list1) == list1;
        assert Lists.toList(Sets.newHashSet(1, 2, 3)).size() == 3;
        assert Lists.tryNewArrayList(list1).equals(list1);
        assert Lists.tryNewArrayList(Sets.newHashSet(1, 2, 3)).equals(list1);
    }
}
