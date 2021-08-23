package com.tvd12.ezyfox.bean.v117.testing;

import java.util.Properties;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.boot.DatabaseContext;
import com.tvd12.test.assertion.Asserts;

public class EzySimpleBindingTest {

	@Test
	public void defaultTest() {
		// given
		EzyBeanContext beanContext = EzyBeanContext.builder()
				.build();
		
		// when
		DatabaseContext databaseContext = 
				(DatabaseContext) beanContext.getBean(DatabaseContext.class);

		// then
		Asserts.assertEquals(0, beanContext.getPackagesToScan().size());
		Asserts.assertEquals(0, databaseContext.getPackagesToScan().size());
	}
	
	@Test
	public void scanPackages() {
		// given
		EzyBeanContext beanContext = EzyBeanContext.builder()
				.scan("com.tvd12.ezyfox.bean.v117.testing")
				.build();
		
		// when
		DatabaseContext databaseContext = 
				(DatabaseContext) beanContext.getBean(DatabaseContext.class);
		
		// then
		Asserts.assertEquals(3, beanContext.getPackagesToScan().size());
		Asserts.assertEquals(beanContext.getPackagesToScan(), databaseContext.getPackagesToScan());
	}
	
	@Test
	public void disableAutoConfig() {
		// given
		EzyBeanContext beanContext = EzyBeanContext.builder()
				.disableAutoConfiguration()
				.build();
		
		// when
		DatabaseContext databaseContext = 
				(DatabaseContext) beanContext.getSingleton(DatabaseContext.class);
		
		// then
		Asserts.assertEquals(0, beanContext.getPackagesToScan().size());
		Asserts.assertNull(databaseContext);
	}
	
	@Test
	public void disableAutoConfigByAnnotation() {
		// given
		EzyBeanContext beanContext = EzyBeanContext.builder()
				.scan("com.tvd12.ezyfox.bean.v117x.testing.packet3")
				.build();
		
		// when
		DatabaseContext databaseContext = 
				(DatabaseContext) beanContext.getSingleton(DatabaseContext.class);
		
		// then
		Asserts.assertEquals(1, beanContext.getPackagesToScan().size());
		Asserts.assertNull(databaseContext);
	}
	
	@Test
	public void importConfigs() {
		// given
		EzyBeanContext beanContext = EzyBeanContext.builder()
				.scan("com.tvd12.ezyfox.bean.v117.testing")
				.scan("com.tvd12.ezyfox.bean.v117x.testing.packet3")
				.build();
		
		// when
		DatabaseContext databaseContext = 
				(DatabaseContext) beanContext.getSingleton(DatabaseContext.class);
		
		// then
		Asserts.assertEquals(4, beanContext.getPackagesToScan().size());
		Asserts.assertEquals(beanContext.getPackagesToScan(), databaseContext.getPackagesToScan());
	}
	
	@Test
	public void loadPropertiesSources() {
		// given
		System.setProperty(EzyBeanContext.ACTIVE_PROFILES_KEY, "alpha");
		EzyBeanContext beanContext = EzyBeanContext.builder()
				.scan("com.tvd12.ezyfox.bean.v117.testing")
				.build();
		
		// when
		Properties properties = beanContext.getProperties();
		
		// then
		Asserts.assertEquals(properties.get("version"), "v1.1.7");
		Asserts.assertEquals(properties.get("env"), "Alpha");
	}
	
	public static void main(String[] args) {
		EzyBeanContext beanContext = EzyBeanContext.builder()
				.scan("com.tvd12.ezyfox.bean.v117.testing")
				.build();
		Properties properties = beanContext.getProperties();
		System.out.println(properties);
	}
}
