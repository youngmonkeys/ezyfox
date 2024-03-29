package com.tvd12.ezyfox.message.testing;

import com.tvd12.ezyfox.identifier.EzyIdSetter;
import com.tvd12.ezyfox.identifier.EzySimpleIdSetterImplementer;
import com.tvd12.ezyfox.message.EzyMessageIdSetters;
import com.tvd12.ezyfox.message.testing.entity.Message2;
import com.tvd12.ezyfox.message.testing.entity.Message3;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.util.Map;

public class EzyMessageIdSettersTest extends BaseTest {

    @Test
    public void test() {
        EzySimpleIdSetterImplementer.setDebug(true);
        Map<Class<?>, EzyIdSetter> idSetters = EzyMessageIdSetters.builder()
            .scan("com.tvd12.ezyfox.message.testing.entity")
            .addIdSetter(
                Message3.class,
                (object, id) -> ((Message3) object).setId((Long) id)
            )
            .build()
            .getIdSetters();
        EzyIdSetter idSetter = idSetters.get(Message2.class);
        Message2 msg = new Message2(100L, "hello");
        idSetter.setId(msg, 300L);
        assert msg.getId() == 300L;
    }
}
