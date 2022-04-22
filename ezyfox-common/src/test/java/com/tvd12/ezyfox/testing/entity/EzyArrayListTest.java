package com.tvd12.ezyfox.testing.entity;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyArrayList;
import com.tvd12.ezyfox.exception.EzyArrayGetException;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.io.EzyCollectionConverter;
import com.tvd12.ezyfox.io.EzyInputTransformer;
import com.tvd12.ezyfox.io.EzyOutputTransformer;
import com.tvd12.test.base.BaseTest;

public class EzyArrayListTest extends BaseTest {
    
    @Test
    public void test() {
        EzyArrayList arr = new EzyEzyArrayList(null, null, null);
        arr.add((Object)null);
        arr.add("a");
        assert arr.isNotNullValue(1);
        assert !arr.isNotNullValue(0);
        assert !arr.isNotNullValue(100);
        EzyArray newArray = EzyEntityFactory.newArray();
        assert newArray.first(10) == 10;
        assert newArray.first(int.class, 9) == 9;
        newArray.add(3);
        newArray.add(1);
        newArray.add(2);
        assert newArray.first(0) == 3;
        assert newArray.first(int.class, 0) == 3;
        newArray.sort((a, b) -> ((int)a) - ((int)b));
        assert newArray.first(1) == 1;
        assert newArray.get(1, int.class) == 2;
        assert newArray.get(2, int.class) == 3;
        
        EzyArray myArray = EzyEntityFactory.newArray();
        myArray.add("a");
        try {
            myArray.get(0, int.class);
        }
        catch (Exception e) {
            assert e instanceof EzyArrayGetException;
            EzyArrayGetException ex = (EzyArrayGetException)e;
            assert ex.getOutType() == int.class;
            assert ex.getIndex() == 0;
            assert ex.getValue().equals("a");
        }
    }

    public static class EzyEzyArrayList extends EzyArrayList {
        private static final long serialVersionUID = 1L;
        
        public EzyEzyArrayList(
                EzyInputTransformer inputTransformer,
                EzyOutputTransformer outputTransformer, 
                EzyCollectionConverter collectionConverter) {
            super(inputTransformer, outputTransformer, collectionConverter);
        }
        
        @Override
        public void add(Object item) {
            list.add(item);
        }
        
    }
    }