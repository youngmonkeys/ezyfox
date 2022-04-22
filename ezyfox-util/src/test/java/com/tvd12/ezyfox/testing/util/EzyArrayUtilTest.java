package com.tvd12.ezyfox.testing.util;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.util.EzyArrayUtil;

public class EzyArrayUtilTest {

    @Test
    public void test() {
        assert EzyArrayUtil.isEmpty((boolean[])null);
        assert EzyArrayUtil.isEmpty((byte[])null);
        assert EzyArrayUtil.isEmpty((char[])null);
        assert EzyArrayUtil.isEmpty((double[])null);
        assert EzyArrayUtil.isEmpty((float[])null);
        assert EzyArrayUtil.isEmpty((int[])null);
        assert EzyArrayUtil.isEmpty((long[])null);
        assert EzyArrayUtil.isEmpty((short[])null);
        assert EzyArrayUtil.isEmpty((String[])null);
        
        assert EzyArrayUtil.isEmpty(new boolean[0]);
        assert EzyArrayUtil.isEmpty(new byte[0]);
        assert EzyArrayUtil.isEmpty(new char[0]);
        assert EzyArrayUtil.isEmpty(new double[0]);
        assert EzyArrayUtil.isEmpty(new float[0]);
        assert EzyArrayUtil.isEmpty(new int[0]);
        assert EzyArrayUtil.isEmpty(new long[0]);
        assert EzyArrayUtil.isEmpty(new short[0]);
        assert EzyArrayUtil.isEmpty(new String[0]);
        
        assert EzyArrayUtil.isNotEmpty(new boolean[] {true});
        assert EzyArrayUtil.isNotEmpty(new byte[] {1});
        assert EzyArrayUtil.isNotEmpty(new char[] {'a'});
        assert EzyArrayUtil.isNotEmpty(new double[] {1.0D});
        assert EzyArrayUtil.isNotEmpty(new float[] {1.0F});
        assert EzyArrayUtil.isNotEmpty(new int[] {1});
        assert EzyArrayUtil.isNotEmpty(new long[] {1L});
        assert EzyArrayUtil.isNotEmpty(new short[] {1});
        assert EzyArrayUtil.isNotEmpty(new String[] {"a"});
        
        assert !EzyArrayUtil.isNotEmpty(new boolean[0]);
        assert !EzyArrayUtil.isNotEmpty(new byte[0]);
        assert !EzyArrayUtil.isNotEmpty(new char[0]);
        assert !EzyArrayUtil.isNotEmpty(new double[0]);
        assert !EzyArrayUtil.isNotEmpty(new float[0]);
        assert !EzyArrayUtil.isNotEmpty(new int[0]);
        assert !EzyArrayUtil.isNotEmpty(new long[0]);
        assert !EzyArrayUtil.isNotEmpty(new short[0]);
        assert !EzyArrayUtil.isNotEmpty(new String[0]);
    }}