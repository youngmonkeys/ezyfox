package com.tvd12.ezyfox.testing.io;

import com.tvd12.ezyfox.io.EzySimpleValueConverter;
import com.tvd12.ezyfox.io.EzyValueConverter;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class EzySimpleValueTransformerWrapperArraysTest extends BaseTest {

    @Test
    public void test() {
        EzyValueConverter transformer = new EzySimpleValueConverter();
        assertEquals((Object) transformer.convert("true,false,true;true,false,true", Boolean[][].class), new Boolean[][]{{true, false, true}, {true, false, true}});
        assertEquals((Object) transformer.convert(new boolean[][]{{true, false, true}, {true, false, true}}, Boolean[][].class), new Boolean[][]{{true, false, true}, {true, false, true}});
        assertEquals((Object) transformer.convert(new Boolean[][]{{Boolean.TRUE, Boolean.FALSE, Boolean.TRUE}, {Boolean.TRUE, Boolean.FALSE, Boolean.TRUE}}, Boolean[][].class), new Boolean[][]{{true, false, true}, {true, false, true}});

        assertEquals((Object) transformer.convert("1,2,3;1,2,3", Byte[][].class), new Byte[][]{{1, 2, 3}, {1, 2, 3}});
        assertEquals((Object) transformer.convert(new byte[][]{{1, 2, 3}, {1, 2, 3}}, Byte[][].class), new Byte[][]{{1, 2, 3}, {1, 2, 3}});
        assertEquals((Object) transformer.convert(new Integer[][]{{1, 2, 3}, {1, 2, 3}}, Byte[][].class), new Byte[][]{{1, 2, 3}, {1, 2, 3}});

        assertEquals((Object) transformer.convert("123;123", Character[][].class), new Character[][]{{'1', '2', '3'}, {'1', '2', '3'}});
        assertEquals((Object) transformer.convert(new char[][]{{1, 2, 3}, {1, 2, 3}}, Character[][].class), new Character[][]{{1, 2, 3}, {1, 2, 3}});
        assertEquals((Object) transformer.convert(new Integer[][]{{1, 2, 3}, {1, 2, 3}}, Character[][].class), new Character[][]{{1, 2, 3}, {1, 2, 3}});

        assertEquals((Object) transformer.convert("1.1,2.2,3.3;1.1,2.2,3.3", Double[][].class), new Double[][]{{1.1D, 2.2D, 3.3D}, {1.1D, 2.2D, 3.3D}});
        assertEquals((Object) transformer.convert(new double[][]{{1D, 2D, 3D}, {1D, 2D, 3D}}, Double[][].class), new Double[][]{{1.0D, 2.0D, 3.0D}, {1.0D, 2.0D, 3.0D}});
        assertEquals((Object) transformer.convert(new Integer[][]{{1, 2, 3}, {1, 2, 3}}, Double[][].class), new Double[][]{{1.0D, 2.0D, 3.0D}, {1.0D, 2.0D, 3.0D}});

        assertEquals((Object) transformer.convert("1.1,2.2,3.3;1.1,2.2,3.3", Float[][].class), new Float[][]{{1.1F, 2.2F, 3.3F}, {1.1F, 2.2F, 3.3F}});
        assertEquals((Object) transformer.convert(new float[][]{{1F, 2F, 3F}, {1F, 2F, 3F}}, Float[][].class), new Float[][]{{1.0F, 2.0F, 3.0F}, {1.0F, 2.0F, 3.0F}});
        assertEquals((Object) transformer.convert(new Integer[][]{{1, 2, 3}, {1, 2, 3}}, Float[][].class), new Float[][]{{1.0F, 2.0F, 3.0F}, {1.0F, 2.0F, 3.0F}});

        assertEquals((Object) transformer.convert("1,2,3;1,2,3", Integer[][].class), new Integer[][]{{1, 2, 3}, {1, 2, 3}});
        assertEquals((Object) transformer.convert(new int[][]{{1, 2, 3}, {1, 2, 3}}, Integer[][].class), new Integer[][]{{1, 2, 3}, {1, 2, 3}});
        assertEquals((Object) transformer.convert(new Integer[][]{{1, 2, 3}, {1, 2, 3}}, Integer[][].class), new Integer[][]{{1, 2, 3}, {1, 2, 3}});

        assertEquals((Object) transformer.convert("1,2,3;1,2,3", Long[][].class), new Long[][]{{1L, 2L, 3L}, {1L, 2L, 3L}});
        assertEquals((Object) transformer.convert(new long[][]{{1L, 2L, 3L}, {1L, 2L, 3L}}, Long[][].class), new Long[][]{{1L, 2L, 3L}, {1L, 2L, 3L}});
        assertEquals((Object) transformer.convert(new Integer[][]{{1, 2, 3}, {1, 2, 3}}, Long[][].class), new Long[][]{{1L, 2L, 3L}, {1L, 2L, 3L}});

        assertEquals((Object) transformer.convert("1,2,3;1,2,3", Short[][].class), new Short[][]{{1, 2, 3}, {1, 2, 3}});
        assertEquals((Object) transformer.convert(new short[][]{{1, 2, 3}, {1, 2, 3}}, Short[][].class), new Short[][]{{1, 2, 3}, {1, 2, 3}});
        assertEquals((Object) transformer.convert(new Integer[][]{{1, 2, 3}, {1, 2, 3}}, Short[][].class), new Short[][]{{1, 2, 3}, {1, 2, 3}});

        assertEquals((Object) transformer.convert("1,2,3;1,2,3", String[][].class), new String[][]{{"1", "2", "3"}, {"1", "2", "3"}});
        assertEquals((Object) transformer.convert(new String[][]{{"1", "2", "3"}, {"1", "2", "3"}}, String[][].class), new String[][]{{"1", "2", "3"}, {"1", "2", "3"}});

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test1() {
        EzyValueConverter transformer = new EzySimpleValueConverter();
        transformer.convert(new Object(), Boolean[][].class);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test2() {
        EzyValueConverter transformer = new EzySimpleValueConverter();
        transformer.convert(new Object(), Byte[][].class);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test3() {
        EzyValueConverter transformer = new EzySimpleValueConverter();
        transformer.convert(new Object(), Character[][].class);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test4() {
        EzyValueConverter transformer = new EzySimpleValueConverter();
        transformer.convert(new Object(), Double[][].class);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test5() {
        EzyValueConverter transformer = new EzySimpleValueConverter();
        transformer.convert(new Object(), Float[][].class);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test6() {
        EzyValueConverter transformer = new EzySimpleValueConverter();
        transformer.convert(new Object(), Integer[][].class);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test7() {
        EzyValueConverter transformer = new EzySimpleValueConverter();
        transformer.convert(new Object(), Long[][].class);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test8() {
        EzyValueConverter transformer = new EzySimpleValueConverter();
        transformer.convert(new Object(), Short[][].class);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test9() {
        EzyValueConverter transformer = new EzySimpleValueConverter();
        transformer.convert(new Object(), String[][].class);
    }
}
