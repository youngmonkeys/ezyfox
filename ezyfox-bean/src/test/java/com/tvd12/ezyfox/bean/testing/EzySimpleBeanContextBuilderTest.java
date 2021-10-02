package com.tvd12.ezyfox.bean.testing;

import java.util.Properties;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.reflect.FieldUtil;
import com.tvd12.test.reflect.MethodUtil;

public class EzySimpleBeanContextBuilderTest {

    @Test
    public void activeProfilesTest() {
        // given
        String activeProfiles = "foo,bar";
        
        EzyBeanContextBuilder sut = EzyBeanContext.builder()
            .scan((String)null)
            .addProperty(EzyBeanContext.ACTIVE_PROFILES_KEY, "hello,big,world")
            .activeProfiles(activeProfiles);
        
        // when
        String actual = MethodUtil.invokeMethod("getActiveProfiles", sut);
        
        // then
        Asserts.assertEquals("hello,big,world,bar,foo", actual);
    }
    
    @Test
    public void activeProfilesDefaultNullTest() {
        // given
        String activeProfiles = "foo,bar";
        
        EzyBeanContextBuilder sut = EzyBeanContext.builder()
            .activeProfiles(null)
            .scan((String)null);
        
        Properties properties = FieldUtil.getFieldValue(sut, "properties");
        properties.clear();
        
        sut.activeProfiles(activeProfiles);
        
        // when
        String actual = MethodUtil.invokeMethod("getActiveProfiles", sut);
        
        // then
        Asserts.assertEquals("bar,foo", actual);
    }
}
