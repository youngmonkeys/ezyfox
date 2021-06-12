package com.tvd12.ezyfox.binding.testing.scan.pack0;

import com.tvd12.ezyfox.binding.EzyBindingConfig;
import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.EzyBindingContextAware;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.EzyUnwrapper;
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
		context.bindTemplate(ClassD.class, new EzyUnwrapper<EzyObject, ClassD>() {
			@Override
			public void unwrap(EzyUnmarshaller unmarshaller, EzyObject input, ClassD output) {
			}
		});
	}
	
	
	
}
