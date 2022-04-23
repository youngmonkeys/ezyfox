package com.tvd12.ezyfox.binding.testing.scan2;

import com.tvd12.ezyfox.binding.annotation.*;
import lombok.Getter;
import lombok.Setter;

@EzyObjectBinding
public class Scan2C {

    @EzyIgnore
    @Getter
    @Setter
    private String a;

    @EzyValue("b")
    @Setter
    private String b;

    @EzyValue
    @Setter
    @Getter
    private String c;

    @Getter
    @EzyReader(Scan2ObjectReader.class)
    private Scan2Object object;

    @EzyIgnore
    public String getB() {
        return "b";
    }

    @EzyWriter(Scan2ObjectWriter.class)
    public void setObject(Scan2Object object) {
        this.object = object;
    }

    @EzyWriter(Scan2ObjectWriter.class)
    public Scan2Object getObject1() {
        return new Scan2Object();
    }
}
