package com.tvd12.ezyfox.bean.v117.testing;

import com.tvd12.ezyfox.annotation.EzyImport;
import com.tvd12.ezyfox.annotation.EzyPackagesToScan;
import com.tvd12.ezyfox.bean.annotation.EzyPropertiesSources;
import com.tvd12.ezyfox.boot.DatabaseConfiguration;

@EzyImport(DatabaseConfiguration.class)
@EzyPropertiesSources("v117_config.properties")
@EzyPackagesToScan("com.tvd12.ezyfox.bean.v117.testing.packet1")
public class Configuration1 {}
