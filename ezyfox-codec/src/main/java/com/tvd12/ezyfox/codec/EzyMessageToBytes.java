package com.tvd12.ezyfox.codec;

public interface EzyMessageToBytes {

    <T> T convert(EzyMessage message);

}
