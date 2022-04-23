package com.tvd12.ezyfox.io;

public interface EzyCastToByte {

    default byte cast(int value) {
        return (byte) value;
    }

    default byte cast(long value) {
        return (byte) value;
    }
}
