package com.tvd12.ezyfox.binding.testing.scan.pack1;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.binding.annotation.EzyReaderImpl;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EzyObjectBinding
public class ClassB {

    @com.tvd12.ezyfox.binding.annotation.EzyReader(TypeReader.class)
    private Type type = Type.HELLO;

    public static enum Type {
        HELLO
    }

    @EzyReaderImpl
    public static class TypeReader implements EzyReader<Object, Type> {
        @Override
        public Type read(EzyUnmarshaller unmarshaller, Object value) {
            return Type.valueOf(value.toString());
        }
    }
}