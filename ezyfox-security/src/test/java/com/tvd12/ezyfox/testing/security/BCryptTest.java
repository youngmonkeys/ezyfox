package com.tvd12.ezyfox.testing.security;

import com.tvd12.ezyfox.security.BCrypt;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.util.RandomUtil;
import org.testng.annotations.Test;

public class BCryptTest {

    @Test
    public void test() {
        // given
        String salt = BCrypt.gensalt();
        String password = RandomUtil.randomAlphabetString(128);

        // when
        String encryptedPassword = BCrypt.hashpw(
            password,
            salt
        );

        // then
        Asserts.assertEquals(encryptedPassword.length(), 60);
    }

    @Test
    public void hashpwWithNullSalt() {
        // given
        String password = RandomUtil.randomAlphabetString(128);

        // when
        Throwable e = Asserts.assertThrows(() ->
            BCrypt.hashpw(password, null)
        );

        // then
        Asserts.assertEqualsType(e, IllegalArgumentException.class);
    }

    @Test
    public void hashpwWithInvalidSaltDueToLength() {
        // given
        String password = RandomUtil.randomAlphabetString(128);

        // when
        Throwable e = Asserts.assertThrows(() ->
            BCrypt.hashpw(password, "abc")
        );

        // then
        Asserts.assertEqualsType(e, IllegalArgumentException.class);
    }

    @Test
    public void hashpwWithInvalidSaltDueToChatAt0() {
        // given
        String password = RandomUtil.randomAlphabetString(128);

        // when
        Throwable e = Asserts.assertThrows(() ->
            BCrypt.hashpw(password, RandomUtil.randomAlphabetString(28))
        );

        // then
        Asserts.assertEqualsType(e, IllegalArgumentException.class);
    }

    @Test
    public void hashpwWithInvalidSaltDueToChatAt1() {
        // given
        String password = RandomUtil.randomAlphabetString(128);

        // when
        Throwable e = Asserts.assertThrows(() ->
            BCrypt.hashpw(password, "$#" + RandomUtil.randomAlphabetString(28))
        );

        // then
        Asserts.assertEqualsType(e, IllegalArgumentException.class);
    }

    @Test
    public void hashpwWithInvalidSaltDueToChatAt2And3() {
        // given
        String password = RandomUtil.randomAlphabetString(128);

        // when
        Throwable e = Asserts.assertThrows(() ->
            BCrypt.hashpw(password, "$2a" + RandomUtil.randomAlphabetString(28))
        );

        // then
        Asserts.assertEqualsType(e, IllegalArgumentException.class);
    }

    @Test
    public void hashpwWithInvalidSaltDueToChatAt2AndInvalidMinor() {
        // given
        String password = RandomUtil.randomAlphabetString(128);

        // when
        Throwable e = Asserts.assertThrows(() ->
            BCrypt.hashpw(password, "$2z" + RandomUtil.randomAlphabetString(28))
        );

        // then
        Asserts.assertEqualsType(e, IllegalArgumentException.class);
    }

    @Test
    public void gensaltWithPrefix() {
        // given
        String salt = BCrypt.gensalt("$2aezyfox");
        String password = RandomUtil.randomAlphabetString(128);

        // when
        String encryptedPassword = BCrypt.hashpw(
            password,
            salt
        );

        // then
        Asserts.assertEquals(encryptedPassword.length(), 60);
    }

    @Test
    public void gensaltWithPrefixAndRoundsTest() {
        // given
        String salt = BCrypt.gensalt("$2aezyfox", 5);
        String password = RandomUtil.randomAlphabetString(128);

        // when
        String encryptedPassword = BCrypt.hashpw(
            password,
            salt
        );

        // then
        Asserts.assertEquals(encryptedPassword.length(), 60);
    }

    @Test
    public void checkpwTest() {
        // given
        String salt = BCrypt.gensalt("$2a", 12);
        String password = "hello world";

        // when
        String encryptedPassword = BCrypt.hashpw(
            password,
            salt
        );

        // then
        Asserts.assertTrue(BCrypt.checkpw(password, encryptedPassword));
    }

    @Test
    public void checkpwNotEqualsTest() {
        // given
        String salt = BCrypt.gensalt("$2a", 12);
        String password = "hello world";

        // when
        String encryptedPassword = BCrypt.hashpw(
            password,
            salt
        );

        // then
        Asserts.assertFalse(
            BCrypt.checkpw("hello worl".getBytes(), encryptedPassword)
        );
    }
}
