package com.tvd12.ezyfox.util;

import java.util.Properties;

import com.tvd12.properties.file.reader.MultiFileReader;
import com.tvd12.properties.file.util.PropertiesUtil;

@SuppressWarnings("unchecked")
public abstract class EzyPropertiesKeeper<P extends EzyPropertiesKeeper<P>>
		extends EzyLoggable {

	protected final Properties properties = new Properties();
	
	public P properties(Properties properties) {
		this.properties.putAll(properties);
		return (P)this;
	}
	
	public P properties(Properties properties, String keyPrefix) {
		return properties(properties, keyPrefix, false);
	}
	
	public P properties(Properties properties, String keyPrefix, boolean keepKeyPrefix) {
		this.properties.putAll(
			keepKeyPrefix
			? PropertiesUtil.filterPropertiesByKeyPrefix(properties, keyPrefix)
			: PropertiesUtil.getPropertiesByPrefix(properties, keyPrefix)
		);
		return (P)this;
	}

	public P property(String name, Object value) {
		this.properties.put(name, value);
		return (P)this;
	}

	public P propertiesFile(String filePath) {
		return propertiesFile(filePath, null);
	}
	
	public P propertiesFile(String filePath, String profiles) {
		this.properties.putAll(loadProperties(filePath, profiles));
		return (P)this;
	}
	
	private Properties loadProperties(String filePath, String profiles) {
		return new MultiFileReader(profiles).read(filePath);
	}
	
}
