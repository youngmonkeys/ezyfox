package com.tvd12.ezyfox.binding.testing.full;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EzyObjectBinding
public class FullData {

    protected boolean wrapperBooleanValue = true;
    protected byte wrapperByteValue = 1;
    protected char wrapperCharValue = 'a';
    protected double wrapperDoubleValue = 2.0D;
    protected float wrapperFloatValue = 3.0F;
    protected int wrapperIntValue = 4;
    protected long wrapperLongValue = 5;
    protected short primitiveShortValue = 6;

    protected boolean[] primitiveBooleanValues = new boolean[] {true, false};
    protected byte[] primitiveByteValues = new byte[] {1, 2};
    protected char[] primitiveCharValues = new char[] {'a', 'b'};
    protected double[] primitiveDoubleValues = new double[] {3D, 4D};
    protected float[] primitiveFloatValues = new float[] {5.0F, 6.0F};
    protected int[] primitiveIntValues = new int[] {7, 8};
    protected long[] primitiveLongValues = new long[] {9L, 10L};
    protected short[] primitivewrapperShortValues = new short[] {11, 12};

    protected Boolean[] wrapperBooleanValues = new Boolean[] {true, false};
    protected Byte[] wrapperByteValues = new Byte[] {1, 2};
    protected Character[] wrapperCharValues = new Character[] {'a', 'b'};
    protected Double[] wrapperDoubleValues = new Double[] {3D, 4D};
    protected Float[] wrapperFloatValues = new Float[] {5.0F, 6.0F};
    protected Integer[] wrapperIntValues = new Integer[] {7, 8};
    protected Long[] wrapperLongValues = new Long[] {9L, 10L};
    protected Short[] wrapperShortValues = new Short[] {11, 12};
    protected String[] stringValues = new String[] {"c", "d"};

}
