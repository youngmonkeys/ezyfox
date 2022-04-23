package com.tvd12.ezyfox.testing.stream;

import com.tvd12.ezyfox.exception.EzyFileNotFoundException;
import com.tvd12.ezyfox.stream.EzySimpleInputStreamLoader;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

public class EzySimpleInputStreamLoaderTest extends BaseTest {

    @Test
    public void test() {
        assert new EzySimpleInputStreamLoader().load("pom.xml") != null;
    }

    @Test(expectedExceptions = {EzyFileNotFoundException.class})
    public void test1() {
        EzySimpleInputStreamLoader.builder()
            .throwException(true)
            .build()
            .load("ffsadfs.afasdf");
    }

    @Test(expectedExceptions = {EzyFileNotFoundException.class})
    public void test2() {
        new EzySimpleInputStreamLoader.Builder() {
            public com.tvd12.ezyfox.stream.EzyInputStreamLoader build() {
                return new EzySimpleInputStreamLoader(this) {
                    protected java.io.File getFile(String filePath) throws FileNotFoundException {
                        throw new FileNotFoundException();
                    }
                };
            }
        }
            .throwException(true)
            .build()
            .load("ffsadfs.afasdf");
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void test3() {
        new EzySimpleInputStreamLoader.Builder() {
            public com.tvd12.ezyfox.stream.EzyInputStreamLoader build() {
                return new EzySimpleInputStreamLoader(this) {
                    protected java.io.File getFile(String filePath) {
                        throw new NullPointerException();
                    }
                };
            }
        }
            .throwException(true)
            .build()
            .load("ffsadfs.afasdf");
    }
}
