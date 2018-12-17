package com.tvd12.ezyfox.testing.util;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.util.EzyNameStyles;
import com.tvd12.test.base.BaseTest;

public class EzyNameStylesTest extends BaseTest {

	@Override
	public Class<?> getTestClass() {
		return EzyNameStyles.class;
	}
	
	@Test
	public void test() {
		assertEquals(EzyNameStyles.toLowerHyphen("EzyFox"), "ezy-fox");
		assertEquals(EzyNameStyles.toLowerHyphen("ezyFox"), "ezy-fox");
		assertEquals(EzyNameStyles.toLowerHyphen("Ezyfox"), "ezyfox");
		assertEquals(EzyNameStyles.toLowerHyphen("ezyfox"), "ezyfox");
		assertEquals(EzyNameStyles.toLowerHyphen("URIParser"), "uri-parser");
		assertEquals(EzyNameStyles.toLowerHyphen("URabcIParser"), "u-rabc-i-parser");
		assertEquals(EzyNameStyles.toLowerHyphen("UR123IParser"), "u-r123-i-parser");
		assertEquals(EzyNameStyles.toLowerHyphen("URIParSER"), "uri-par-ser");
		assertEquals(EzyNameStyles.toLowerHyphen("URIParseR"), "uri-parse-r");
	}
	
}
