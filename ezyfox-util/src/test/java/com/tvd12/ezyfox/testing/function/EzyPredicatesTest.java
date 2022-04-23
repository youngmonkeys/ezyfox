package com.tvd12.ezyfox.testing.function;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.function.EzyPredicates;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.function.Predicate;

public class EzyPredicatesTest extends BaseTest {

    @Override
    public Class<?> getTestClass() {
        return EzyPredicates.class;
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test() {
        Predicate<String> p1 = t -> t.equals("hello");
        Predicate<String> p2 = t -> t.equals("world");
        List<Predicate<String>> p12and = Lists.newArrayList(p1, p2);
        Predicate<String> c1 = EzyPredicates.and(p12and);
        assert !c1.test("hello");
        assert !c1.test("hello world");
        Predicate<String> p3 = t -> t.contains("he");
        List<Predicate<String>> p13and = Lists.newArrayList(p1, p3);
        Predicate<String> c2 = EzyPredicates.and(p13and);
        assert c2.test("hello");
        Predicate<String> c3 = EzyPredicates.or(p1, p2);
        assert c3.test("hello");
        assert c3.test("world");
        assert !c3.test("hello world");
    }
}
