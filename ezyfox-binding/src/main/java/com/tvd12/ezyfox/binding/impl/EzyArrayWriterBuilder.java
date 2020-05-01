package com.tvd12.ezyfox.binding.impl;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.tvd12.ezyfox.asm.EzyFunction;
import com.tvd12.ezyfox.asm.EzyInstruction;
import com.tvd12.ezyfox.binding.EzyAccessType;
import com.tvd12.ezyfox.binding.annotation.EzyArrayBinding;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzyGetterMethod;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.ezyfox.reflect.EzyReflectElement;

import lombok.Setter;

@SuppressWarnings("rawtypes")
public class EzyArrayWriterBuilder 
		extends EzyAbstractWriterBuilder {

	@Setter
	protected static boolean debug;
	protected static final AtomicInteger COUNT = new AtomicInteger(0);

	public EzyArrayWriterBuilder(EzyClass clazz) {
		super(clazz);
	}
	
	@Override
	protected Class<?> getOutType() {
		return EzyArray.class;
	}
	
	@Override
	protected int getAccessType(EzyClass clazz) {
		EzyArrayBinding ann = clazz.getAnnotation(EzyArrayBinding.class);
		return ann == null ? EzyAccessType.ALL : ann.accessType();
	}
	
	@Override
	protected EzyElementsFetcher newElementsFetcher() {
		return new EzyArrayWriterElementsFetcher();
	}
	
	@Override
	protected String makeImplMethodContent(EzyMethod writeMethod) {
		EzyFunction.EzyBody methodBody = new EzyFunction(writeMethod)
				.modifier("protected")
				.body()
					.append(new EzyInstruction("\t", "\n")
							.variable(clazz.getClazz(), "value")
							.equal()
							.cast(clazz.getClazz(), "arg1"))
					.append(new EzyInstruction("\t", "\n")
							.variable(EzyArray.class, "array")
							.equal()
							.clazz(EzyEntityFactory.class)
							.dot()
							.append("newArray()"));
		for(Object element : getElements()) {
			EzyInstruction instruction = null;
			if(element == null) {
				instruction = newInstructionByNull();
			}
			else if(element instanceof EzyField) {
				EzyField field = (EzyField)element;
				instruction = newInstruction(field, "");
			}
			else {
				EzyGetterMethod method = (EzyGetterMethod)element;
				instruction = newInstruction(method, "()");
			}
			methodBody.append(instruction);
		}
		methodBody.append(new EzyInstruction("\t", "\n")
				.answer()
				.append("array"));
			
		EzyFunction method = methodBody.function();
			
		return method.toString();
	}
	
	protected EzyInstruction newInstructionByNull() {
		return new EzyInstruction("\t", "\n")
				.append("array.add((java.lang.Object)null)");
	}
	
	protected EzyInstruction newInstruction(
			EzyReflectElement element, String valueExpSuffix) {
		EzyInstruction instruction = new EzyInstruction("\t", "\n")
				.append("array")
				.dot()
				.append("add")
				.bracketopen()
				.brackets(Object.class)
				.append("arg0.marshal(");
		com.tvd12.ezyfox.binding.annotation.EzyWriter wrt = 
				getWriterClass(element);
		if(wrt != null) {
			instruction
				.clazz(wrt.value(), true)
				.comma();
		}
		Class type = getElementType(element);
		String valueExpression = 
				"value." + element.getName() + valueExpSuffix;
		instruction
				.valueOf(type, valueExpression)
				.append(")")
				.bracketclose();
		return instruction;
	}
	
	@Override
	protected String getImplClassName() {
		return clazz.getName() + "$EzyArrayWriter$EzyAutoImpl$" + COUNT.incrementAndGet();
	}
	
	@Override
	protected boolean isDebug() {
		return debug;
	}
}

class EzyArrayWriterElementsFetcher extends EzyArrayElementsFetcher {
	
	@Override
	protected List<? extends EzyMethod> getMethodList(EzyClass clazz) {
		return clazz.getGetterMethods();
	}
	
	@Override
	protected List<? extends EzyMethod> getDeclaredMethods(EzyClass clazz) {
		return clazz.getDeclaredGetterMethods();
	}
	
	@Override
	protected EzyMethod newByFieldMethod(EzyMethod method) {
		return new EzyGetterMethod(method);
	}
	
	@Override
	protected boolean isValidAnnotatedMethod(EzyMethod method) {
		return method.getReturnType() != void.class;
	}
}