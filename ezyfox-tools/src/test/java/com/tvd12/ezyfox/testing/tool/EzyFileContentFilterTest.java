package com.tvd12.ezyfox.testing.tool;

import java.util.List;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.tool.EzyFileContentFilter;
import com.tvd12.ezyfox.tool.data.EzyPathProxy;

public class EzyFileContentFilterTest {

    @Test
    public void test() throws Exception {
        EzyFileContentFilter filter = EzyFileContentFilter.builder()
                .fileFilter(p -> new EzyPathProxy(p).getFileExtension().equals("xml"))
                .rootDir("src/test/resources/")
                .build();
        List<Object> list = filter.filter();
        System.out.println(list);
    }
}
