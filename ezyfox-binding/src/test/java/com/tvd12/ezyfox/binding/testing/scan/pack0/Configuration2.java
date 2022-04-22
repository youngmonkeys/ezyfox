package com.tvd12.ezyfox.binding.testing.scan.pack0;

import com.tvd12.ezyfox.annotation.EzyPackagesToScan;
import com.tvd12.ezyfox.binding.EzyBindingConfig;
import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.EzyBindingContextAware;
import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyTemplate;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.annotation.EzyBindingPackagesToScan;
import com.tvd12.ezyfox.binding.annotation.EzyConfiguration;
import com.tvd12.ezyfox.binding.testing.scan.pack1.ClassC;
import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

import lombok.Setter;

@EzyConfiguration
@EzyPackagesToScan({"com.tvd12.ezyfox.binding.testing.scan.pack1"})
@EzyBindingPackagesToScan({"com.tvd12.ezyfox.binding.testing.scan.pack1"})
public class Configuration2 implements EzyBindingContextAware, EzyBindingConfig {

    @Setter
    private EzyBindingContext context;

    @Override
    public void config() {
        context.addTemplate(new EzyTemplate<EzyArray, ClassA>() {
            @Override
            public ClassA read(EzyUnmarshaller unmarshaller, EzyArray value) {
                return new ClassA();
            }

            @Override
            public EzyArray write(EzyMarshaller marshaller, ClassA object) {
                return EzyEntityFactory.create(EzyArrayBuilder.class).build();
            }
        });

        context.bindTemplate(ClassA.class, new EzyTemplate<EzyArray, ClassA>() {
            @Override
            public ClassA read(EzyUnmarshaller unmarshaller, EzyArray value) {
                return new ClassA();
            }

            @Override
            public EzyArray write(EzyMarshaller marshaller, ClassA object) {
                return EzyEntityFactory.create(EzyArrayBuilder.class).build();
            }
        });

        context.addTemplate(new Object());
        context.bindTemplate(ClassC.class, new Object());

    }

}
