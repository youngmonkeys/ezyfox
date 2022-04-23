package com.tvd12.ezyfox.binding.testing.scan1;

import com.tvd12.ezyfox.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@lombok.ToString
public class ClassA<T> {

    private T t = newT();

    private List<ClassB> classB = Lists.newArrayList(new ClassB(), new ClassB());

    @SuppressWarnings("unchecked")
    protected T newT() {
        return (T) new Integer(10);
    }

    @Getter
    @Setter
    @ToString
    public static class ClassB {
        private String a = "a";
        private String b = "b";
        private String c = "c";
    }
}
