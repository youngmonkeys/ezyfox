package com.tvd12.ezyfox.binding.testing.scan.pack0;

import com.tvd12.ezyfox.binding.*;
import com.tvd12.ezyfox.binding.annotation.EzyBindingPackagesToScan;
import com.tvd12.ezyfox.binding.annotation.EzyConfiguration;
import com.tvd12.ezyfox.binding.testing.objectbinding.ClassD;
import com.tvd12.ezyfox.entity.EzyObject;
import lombok.Setter;

@EzyConfiguration
@EzyBindingPackagesToScan({"com.tvd12.ezyfox.binding.testing.scan.pack1"})
public class Configuration1 implements EzyBindingContextAware, EzyBindingConfig {

    @Setter
    private EzyBindingContext context;

    @Override
    public void config() {
        context.bindTemplate(
            ClassD.class,
            (EzyUnwrapper<EzyObject, ClassD>) (unmarshaller, input, output) -> {}
        );
    }
}
