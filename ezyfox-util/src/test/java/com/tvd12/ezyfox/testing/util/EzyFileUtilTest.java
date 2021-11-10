package com.tvd12.ezyfox.testing.util;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.util.EzyFileUtil;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;

public class EzyFileUtilTest extends BaseTest {

	@Override
	public Class<?> getTestClass() {
		return EzyFileUtil.class;
	}
	
	@Test
	public void createFileIfNotExistsTest() throws IOException {
	    // given
	    File file = new File("test-output/test-files/file.txt");
	    File noParentFile = new File("test-file.txt");
	    
	    // when
	    // then
	    file.delete();
	    file.getParentFile().delete();
	    
	    // parentFile != null && !parentFile.exists()
	    Asserts.assertTrue(EzyFileUtil.createFileIfNotExists(file));
	    
	    // file existed
	    Asserts.assertFalse(EzyFileUtil.createFileIfNotExists(file));
	    
	    // parentFile != null && parentFile.exists()
	    file.delete();
	    Asserts.assertTrue(EzyFileUtil.createFileIfNotExists(file));
	    
	    file.delete();
	    file.getParentFile().delete();
	    
	    noParentFile.delete();
	    
	    // parentFile != null && !parentFile.exists()
	    Asserts.assertTrue(EzyFileUtil.createFileIfNotExists(noParentFile));
	    
	    // parentFile == null
	    noParentFile.delete();
        Asserts.assertTrue(EzyFileUtil.createFileIfNotExists(noParentFile));
	    
        noParentFile.delete();
	}
}
