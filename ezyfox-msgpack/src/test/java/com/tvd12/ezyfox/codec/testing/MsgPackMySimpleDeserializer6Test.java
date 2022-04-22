package com.tvd12.ezyfox.codec.testing;

import java.io.IOException;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.codec.EzyMessageSerializer;
import com.tvd12.ezyfox.codec.MsgPackSimpleDeserializer;
import com.tvd12.ezyfox.codec.MsgPackSimpleSerializer;
import com.tvd12.ezyfox.entity.EzyArray;

public class MsgPackMySimpleDeserializer6Test extends MsgPackCodecTest {

    @Test
    public void test1() throws IOException {
        EzyMessageSerializer serializer = new MsgPackSimpleSerializer();
        EzyArray request = newArrayBuilder()
                .append(-1)
                .build();
        byte[] bytes = serializer.serialize(request);
        MsgPackSimpleDeserializer deserializer = new MsgPackSimpleDeserializer();
        EzyArray answer = deserializer.deserialize(bytes);
        getLogger().info("answer = {}", answer);
    }

}
