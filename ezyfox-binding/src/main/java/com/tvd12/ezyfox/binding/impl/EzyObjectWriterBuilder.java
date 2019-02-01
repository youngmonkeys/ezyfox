package com.tvd12.ezyfox.binding.impl;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.tvd12.ezyfox.asm.EzyFunction;
import com.tvd12.ezyfox.asm.EzyInstruction;
import com.tvd12.ezyfox.binding.EzyAccessType;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzyGetterMethod;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.ezyfox.reflect.EzyReflectElement;

import lombok.Setter;

@SuppressWarnings("rawtypes")
public class EzyObjectWriterBuilder 
		extends EzyAbstractWriterBuilder {

	@Setter
	protected static boolean debug;
	protected static final AtomicInteger COUNT = new AtomicInteger(0);

	public EzyObjectWriterBuilder(EzyClass clazz) {
		super(clazz);
	}
	
	@Override
	protected int getAccessType(EzyClass clazz) {
		EzyObjectBinding ann = clazz.getAnnotation(EzyObjectBinding.class);
		return ann == null ? EzyAccessType.ALL : ann.accessType();
	}
	
	@Override
	protected EzyElementsFetcher newElementsFetcher() {
		return new EzyObjectWriterElementsFetcher();
	}
	
	@Override
	protected String makeImplMethodContent(EzyMethod writeMethod) {
		EzyFunction.EzyBody methodBody = new EzyFunction(writeMethod)
				.modifier("protected")
				.body()
					.append(new EzyInstruction("\t", "\n")
							.variable(clazz.getClazz(), "object")
							.equal()
							.cast(clazz.getClazz(), "arg1")
							)
					.append(new EzyInstruction("\t", "\n")
							.variable(EzyObjectBuilder.class, "builder")
							.equal()
							.clazz(EzyEntityFactory.class)
							.dot()
							.append("newObjectBuilder()")
							);
		for(Object element : getElements()) {
			methodBody.append(newInstructionByElement(element));
		}
		methodBody.append(new EzyInstruction("\t", "\n")
				.answer()
				.append("builder.build()"));
		
		EzyFunction method = methodBody.function();
		
		return method.toString();
	}
	
	protected EzyInstruction newInstructionByElement(Object element) {
		if(element instanceof EzyField)
			return newInstructionByField((EzyField)element);
		return newInstructionByMethod((EzyMethod)element);
	}
	
	protected EzyInstruction newInstructionByField(EzyField field) {
		return newInstruction(field, "");
	}
	
	protected EzyInstruction newInstructionByMethod(EzyMethod method) {
		return newInstruction(method, "()");
	}
	
	protected EzyInstruction newInstruction(
			EzyReflectElement element, String valueExpSuffix) {
		EzyInstruction instruction = new EzyInstruction("\t", "\n")
				.append("builder")
				.dot()
				.append("append")
				.bracketopen()
				.string(getKey(element))
				.comma()
				.brackets(Object.class)
				.append("arg0.marshal(");
		com.tvd12.ezyfox.binding.annotation.EzyWriter wrt = getWriterClass(element);
		if(wrt != null) {
			instruction
				.clazz(wrt.value(), true)
				.comma();
		}
		Class type = getElementType(element);
		instruction
				.valueOf(type, "object." + element.getName() + valueExpSuffix)
				.append(")")
				.bracketclose();
		return instruction;
	}
	
	@Override
	protected String getImplClassName() {
		return clazz.getName() + "$EzyObjectWriter$EzyAutoImpl$" + COUNT.incrementAndGet();
	}
	
	@Override
	protected boolean isDebug() {
		return debug;
	}
}

class EzyObjectWriterElementsFetcher extends EzyObjectElementsFetcher {
	
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
