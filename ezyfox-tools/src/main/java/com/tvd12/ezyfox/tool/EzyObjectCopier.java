package com.tvd12.ezyfox.tool;

import java.util.List;
import java.util.function.Consumer;

import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyField;

public class EzyObjectCopier {

    protected boolean includeAllFields;

    public EzyObjectCopier includeAllFields(boolean includeAllFields) {
        this.includeAllFields = includeAllFields;
        return this;
    }

    public String generateScript(Class<?> objectType) {
        String script = generateScript(objectType, getter -> {});
        return script;
    }

    public String generateScript(
            Class<?> objectType, Consumer<StringBuilder> getterWrapper) {
        EzyClass originClazz = new EzyClass(objectType);
        List<EzyField> originFields = includeAllFields
                ? originClazz.getFields()
                : originClazz.getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        builder.append(objectType.getSimpleName())
            .append(" copy")
            .append(" = ")
            .append("new ")
            .append(objectType.getSimpleName())
            .append("();\n");
        int index = 0;
        for(EzyField field : originFields) {
            builder
                .append("copy.")
                .append(field.getSetterMethod())
                .append("(");
            StringBuilder getter = new StringBuilder()
                    .append("origin")
                    .append(".")
                    .append(field.getGetterMethod()).append("()");
            getterWrapper.accept(getter);
            builder.append(getter)
                .append(")")
                .append(";");
            if((index ++) < (originFields.size() - 1))
                builder.append("\n");
        }
        return builder.toString();
    }
}
