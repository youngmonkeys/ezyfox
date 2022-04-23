package com.tvd12.ezyfox.binding.testing;

import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.testing.data.UserData;
import org.testng.annotations.Test;

public class PackageScanByOrderBugTest {

    @Test
    public void test() {
        EzyBindingContext bindingContext = EzyBindingContext.builder()
            .scan("com.tvd12.ezyfox.binding.testing.data")
            .scan("com.tvd12.ezyfox.binding.testing.ext.data")
            .build();
        EzyMarshaller marshaller = bindingContext.newMarshaller();
        marshaller.marshal(new UserData());
    }
}
