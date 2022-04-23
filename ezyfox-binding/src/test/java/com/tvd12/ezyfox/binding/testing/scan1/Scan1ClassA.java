package com.tvd12.ezyfox.binding.testing.scan1;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.binding.annotation.EzyReader;
import com.tvd12.ezyfox.binding.annotation.EzyWriter;
import com.tvd12.ezyfox.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@ToString(callSuper = true)
@EzyObjectBinding
public class Scan1ClassA extends Scan1ClassA1 {
    private String a = "1";
    private String b = "1";
    private String c = "1";
    private int d = 10;

    private Scan1ClassB classB = new Scan1ClassB();

    @EzyWriter(Scan1ClassCWriterImpl.class)
    @EzyReader(Scan1ClassCReaderImpl.class)
    private Scan1ClassC classC = new Scan1ClassC();

    private Map<String, String> map = newMap();

    private List<String> list = Lists.newArrayList("a", "b", "c");

    private List<Data> dataList1 = Lists.newArrayList(new Data(), new Data());

    private Data[] dataArray1 = new Data[]{new Data(), new Data()};

    protected Map<String, String> newMap() {
        Map<String, String> map = new HashMap<>();
        map.put("hello", "world");
        return map;
    }
}
