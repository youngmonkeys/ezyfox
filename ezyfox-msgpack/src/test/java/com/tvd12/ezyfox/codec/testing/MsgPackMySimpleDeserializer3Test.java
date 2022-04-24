package com.tvd12.ezyfox.codec.testing;

import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.codec.EzyMessageSerializer;
import com.tvd12.ezyfox.codec.MsgPackSimpleDeserializer;
import com.tvd12.ezyfox.codec.MsgPackSimpleSerializer;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.io.EzyMath;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;

public class MsgPackMySimpleDeserializer3Test extends MsgPackCodecTest {

    @Test
    public void test1() throws IOException {
        byte[] bin8 = new byte[EzyMath.bin2int(1)];
        byte[] bin16 = new byte[EzyMath.bin2int(6)];
        byte[] bin32 = new byte[EzyMath.bin2int(17)];
        Arrays.fill(bin8, (byte) 'a');
        Arrays.fill(bin16, (byte) 'b');
        Arrays.fill(bin32, (byte) 'c');
        String str8 = new String(bin8);
        String str16 = new String(bin16);
        String str32 = new String(bin32);
        EzyMessageSerializer serializer = new MsgPackSimpleSerializer();
        EzyObjectBuilder dataBuilder = newObjectBuilder()
            .append("a", str8)
            .append("b", str16)
            .append("c", str32);
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
        Asserts.assertEquals(token, "abcdef");
    }
}
