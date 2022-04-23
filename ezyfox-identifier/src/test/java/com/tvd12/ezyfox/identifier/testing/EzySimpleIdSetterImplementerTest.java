package com.tvd12.ezyfox.identifier.testing;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.identifier.EzySimpleIdSetterImplementer;
import com.tvd12.ezyfox.identifier.testing.entity2.Message6;
import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.ezyfox.reflect.EzyReflectElement;
import com.tvd12.test.base.BaseTest;

public class EzySimpleIdSetterImplementerTest extends BaseTest {

    @Test
    public void test() {
        EzySimpleIdSetterImplementer implementer = new EzySimpleIdSetterImplementer(Message6.class) {
            @Override
            protected EzyReflectElement getIdElement() {
                try {
                    return new EzyField(Message6.class.getDeclaredField("id"));
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        assert implementer.implement() != null;
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void exceptionCaseTest() {
        EzySimpleIdSetterImplementer implementer = new EzySimpleIdSetterImplementer(Message6.class) {
            @Override
            protected String makeSetIdMethodContent(EzyMethod setIdMethod) {
                throw new IllegalStateException("i don't know");
            }
        };
        assert implementer.implement() != null;
    }
}
