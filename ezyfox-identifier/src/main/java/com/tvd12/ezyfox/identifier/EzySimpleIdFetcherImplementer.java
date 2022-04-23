package com.tvd12.ezyfox.identifier;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.asm.EzyFunction;
import com.tvd12.ezyfox.asm.EzyInstruction;
import com.tvd12.ezyfox.reflect.*;
import com.tvd12.ezyfox.util.EzyHasIdEntity;
import com.tvd12.ezyfox.util.EzyLoggable;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewMethod;
import lombok.Setter;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class EzySimpleIdFetcherImplementer 
        extends EzyLoggable
        implements EzyIdFetcherImplementer {

    protected final EzyClass clazz;
    protected final EzyReflectElement idElement;

    @Setter
    protected static boolean debug = false;
    protected static final AtomicInteger COUNT = new AtomicInteger(0);

    public EzySimpleIdFetcherImplementer(Class<?> clazz) {
        this(new EzyClass(clazz));
    }

    public EzySimpleIdFetcherImplementer(EzyClass clazz) {
        this.clazz = clazz;
        this.idElement = getIdElement0();
    }

    @Override
    public EzyIdFetcher implement() {
        try {
            return doImplement();
        }
        catch(Exception e) {
            throw new IllegalStateException("implement getter of " + clazz + " error", e);
        }
    }

    private EzyIdFetcher doImplement() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        String implClassName = getImplClassName();
        CtClass implClass = pool.makeClass(implClassName);
        EzyMethod getIdMethod = getGetIdMethod();
        String implMethodContent = makeGetIdMethodContent(getIdMethod);
        getIdMethod.setDisplayName("getId");
        printMethodContent(implMethodContent);
        implClass.setInterfaces(new CtClass[] { pool.get(EzyIdFetcher.class.getName()) });
        implClass.addMethod(CtNewMethod.make(implMethodContent, implClass));
        Class<?> answerClass = implClass.toClass();
        implClass.detach();
        return EzyClasses.newInstance(answerClass);
    }

    protected String makeGetIdMethodContent(EzyMethod getIdMethod) {
        EzyInstruction instruction = new EzyInstruction("\t", "\n")
                .answer();
        EzyInstruction value = new EzyInstruction("", "", false)
                .cast(clazz.getClazz(), "arg0")
                .dot();

        Class<?> valueType;

        if(idElement instanceof EzyField) {
            value.append(idElement.getName());
            valueType = ((EzyField)idElement).getType();
        }
        else {
            value.function(idElement.getName());
            valueType = ((EzyMethod)idElement).getReturnType();
        }

        instruction.valueOf(valueType, value.toString());

        return new EzyFunction(getIdMethod)
                .body()
                    .append(instruction)
                .function()
                .toString();
    }

    protected EzyMethod getGetIdMethod() {
        return EzyMethod.builder()
                .clazz(EzyIdFetcher.class)
                .methodName("getId")
                .parameterTypes(Object.class)
                .build();
    }

    private EzyReflectElement getIdElement0() {
        EzyReflectElement element = getIdElement();
        if(element != null)
            return element;
        if(EzyHasIdEntity.class.isAssignableFrom(clazz.getClazz()))
            return clazz.getMethod("getId");
        Optional<EzyField> foundField =
                clazz.getField(f -> f.isAnnotated(EzyId.class));
        if(foundField.isPresent()) {
            EzyField field = foundField.get();
            if(field.isPublic())
                return field;
            EzyMethod method = clazz.getMethod(field.getGetterMethod());
            if(method != null && method.isPublic())
                return method;
        }
        Optional<EzyMethod> foundMethod =
                clazz.getGetterMethod(m -> m.isAnnotated(EzyId.class));
        if(foundMethod.isPresent())
            return foundMethod.get();
        throw new IllegalStateException("use @EzyId to specific 'id' element on " + clazz);
    }

    protected EzyReflectElement getIdElement() {
        return null;
    }

    protected String getImplClassName() {
        return clazz.getName() + "$EzyIdFetcher$EzyAutoImpl$" + COUNT.incrementAndGet();
    }

    protected void printMethodContent(String methodContent) {
        if(debug)
            logger.debug("getId: method content \n{}", methodContent);
    }
}
