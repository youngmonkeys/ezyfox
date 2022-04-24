package com.tvd12.ezyfox.bean.testing.prototype;

import com.tvd12.ezyfox.annotation.EzyProperty;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPostInit;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@EzyPrototype
public class ClassA {

    private final ClassB classB;
    private final ClassD classD;
    @EzyProperty("game.auto_start")
    public boolean autoStartField;
    @EzyProperty("game.byte")
    public byte byteValueField;
    @EzyProperty("game.char")
    public char charValueField;
    @EzyProperty("game.double")
    public double doubleValueField;
    @EzyProperty("game.float")
    public float floatValueField;
    @EzyProperty("game.int")
    public int intValueField;
    @EzyProperty("game.long")
    public long longValueField;
    @EzyProperty("game.short")
    public short shortValueField;
    @EzyAutoBind
    public ClassE classE;
    @Setter
    @EzyProperty("game.auto_start")
    private boolean autoStart;
    @Setter
    @EzyProperty("game.byte")
    private byte byteValue;
    @Setter
    @EzyProperty("game.char")
    private char charValue;
    @Setter
    @EzyProperty("game.double")
    private double doubleValue;
    @Setter
    @EzyProperty("game.float")
    private float floatValue;
    @Setter
    @EzyProperty("game.int")
    private int intValue;
    @Setter
    @EzyProperty("game.long")
    private long longValue;
    @Setter
    @EzyProperty("game.short")
    private short shortValue;
    @EzyAutoBind
    @Setter
    private ClassC classC;

    @EzyAutoBind("classB")
    public ClassA(ClassB classB, ClassD classD) {
        this.classB = classB;
        this.classD = classD;
    }

    @EzyPostInit
    public void haha() {
        System.out.println("hello world");
    }
}
