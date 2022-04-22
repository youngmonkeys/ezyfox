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

    @Setter
    @EzyProperty("game.auto_start")
    private boolean autoStart;
    @EzyProperty("game.auto_start")
    public boolean autoStartField;
    @Setter
    @EzyProperty("game.byte")
    private byte byteValue;
    @EzyProperty("game.byte")
    public byte byteValueField;
    @Setter
    @EzyProperty("game.char")
    private char charValue;
    @EzyProperty("game.char")
    public char charValueField;
    @Setter
    @EzyProperty("game.double")
    private double doubleValue;
    @EzyProperty("game.double")
    public double doubleValueField;
    @Setter
    @EzyProperty("game.float")
    private float floatValue;
    @EzyProperty("game.float")
    public float floatValueField;
    @Setter
    @EzyProperty("game.int")
    private int intValue;
    @EzyProperty("game.int")
    public int intValueField;
    @Setter
    @EzyProperty("game.long")
    private long longValue;
    @EzyProperty("game.long")
    public long longValueField;
    @Setter
    @EzyProperty("game.short")
    private short shortValue;
    @EzyProperty("game.short")
    public short shortValueField;
    private final ClassB classB;
    private final ClassD classD;
    
    @EzyAutoBind
    @Setter
    private ClassC classC;
    
    @EzyAutoBind
    public ClassE classE;
    
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
