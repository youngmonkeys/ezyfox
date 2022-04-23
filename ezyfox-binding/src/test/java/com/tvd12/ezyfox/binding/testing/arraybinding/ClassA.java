package com.tvd12.ezyfox.binding.testing.arraybinding;

import com.tvd12.ezyfox.binding.annotation.EzyArrayBinding;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Setter
@Getter
@ToString
@EzyArrayBinding
public class ClassA {

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    public String d = "4";
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    public String e = "5";
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    public String f = "6";
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private String ignore1;
    @SuppressWarnings("rawtypes")
    private Map ignore2;
    private String null1;
    private String a = "1";
    private String b = "2";
    private String c = "3";
}
