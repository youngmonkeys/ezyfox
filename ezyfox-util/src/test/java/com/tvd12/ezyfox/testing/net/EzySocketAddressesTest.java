package com.tvd12.ezyfox.testing.net;

import com.tvd12.ezyfox.net.EzySocketAddresses;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import lombok.AllArgsConstructor;
import org.testng.annotations.Test;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class EzySocketAddressesTest extends BaseTest {

    @Test
    public void test() {
        InetSocketAddress address1 = new InetSocketAddress("127.0.0.1", 123);
        System.out.println("add: " + address1 + ", unr: " + address1.isUnresolved());
        System.out.println(EzySocketAddresses.getHost(address1));
        System.out.println(EzySocketAddresses.getHost(new Add("192.168.1.1", 12345)));
    }

    @Test
    public void getHostByCustomAddress() {
        // given
        String host = "192.168.1.1";
        Add add = new Add(host, 12345);

        // when
        String actual = EzySocketAddresses.getHost(add);

        // then
        Asserts.assertEquals(actual, host);
    }

    @Test
    public void getHostByCustomAddressWithoutSource() {
        // given
        String host = "192.168.1.1";
        AddNoSource add = new AddNoSource(host, 12345);

        // when
        String actual = EzySocketAddresses.getHost(add);

        // then
        Asserts.assertEquals(actual, host);
    }

    @Override
    public Class<?> getTestClass() {
        return EzySocketAddressesTest.class;
    }
    
    @AllArgsConstructor
    public static class Add extends SocketAddress {
        private static final long serialVersionUID = -7021682289552522671L;
        protected String host;
        protected int port;

        @Override
        public String toString() {
            return "/" + host + ":" + port;
        }
    }

    @AllArgsConstructor
    public static class AddNoSource extends SocketAddress {
        private static final long serialVersionUID = -7021682289552522671L;
        protected String host;
        protected int port;

        @Override
        public String toString() {
            return host + ":" + port;
        }
    }
}
