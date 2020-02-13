package com.tvd12.ezyfox.testing.net;

import java.net.InetSocketAddress;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.net.EzySocketAddresses;
import com.tvd12.test.base.BaseTest;

public class EzySocketAddressesTest extends BaseTest {
	
	@Test
	public void test() {
		InetSocketAddress address1 = new InetSocketAddress("127.0.0.1", 123);
		System.out.println("add: " + address1 + ", unr: " + address1.isUnresolved());
		System.out.println(EzySocketAddresses.getHost(address1));
	}
	
	@Override
	public Class<?> getTestClass() {
		return EzySocketAddressesTest.class;
	}

}
