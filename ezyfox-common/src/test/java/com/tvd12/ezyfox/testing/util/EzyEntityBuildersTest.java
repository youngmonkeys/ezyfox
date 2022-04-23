package com.tvd12.ezyfox.testing.util;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.util.EzyEntityBuilders;
import com.tvd12.test.base.BaseTest;

public class EzyEntityBuildersTest extends BaseTest {

    @Test
    public void test() {
        XEntityBuilders bd = new XEntityBuilders();
        bd.newObjectBuilder();
        bd.newArray();
        bd.newObject();
    }

    public static class XEntityBuilders extends EzyEntityBuilders {
        @Override
        public EzyArray newArray() {
            return super.newArray();
        }

        @Override
        public EzyObject newObject() {
            return super.newObject();
        }

        @Override
        protected EzyObjectBuilder newObjectBuilder() {
            return super.newObjectBuilder();
        }
    }
}
