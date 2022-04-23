package com.tvd12.ezyfox.binding.testing.arraybinding;

import com.tvd12.ezyfox.binding.EzyAccessType;
import com.tvd12.ezyfox.binding.annotation.EzyArrayBinding;
import com.tvd12.ezyfox.binding.annotation.EzyIndex;
import com.tvd12.ezyfox.binding.annotation.EzyPostRead;
import com.tvd12.ezyfox.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@EzyArrayBinding(accessType = EzyAccessType.NONE)
public class ClassA2 {

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

    @EzyPostRead
    public void hello() {
        System.out.println("\n\n\npostReadMethod: hello world\n\n");
    }
}
