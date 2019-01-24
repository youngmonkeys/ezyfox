package com.tvd12.ezyfox.rabbitmq.codec;

public interface EzyRabbitDataCodec {

    Object deserialize(String cmd, byte[] request);

    byte[] serialize(String cmd, Object response);
}
