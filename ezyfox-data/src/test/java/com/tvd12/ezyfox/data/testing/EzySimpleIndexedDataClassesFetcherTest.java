package com.tvd12.ezyfox.data.testing;

import static org.testng.Assert.assertEquals;

import java.util.Set;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.data.EzyIndexedDataClassesFetcher;
import com.tvd12.ezyfox.data.EzySimpleIndexedDataClassesFetcher;
import com.tvd12.ezyfox.data.testing.index_data.ClassA;
import com.tvd12.ezyfox.data.testing.index_data.ClassB;
import com.tvd12.ezyfox.data.testing.index_data.ClassC;
import com.tvd12.ezyfox.data.testing.index_data.ClassD;
import com.tvd12.ezyfox.data.testing.index_data.ClassE;
import com.tvd12.ezyfox.data.testing.index_data1.ClassF;
import com.tvd12.test.base.BaseTest;

public class EzySimpleIndexedDataClassesFetcherTest extends BaseTest {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void test() {
        EzyIndexedDataClassesFetcher fetcher = new EzySimpleIndexedDataClassesFetcher()
                .scan(Sets.newHashSet())
                .scan(Sets.newHashSet("com.tvd12.ezyfox.data.testing.index_data"))
                .scan("com.tvd12.ezyfox.data.testing.index_data")
                .scan("com.tvd12.ezyfox.data.testing.index_data", "com.tvd12.ezyfox.data.testing.index_data1")
                .addIndexedDataClass(ClassC.class)
                .addIndexedDataClasses(ClassD.class, ClassE.class)
                .addIndexedDataClasses(Sets.newHashSet(ClassA.class, ClassE.class))
                .addIndexedDataClasses(new Object());
        Set<Class> classes = fetcher.getIndexedDataClasses();
        assertEquals(classes, Sets.newHashSet(
                ClassA.class, 
                ClassB.class, 
                ClassC.class, ClassD.class, ClassE.class, ClassF.class));
        fetcher = new EzySimpleIndexedDataClassesFetcher()
                .scan(Sets.newHashSet());
        classes = fetcher.getIndexedDataClasses();
        assert classes.isEmpty();
    }
    
}
