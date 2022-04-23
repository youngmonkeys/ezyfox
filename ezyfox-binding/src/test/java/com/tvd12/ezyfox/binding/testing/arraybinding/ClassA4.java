package com.tvd12.ezyfox.binding.testing.arraybinding;

import com.tvd12.ezyfox.binding.EzyAccessType;
import com.tvd12.ezyfox.binding.annotation.EzyArrayBinding;
import com.tvd12.ezyfox.binding.annotation.EzyIndex;
import com.tvd12.ezyfox.collect.Lists;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@EzyArrayBinding(indexes = {"a", "d", "z1", "f", "g", "h", "z2", "i"}, accessType = EzyAccessType.NONE)
public class ClassA4 {

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @EzyIndex(8)
    public List<?> i = Lists.newArrayList(1, 2, 3);
    @EzyIndex(0)
    private String a = "1";
    @EzyIndex(1)
    private String b = "2";
    @EzyIndex(2)
    private String c = "3";
    private String d = "4";
    @EzyIndex(5)
    private String f = "5";
    @EzyIndex(6)
    private List<Integer> g = Lists.newArrayList(1, 2, 3, 4, 5);
    @EzyIndex(7)
    private List<?> h = Lists.newArrayList(1, 2, 3, 4, 5);

    @EzyIndex(9)
    public void setJ(String a, String b) {}

    @EzyIndex(9)
    public void getJ() {}

    @EzyIndex(11)
    public String getK() {
        return "k";
    }

    @EzyIndex(10)
    public void setK(String k) {}
}
