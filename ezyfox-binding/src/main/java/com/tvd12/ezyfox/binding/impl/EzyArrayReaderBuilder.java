package com.tvd12.ezyfox.binding.impl;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.tvd12.ezyfox.asm.EzyFunction;
import com.tvd12.ezyfox.asm.EzyInstruction;
import com.tvd12.ezyfox.binding.EzyAccessType;
import com.tvd12.ezyfox.binding.annotation.EzyArrayBinding;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzyGenericSetterValidator;
import com.tvd12.ezyfox.reflect.EzyGenerics;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.ezyfox.reflect.EzyReflectElement;
import com.tvd12.ezyfox.reflect.EzySetterMethod;

import lombok.Setter;

@SuppressWarnings("rawtypes")
public class EzyArrayReaderBuilder extends EzyAbstractReaderBuilder {

    @Setter
    protected static boolean debug;
    protected static final AtomicInteger COUNT = new AtomicInteger(0);

    public EzyArrayReaderBuilder(EzyClass clazz) {
        super(clazz);
    }
    
    @Override
    protected int getAccessType(EzyClass clazz) {
        EzyArrayBinding ann = clazz.getAnnotation(EzyArrayBinding.class);
        return ann == null ? EzyAccessType.ALL : ann.accessType();
    }
    
    @Override
    protected EzyElementsFetcher newElementsFetcher() {
        return new EzyArrayReaderElementsFetcher();
    }
    
    @Override
    protected String makeImplMethodContent(EzyMethod readMethod) {
        EzyFunction.EzyBody methodBody = new EzyFunction(readMethod)
                .modifier("protected")
                .body()
                    .append(new EzyInstruction("\t", "\n")
                            .variable(EzyArray.class, "value")
                            .equal()
                            .cast(EzyArray.class, "arg1")
                    );
        
        appendOutputObjectConstructor(methodBody);
        
        List<Object> elements = getElements();
        for(int index = 0 ; index < elements.size() ; index ++) {
            Object element = elements.get(index);
            EzyInstruction instruction = null;
            if(element == null) {
                continue;
            }
            if(element instanceof EzyField) {
                if(!((EzyField)element).isWritable())
                    continue;
            }
            methodBody.append(checkNotNullInstruction((EzyReflectElement)element, index));
            if(element instanceof EzyField) {
                EzyField field = (EzyField)element;
                instruction = new EzyInstruction("\t\t", "\n")
                        .append("object")
                        .dot()
                        .append(field.getName())
                        .equal();
                EzyInstruction unmarshalInstruction = newUnmarshalInstruction(
                        field, index, "");
                instruction.append(unmarshalInstruction.toString());
            }
            else {
                EzySetterMethod method = (EzySetterMethod)element;
                instruction = new EzyInstruction("\t\t", "\n")
                        .append("object")
                        .dot()
                        .append(method.getName())
                        .bracketopen();
                EzyInstruction unmarshalInstruction = newUnmarshalInstruction(
                        method, index, "()");
                instruction
                        .append(unmarshalInstruction.toString())
                        .bracketclose();
            }
            methodBody.append(instruction);
        }
        
        addPostReadMethods(methodBody, "object");
        
        methodBody.append(new EzyInstruction("\t", "\n")
                .answer()
                .append("object"));
        
        EzyFunction method = methodBody.function();
        
        return method.toString();
    }
    
    protected EzyInstruction checkNotNullInstruction(EzyReflectElement element, int index) {
        return new EzyInstruction("\t", "\n", false)
                .append("if")
                .bracketopen()
                .append("value")
                .dot()
                .append("isNotNullValue")
                .bracketopen()
                .append(index)
                .bracketclose()
                .bracketclose();
    }
    
    protected EzyInstruction newUnmarshalInstruction(
            EzyReflectElement element, int index, String valueExpSuffix) {
        Set<Class> commonGenericTypes = getCommonGenericTypes();
        Class type = getElementType(element);
        Class readerImpl = getReaderImplClass(element);
        EzyInstruction instruction = null;
        if(readerImpl != null || !commonGenericTypes.contains(type))
            instruction = newUnmarshalNormalInstruction(element, readerImpl, index);
        else
            instruction = newUnmarshalGenericInstruction(element, index);
        return wrapUnmarshalInstruction(instruction, type);
    }
    
    protected EzyInstruction newUnmarshalGenericInstruction(
            EzyReflectElement element, int index) {
        Class genericType = getElementType(element);
        if(Map.class.isAssignableFrom(genericType))
            return newUnmarshalMapInstruction(element, index);
        return newUnmarshalCollectionInstruction(element, index);
    }
    
    protected EzyInstruction newUnmarshalMapInstruction(
            EzyReflectElement element, int index) {
        Class mapType = getElementType(element);
        Type genericType = getElementGenericType(element);
        Class[] keyValueTypes = EzyGenerics.getTwoGenericClassArguments(genericType);
        EzyInstruction instruction = new EzyInstruction("", "", false)
                .append("arg0.unmarshalMap")
                .bracketopen()
                .bracketopen()
                .clazz(Object.class)
                .bracketclose()
                .append("value.getValue(")
                .append(index)
                .comma()
                .clazz(mapType, true)
                .bracketclose()
                .comma()
                .clazz(mapType, true)
                .comma()
                .clazz(keyValueTypes[0], true)
                .comma()
                .clazz(keyValueTypes[1], true)
                .bracketclose();
        return instruction;
    }
    
    protected EzyInstruction newUnmarshalCollectionInstruction(
            EzyReflectElement element, int index) {
        Class collectionType = getElementType(element);
        Type genericType = getElementGenericType(element);
        Class itemType = EzyGenerics.getOneGenericClassArgument(genericType);
        EzyInstruction instruction = new EzyInstruction("", "", false)
                .append("arg0.unmarshalCollection")
                .bracketopen()
                .bracketopen()
                .clazz(Object.class)
                .bracketclose()
                .append("value.getValue(")
                .append(index)
                .comma()
                .clazz(collectionType, true)
                .bracketclose()
                .comma()
                .clazz(collectionType, true)
                .comma()
                .clazz(itemType, true)
                .bracketclose();
        return instruction;
    }
    
    protected EzyInstruction newUnmarshalNormalInstruction(
            EzyReflectElement element, Class readerImpl, int index) {
        EzyInstruction instruction = new EzyInstruction("", "", false)
                .append("arg0.unmarshal")
                .bracketopen();
        if(readerImpl != null) {
            instruction
                .clazz(readerImpl, true)
                .comma();
        }
        Class type = getElementType(element);
        instruction
                .bracketopen()
                .clazz(Object.class)
                .bracketclose()
                .append("value.getValue(")
                .append(index)
                .comma()
                .clazz(type, true)
                .bracketclose();
        if(readerImpl == null) {
            instruction
                .comma()
                .clazz(type, true);
        }
        instruction.bracketclose();
        return instruction;
    }
    
    @Override
    protected void appendConstructorParamValue(
            EzyInstruction instruction,
            Parameter parameter, 
            int parameterIndex,
            EzyField field,
            String key) {
        instruction
                .append("value")
                .dot()
                .append("isNotNullValue")
                .bracketopen()
                .append(parameterIndex)
                .bracketclose()
                .append(" ? ")
                .append(newUnmarshalInstruction(field, parameterIndex, ""))
                .append(" : ")
                .defaultValue(parameter.getType());
    }
    
    @Override
    protected String getImplClassName() {
        return clazz.getName() + "$EzyArrayReader$EzyAutoImpl$" + COUNT.incrementAndGet();
    }
    
    @Override
    protected boolean isDebug() {
        return debug;
    }}
class EzyArrayReaderElementsFetcher extends EzyArrayElementsFetcher {
    
    private final EzyGenericSetterValidator setterValidator = new EzyGenericSetterValidator();
    
    @Override
    protected List<? extends EzyMethod> getMethodList(EzyClass clazz) {
        return clazz.getSetterMethods();
    }
    
    @Override
    protected List<? extends EzyMethod> getDeclaredMethods(EzyClass clazz) {
        return clazz.getDeclaredSetterMethods();
    }
    
    @Override
    protected boolean isValidGenericField(EzyField field) {
        return setterValidator.validate(field.getGenericType());
    }
    
    @Override
    protected boolean isValidGenericMethod(EzyMethod method) {
        return setterValidator.validate(((EzySetterMethod)method).getGenericType());
    }
    
    @Override
    protected EzyMethod newByFieldMethod(EzyMethod method) {
        return new EzySetterMethod(method);
    }
    
    @Override
    protected boolean isValidAnnotatedMethod(EzyMethod method) {
        return method.getParameterCount() == 1;
    }
}
