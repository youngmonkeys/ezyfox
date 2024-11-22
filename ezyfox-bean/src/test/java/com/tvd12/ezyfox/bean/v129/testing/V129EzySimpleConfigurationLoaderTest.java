package com.tvd12.ezyfox.bean.v129.testing;

import com.tvd12.ezyfox.bean.EzySingletonFactory;
import com.tvd12.ezyfox.bean.impl.EzyBeanKey;
import com.tvd12.ezyfox.bean.impl.EzySimpleConfigurationLoader;
import com.tvd12.ezyfox.bean.v129.testing.config.V129BeanConfig;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.ezyfox.util.EzyMapBuilder;
import com.tvd12.test.reflect.FieldUtil;
import com.tvd12.test.reflect.MethodInvoker;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

public class V129EzySimpleConfigurationLoaderTest {

    @Test
    public void addSingletonByMethodsMethodIsNullTest() throws Exception {
        // given
        EzySimpleConfigurationLoader instance = new EzySimpleConfigurationLoader();
        EzySingletonFactory singletonFactory = mock(EzySingletonFactory.class);
        when(
            singletonFactory.getSingleton(any(EzyBeanKey.class))
        ).thenReturn("foo");
        FieldUtil.setFieldValue(instance, "singletonFactory", singletonFactory);

        FieldUtil.setFieldValue(
            instance,
            "singletonMethodByKey",
            EzyMapBuilder.mapBuilder()
                .put(
                    EzyBeanKey.of("foo", String.class),
                    new EzyMethod(
                        V129BeanConfig.class.getDeclaredMethod("foo")
                    )
                )
                .put(
                    EzyBeanKey.of("bar", String.class),
                    null
                )
                .toMap()
        );
        V129BeanConfig config = new V129BeanConfig();

        // when
        MethodInvoker.create()
            .object(instance)
            .method("addSingletonByMethods")
            .param(Object.class, config)
            .invoke();

        // then
        verify(singletonFactory, times(1)).getSingleton(
            any(EzyBeanKey.class)
        );
        verifyNoMoreInteractions(singletonFactory);
    }
}
