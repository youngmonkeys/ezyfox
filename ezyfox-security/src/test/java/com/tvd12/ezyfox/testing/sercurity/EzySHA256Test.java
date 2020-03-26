package com.tvd12.ezyfox.testing.sercurity;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.sercurity.EzySHA256;

public class EzySHA256Test {

	@Test
	public void test() {
		System.out.println(EzySHA256.cryptUtf("dung"));
		assert "05B3B66D444B8ED761948161AA8E4B986D2DF0588FACC556B4C97DC60D44C286".equals(EzySHA256.cryptUtf("dung"));
		System.out.println(EzySHA256.cryptUtfToLowercase("dung"));
		assert "05B3B66D444B8ED761948161AA8E4B986D2DF0588FACC556B4C97DC60D44C286".toLowerCase().equals(EzySHA256.cryptUtfToLowercase("dung"));
	}
	
}
