package com.tvd12.ezyfox.database.testing.util;

import com.tvd12.ezyfox.database.annotation.EzyCollection;
import com.tvd12.ezyfox.database.util.EzyCollectionAnnotations;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyCollectionAnnotationsTest extends BaseTest {

    @Test
    public void test() {
        assert EzyCollectionAnnotations.getCollectionName(A.class).equals("EntityA");
        assert EzyCollectionAnnotations.getCollectionName(B.class).equals("EntityB");
        assert EzyCollectionAnnotations.getCollectionName(C.class).equals("C");
        assert EzyCollectionAnnotations.getCollectionName(D.class).equals("D");
    }

    @Override
    public Class<?> getTestClass() {
        return EzyCollectionAnnotations.class;
    }

    @EzyCollection("EntityA")
    private static class A {}

    @EzyCollection(name = "EntityB")
    private static class B {}

    @EzyCollection
    private static class C {}

    private static class D {}
}
