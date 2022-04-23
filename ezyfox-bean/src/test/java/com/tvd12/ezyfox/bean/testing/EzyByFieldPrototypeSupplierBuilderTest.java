package com.tvd12.ezyfox.bean.testing;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.bean.impl.EzyByFieldPrototypeSupplierLoader;
import com.tvd12.ezyfox.bean.impl.EzySimplePrototypeFactory;
import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzyFields;

public class EzyByFieldPrototypeSupplierBuilderTest {

    public ClassA classA;

    @Test
    public void test() throws Exception {
        EzyByFieldPrototypeSupplierLoader.setDebug(true);

        EzyField field = new EzyField(EzyFields.getField(getClass(), "classA"));
        EzyByFieldPrototypeSupplierLoader builder = new EzyByFieldPrototypeSupplierLoader(
                "classA", field, this);
        builder.load(new EzySimplePrototypeFactory());
    }

    public static class ClassA {

    }
}
