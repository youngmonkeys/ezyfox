package com.tvd12.ezyfox.testing.sercurity;

import static org.testng.Assert.assertEquals;

import java.util.Random;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.sercurity.EzyMD5;
import com.tvd12.ezyfox.sercurity.EzyMessageDigests;
import com.tvd12.test.base.BaseTest;

public class EzyMD5Test extends BaseTest {

    static final String B64T = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    @Override
    public Class<?> getTestClass() {
        return EzyMD5.class;
    }

    @Test
    public void test() throws Exception {
        String salt = "$1$" + B64.getRandomSalt(8);
        System.out.println(salt);
        System.out.println("a: " + EzyMD5.cryptUtf("dungtv"));
        System.out.println("a: " + EzyMD5.cryptUtf("", "dungtv"));
        assertEquals(EzyMD5.cryptUtf("dungtv", ""), "D628426A481BC99850EFFC0B7F6997ED");
        assertEquals(EzyMD5.cryptUtfToBytes("dungtv", ""), EzyMessageDigests.getAlgorithm("MD5").digest("dungtv".getBytes()));
        System.out.println(salt);
        System.out.println("a: " + EzyMD5.cryptUtfToLowercase("dungtv"));
        System.out.println("a: " + EzyMD5.cryptUtfToLowercase("", "dungtv"));
        assertEquals(EzyMD5.cryptUtfToLowercase("dungtv", ""), "D628426A481BC99850EFFC0B7F6997ED".toLowerCase());
    }

    public static class B64 {
        static String getRandomSalt(int num) {
            StringBuilder saltString = new StringBuilder();
            for (int i = 1; i <= num; ++i)
                saltString.append(B64T.charAt(new Random().nextInt(B64T.length())));
           return saltString.toString();
        }
    }}