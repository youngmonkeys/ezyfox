package com.tvd12.ezyfox.testing.util;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.util.EzyPair;

public class EzyPairTest {

    @Test
    public void test() {
        EzyPair<String, Integer> pair = new EzyPair<>("a");
        assert pair.getKey().equals("a");
        assert pair.getValue() == null;
        pair = new EzyPair<>("b", 2);
        assert pair.getKey().equals("b");
        assert pair.getValue() == 2;
        assert pair.equals(pair);
        assert !pair.equals(null);
        assert !pair.equals((Object)new String());
        assert pair.equals(new EzyPair<>("b"));
        assert !pair.equals(new EzyPair<>("a"));
        assert pair.hashCode() == "b".hashCode();
        System.out.println(pair.toString());
        try {
            pair = new EzyPair<>(null);
        }
        catch (Exception e) {
            assert e instanceof NullPointerException;
        }
    }

}
