package com.tvd12.ezyfox.testing.io;

import org.testng.annotations.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class CharBufferTest {

    @Test
    public void test() {
        try {
            String hello = "Hello World";
            ByteBuffer buffer = ByteBuffer.allocate(hello.length());
            CharBuffer charBuffer = buffer.asCharBuffer();
            charBuffer.put(hello);
            charBuffer.flip();
            byte[] bytes = new byte[hello.length()];
            buffer.get(bytes);
            System.out.println(new String(bytes));
        } catch (Exception e) {
        }
    }
}
