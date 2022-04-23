package com.tvd12.ezyfox.codec.testing;

import java.io.IOException;
import java.util.Arrays;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.codec.EzyMessageSerializer;
import com.tvd12.ezyfox.codec.MsgPackSimpleDeserializer;
import com.tvd12.ezyfox.codec.MsgPackSimpleSerializer;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.exception.EzyCodecException;
import com.tvd12.ezyfox.io.EzyMath;
import com.tvd12.test.reflect.MethodInvoker;

public class MsgPackMySimpleDeserializer2Test extends MsgPackCodecTest {

    @Test
    public void test1() throws IOException {
        byte[] bin8 = new byte[EzyMath.bin2int(1)];
        byte[] bin16 = new byte[EzyMath.bin2int(6)];
        byte[] bin32 = new byte[EzyMath.bin2int(17)];
        Arrays.fill(bin8, (byte)'a');
        Arrays.fill(bin16, (byte)'b');
        Arrays.fill(bin32, (byte)'c');
        EzyMessageSerializer serializer = new MsgPackSimpleSerializer();
        EzyObjectBuilder dataBuilder = newObjectBuilder()
                .append("a", bin16)
                .append("b", bin16)
                .append("c", bin32);
        EzyArray request = newArrayBuilder()
                .append(15)
                .append(26)
                .append("abcdef")
                .append(dataBuilder)
                .build();
        byte[] bytes = serializer.serialize(request);
        MsgPackSimpleDeserializer deserializer = new MsgPackSimpleDeserializer();
        EzyArray answer = deserializer.deserialize(bytes);
        int appId = answer.get(0);
        int command = answer.get(1);
        String token = answer.get(2);
        assert appId == 15 : "deserialize error";
        assert command == 26 : "deserialize error";
        assert token.equals("abcdef") : "deserialize error";
        assert token.equals("abcdef") : "deserialize error";
        try {
            MethodInvoker.create()
                .method("getDataType")
                .param(int.class, -1234)
                .object(deserializer)
                .invoke();
        }
        catch (Exception e) {
            assert e.getCause().getCause() instanceof EzyCodecException;
        }
    }
}
