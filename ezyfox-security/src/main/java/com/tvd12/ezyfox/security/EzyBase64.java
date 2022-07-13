package com.tvd12.ezyfox.security;

import com.tvd12.ezyfox.io.EzyStrings;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import static com.tvd12.ezyfox.io.EzyDoubleArrays.toByteArray;
import static com.tvd12.ezyfox.io.EzyDoubleArrays.toDoubleArray;

public final class EzyBase64 {

    private EzyBase64() {}

    public static byte[] encode(byte[] input) {
        Encoder encoder = Base64.getEncoder();
        return encoder.encode(input);
    }

    public static byte[] encode(String input) {
        Encoder encoder = Base64.getEncoder();
        byte[] bytes = EzyStrings.getUtfBytes(input);
        return encoder.encode(bytes);
    }

    public static String encode(double[] doubleArray) {
        byte[] bytes = toByteArray(doubleArray);
        return encode2utf(bytes);
    }

    public static byte[] decode(byte[] input) {
        Decoder decoder = Base64.getDecoder();
        return decoder.decode(input);
    }

    public static byte[] decode(String input) {
        Decoder decoder = Base64.getDecoder();
        return decoder.decode(input);
    }

    public static String encodeUtf(String input) {
        Encoder encoder = Base64.getEncoder();
        byte[] bytes = EzyStrings.getUtfBytes(input);
        byte[] output = encoder.encode(bytes);
        return EzyStrings.newUtf(output);
    }

    public static String decodeUtf(String input) {
        Decoder decoder = Base64.getDecoder();
        byte[] bytes = EzyStrings.getUtfBytes(input);
        byte[] output = decoder.decode(bytes);
        return EzyStrings.newUtf(output);
    }

    public static String encode2utf(byte[] input) {
        return EzyStrings.newUtf(encode(input));
    }

    public static String decode2utf(byte[] input) {
        return EzyStrings.newUtf(decode(input));
    }

    public static double[] decode2doubles(String base64Encoded) {
        byte[] bytes = decode(base64Encoded);
        return toDoubleArray(bytes);
    }
}
