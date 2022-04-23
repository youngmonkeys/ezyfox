package com.tvd12.ezyfox.binding.testing;

import com.tvd12.ezyfox.binding.reader.EzyUuidReader;
import org.testng.annotations.Test;

import java.util.UUID;

public class EzyUuidReaderTest {

    @Test
    public void test() {
        UUID value = UUID.randomUUID();
        assert EzyUuidReader.getInstance().read(null, value) == value;
        assert EzyUuidReader.getInstance().read(null, value.toString()).equals(value);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test1() {
        EzyUuidReader.getInstance().read(null, getClass());
    }
}
