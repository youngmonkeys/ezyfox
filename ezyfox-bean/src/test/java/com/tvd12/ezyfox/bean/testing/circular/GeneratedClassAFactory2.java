package com.tvd12.ezyfox.bean.testing.circular;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;
import com.tvd12.ezyfox.bean.impl.EzyByConstructorPrototypeSupplierLoader;
import com.tvd12.ezyfox.bean.impl.EzyPrototypeSupplierLoader;
import com.tvd12.ezyfox.bean.impl.EzySimpleBeanContext;
import com.tvd12.ezyfox.bean.impl.EzySimplePrototypeFactory;
import com.tvd12.ezyfox.reflect.EzyClass;

public class GeneratedClassAFactory2 {

    @Test(expectedExceptions = {IllegalStateException.class})
    public void test() throws Exception {
        EzyByConstructorPrototypeSupplierLoader.setDebug(true);
        
        EzyPrototypeSupplierLoader builder = 
                new EzyByConstructorPrototypeSupplierLoader("classA", new EzyClass(ClassA.class));
        
        EzyPrototypeSupplier supplier = builder.load(new EzySimplePrototypeFactory());
        
        EzyBeanContext context = EzySimpleBeanContext.builder()
                .scan("com.tvd12.ezyfox.bean.testing.circular")
                .build();
        System.out.println(supplier.getObjectType());
        supplier.supply(context);
    }
    
    @Test
    public void test1() throws Exception {
        try {
            EzyByConstructorPrototypeSupplierLoader.setDebug(true);
            
            EzyPrototypeSupplierLoader builder = 
                    new EzyByConstructorPrototypeSupplierLoader("classA", new EzyClass(ClassA.class));
            
            EzyPrototypeSupplier supplier = builder.load(new EzySimplePrototypeFactory());
            
            EzyBeanContext context = EzySimpleBeanContext.builder()
                    .scan("com.tvd12.ezyfox.bean.testing.circular")
                    .build();
            System.out.println(supplier.getObjectType());
            supplier.supply(context);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
}
