package com.tvd12.ezyfox.sercurity;

import static com.tvd12.ezyfox.io.EzyDoubleArrays.toByteArray;
import static com.tvd12.ezyfox.io.EzyDoubleArrays.toDoubleArray;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import com.tvd12.ezyfox.io.EzyStrings;

public final class EzyBase64 {

    private EzyBase64() {
    }

    public static byte[] encode(byte[] input) {
        Encoder encoder = Base64.getEncoder();
        byte[] answer = encoder.encode(input);
        return answer;
    }

    public static byte[] decode(byte[] input) {
        Decoder decoder = Base64.getDecoder();
        byte[] answer = decoder.decode(input);
        return answer;
    }

    public static byte[] decode(String input) {
        Decoder decoder = Base64.getDecoder();
        byte[] answer = decoder.decode(input);
        return answer;
    }

    public static byte[] encode(String input) {
        Encoder encoder = Base64.getEncoder();
        byte[] bytes = EzyStrings.getUtfBytes(input);
        byte[] answer = encoder.encode(bytes);
        return answer;
    }

    public static String encodeUtf(String input) {
        Encoder encoder = Base64.getEncoder();
        byte[] bytes = EzyStrings.getUtfBytes(input);
        byte[] output = encoder.encode(bytes);
        String answer = EzyStrings.newUtf(output);
        return answer;
    }

    public static String decodeUtf(String input) {
        Decoder decoder = Base64.getDecoder();
        byte[] bytes = EzyStrings.getUtfBytes(input);
        byte[] output = decoder.decode(bytes);
        String answer = EzyStrings.newUtf(output);
        return answer;
    }

    public static String encode2utf(byte[] input) {
        String answer = EzyStrings.newUtf(encode(input));
        return answer;
    }

    public static String decode2utf(byte[] input) {
        String answer = EzyStrings.newUtf(decode(input));
        return answer;
    }

    public static String encode(double[] doubleArray) {
        byte[] bytes = toByteArray(doubleArray);
        String answer = encode2utf(bytes);
        return answer;
    }

    public static double[] decode2doubles(String base64Encoded) {
        byte[] bytes = decode(base64Encoded);
        double[] answer = toDoubleArray(bytes);
        return answer;
    }

}
