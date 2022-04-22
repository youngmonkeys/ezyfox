package com.tvd12.ezyfox.identifier.testing;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.identifier.EzyIdSetter;
import com.tvd12.ezyfox.identifier.EzyIdSetters;
import com.tvd12.ezyfox.identifier.EzySimpleIdSetterImplementer;
import com.tvd12.ezyfox.identifier.EzySimpleIdSetters;
import com.tvd12.ezyfox.identifier.testing.annotation.HasIdTest;
import com.tvd12.ezyfox.identifier.testing.entity1.Message;
import com.tvd12.ezyfox.identifier.testing.entity1.Message2;
import com.tvd12.ezyfox.identifier.testing.entity1.Message3;
import com.tvd12.ezyfox.identifier.testing.entity1.Message4;
import com.tvd12.ezyfox.identifier.testing.entity1.Message5;
import com.tvd12.ezyfox.identifier.testing.entity2.Message6;
import com.tvd12.ezyfox.identifier.testing.entity2.NotMessage;
import com.tvd12.ezyfox.identifier.testing.entity3.Message7;
import com.tvd12.ezyfox.identifier.testing.entity3.Message8;
import com.tvd12.ezyfox.identifier.testing.entity3.Message9;
import com.tvd12.ezyfox.util.EzyMapBuilder;
import com.tvd12.test.base.BaseTest;

public class EzySimpleIdSettersTest extends BaseTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test() {
        EzySimpleIdSetterImplementer.setDebug(true);
        EzyIdSetters idSettersObject = new EzySimpleIdSetters.Builder() {
                protected Set<Class<? extends Annotation>> getAnnotationClasses() {
                    return Sets.newHashSet(HasIdTest.class);
                };
            }
            .scan("com.tvd12.ezyfox.identifier.testing.entity1")
            .scan("com.tvd12.ezyfox.identifier.testing.entity1", "com.tvd12.ezyfox.identifier.testing.entity2")
            .scan(Sets.newHashSet("com.tvd12.ezyfox.identifier.testing.entity2"))
            .addClass(Message.class)
            .addClass(Message2.class)
            .addClasses(Message3.class, Message4.class)
            .addClasses(Sets.newHashSet(Message5.class, Message6.class))
            .addIdSetter(Message3.class, (m3, id) -> ((Message3)m3).setId((Long)id))
            .addIdSetters(EzyMapBuilder.mapBuilder()
                    .put(Message6.class, new EzyIdSetter() {
                        @Override
                        public void setId(Object object, Object id) {
                            ((Message6)object).id = (Long) id;
                        }
                    })
                    .build())
            .build();
        Map<Class<?>, EzyIdSetter> idSetters = idSettersObject.getIdSetters();
        EzyIdSetter idSetter = idSetters.get(Message2.class);

        Message2 message2 = new Message2(100L, "hello");
        idSetter.setId(message2, 200L);
        assert message2.getId() == 200L;
        assert idSettersObject.getIdSetter(Message6.class) != null;
        try {
            idSettersObject.getIdSetter(NotMessage.class);
        }
        catch (Exception e) {
            assert e instanceof IllegalArgumentException;
        }

        idSetters = EzySimpleIdSetters.builder()
                .build()
                .getIdSetters();
        assert idSetters.isEmpty();
        idSetters = new EzySimpleIdSetters.Builder()
                .scan("com.tvd12.ezyfox.identifier.testing.entity2")
                .build()
                .getIdSetters();
        assert idSetters.size() == 0;

        try {
            idSettersObject = new EzySimpleIdSetters.Builder() {
                protected Set<Class<? extends Annotation>> getAnnotationClasses() {
                    return Sets.newHashSet(HasIdTest.class);
                };
            }
            .addClass(Message7.class)
            .build();
        }
        catch (Exception e) {
            assert e instanceof IllegalStateException;
        }

        try {
            idSettersObject = new EzySimpleIdSetters.Builder() {
                protected Set<Class<? extends Annotation>> getAnnotationClasses() {
                    return Sets.newHashSet(HasIdTest.class);
                };
            }
            .addClass(Message8.class)
            .build();
        }
        catch (Exception e) {
            assert e instanceof IllegalStateException;
        }
        EzySimpleIdSetterImplementer.setDebug(false);
        idSettersObject = new EzySimpleIdSetters.Builder() {
            protected Set<Class<? extends Annotation>> getAnnotationClasses() {
                return Sets.newHashSet(HasIdTest.class);
            };
        }
        .addClass(Message9.class)
        .build();
    }
}