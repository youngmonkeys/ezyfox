package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.util.EzyCredential;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyCredentialTest extends BaseTest {

    @Test
    public void test() {
        EzyCredential credential = new EzyCredential();
        credential.setUsername("dungtv");
        credential.setPassword("123456");
        assert credential.getUsername().equals("dungtv");
        assert credential.getPassword().equals("123456");
    }
}
