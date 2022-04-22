package com.tvd12.ezyfox.bean.testing.combine.pack1;

import java.util.HashMap;
import java.util.List;

import com.tvd12.ezyfox.annotation.EzyProperty;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPostInit;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.bean.testing.combine.EzyCombine0Ann;
import com.tvd12.ezyfox.io.EzyPrints;

import lombok.Getter;
import lombok.Setter;

@Getter
@EzyCombine0Ann
@EzySingleton("s1")
public class Singleton1 {

    @Setter
    @EzyAutoBind
    private HashMap<String, String> map;
    
    @Setter
    @EzyProperty("array")
    private Integer[] array;
    
    @EzyProperty("ints")
    public List<Integer> ints;
    
    @EzyPostInit
    public void post() {
        System.out.println("\n\nsingleton1: I have array: " + EzyPrints.print(array) + ", and ints: " + EzyPrints.print(ints) + "\n\n");
    }
}
