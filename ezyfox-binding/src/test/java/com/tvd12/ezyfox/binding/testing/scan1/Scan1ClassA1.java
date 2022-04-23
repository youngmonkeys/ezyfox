package com.tvd12.ezyfox.binding.testing.scan1;

import com.tvd12.ezyfox.binding.annotation.EzyValue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Scan1ClassA1 {

    protected String hung = "dep trai";

    @EzyValue("hll")
    public String getHello() {
        return "world";
    }
}
