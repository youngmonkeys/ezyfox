package com.tvd12.ezyfox.net;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public final class EzySocketAddresses {

    private EzySocketAddresses() {}

    public static String getHost(SocketAddress address) {
        String host;
        if (address instanceof InetSocketAddress) {
            host = ((InetSocketAddress) address).getHostString();
        } else {
            String[] strs = address.toString().split(":");
            host = strs[0].startsWith("/") ? strs[0].substring(1) : strs[0];
        }
        return host;
    }
}
