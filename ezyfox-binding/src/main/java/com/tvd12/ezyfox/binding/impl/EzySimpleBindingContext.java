package com.tvd12.ezyfox.binding.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.EzyBindingContextBuilder;
import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.EzyUnwrapper;
import com.tvd12.ezyfox.binding.EzyWriter;
import com.tvd12.ezyfox.binding.annotation.EzyArrayBinding;
import com.tvd12.ezyfox.binding.annotation.EzyConfiguration;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.binding.annotation.EzyPackagesScan;
import com.tvd12.ezyfox.binding.annotation.EzyReaderImpl;
import com.tvd12.ezyfox.binding.annotation.EzyTemplateImpl;
import com.tvd12.ezyfox.binding.annotation.EzyWriterImpl;
import com.tvd12.ezyfox.binding.writer.EzyMapArrayWriter;
import com.tvd12.ezyfox.binding.writer.EzyMapObjectWriter;
import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyClasses;
import com.tvd12.ezyfox.reflect.EzyPackages;
import com.tvd12.ezyfox.reflect.EzyReflection;
import com.tvd12.ezyfox.util.EzyLoggable;

@SuppressWarnings({ "rawtypes" })
public class EzySimpleBindingContext
		extends EzyLoggable
		implements EzyBindingContext {

	protected Set<EzyWriter> writers = new HashSet<>();
	protected Set<EzyReader> readers = new HashSet<>();
	protected Map<Class, EzyWriter> writersByObjectType = new ConcurrentHashMap<>();
	protected Map<Class, EzyReader> readersByObjectType = new ConcurrentHashMap<>();
	protected Map<Class, EzyUnwrapper> unwrappersByObjectType = new ConcurrentHashMap<>();
	
	@Override
	public EzyMarshaller newMarshaller() {
		EzySimpleMarshaller marshaller = new EzySimpleMarshaller();
		marshaller.addWriters(writers);
		marshaller.addWriters(writersByObjectType);
		return marshaller;
	}
	
	@Override
	public EzyUnmarshaller newUnmarshaller() {
		EzySimpleUnmarshaller unmarshaller = new EzySimpleUnmarshaller();
		unmarshaller.addReaders(readers);
		unmarshaller.addReaders(readersByObjectType);
		unmarshaller.addUnwrappers(unwrappersByObjectType);
		return unmarshaller;
	}
	
	@Override
	public void addReader(EzyReader reader) {
		readers.add(reader);
	}
	
	@Override
	public void addWriter(EzyWriter writer) {
		writers.add(writer);
	}
	
	@Override
	public void addTemplate(Object template) {
		if(template instanceof EzyReader)
			addReader((EzyReader)template);
		if(template instanceof EzyWriter)
			addWriter((EzyWriter)template);
	}
	
	@Override
	public void bindReader(Class clazz, EzyReader reader) {
		readersByObjectType.put(clazz, reader);
	}
	
	@Override
	public void bindWriter(Class clazz, EzyWriter writer) {
		writersByObjectType.put(clazz, writer);
	}
	
	@Override
	public void bindTemplate(Class clazz, Object template) {
		if(template instanceof EzyReader)
			bindReader(clazz, (EzyReader)template);
		if(template instanceof EzyWriter)
			bindWriter(clazz, (EzyWriter)template);
		if(template instanceof EzyUnwrapper)
			bindUnwrapper(clazz, (EzyUnwrapper)template);
	}
	
	public void bindUnwrapper(Class clazz, EzyUnwrapper unwrapper) {
		unwrappersByObjectType.put(clazz, unwrapper);
	}
	
	public static EzyBindingContextBuilder builder() {
		return new Builder();
	}
	
	public static class Builder implements EzyBindingContextBuilder {
		
		protected Set<EzyWriter> writers = new HashSet<>(); 
		protected Set<EzyReader> readers = new HashSet<>();
		protected Map<Class, EzyWriter> writersByObjectType = new HashMap<>();
		protected Map<Class, EzyReader> readersByObjectType = new HashMap<>();
		protected Map<Class, EzyUnwrapper> unwrappersByObjectType = new HashMap<>();
		
		protected Set<Class> writerImplClasses = new HashSet<>();
		protected Set<Class> readerImplClasses = new HashSet<>();
		protected Set<Class> objectBindingClasses = new HashSet<>();
		protected Set<Class> arrayBindingClasses = new HashSet<>();
		protected Set<Class> packagesScanClasses = new HashSet<>();
		protected Set<Class> configurationClasses = new HashSet<>();
		
		/* (non-Javadoc)
		 * @see com.tvd12.ezyfox.binding.impl.EzyBindingContextBuilder#scan(java.lang.String)
		 */
		@Override
		public EzyBindingContextBuilder scan(String packageName) {
			EzyReflection reflection = EzyPackages.scanPackage(packageName);
			this.addAllClasses(reflection);
			return this;
		}
		
		/* (non-Javadoc)
		 * @see com.tvd12.ezyfox.binding.impl.EzyBindingContextBuilder#scan(java.lang.String)
		 */
		@Override
		public EzyBindingContextBuilder scan(String... packageNames) {
			return scan(Sets.newHashSet(packageNames));
		}
		
		/* (non-Javadoc)
		 * @see com.tvd12.ezyfox.binding.impl.EzyBindingContextBuilder#scan(java.lang.Iterable)
		 */
		@Override
		public EzyBindingContextBuilder scan(Iterable<String> packageNames) {
			EzyReflection reflection = EzyPackages.scanPackages(packageNames);
			addAllClasses(reflection);
			return this;
		}
		
		/* (non-Javadoc)
		 * @see com.tvd12.ezyfox.binding.impl.EzyBindingContextBuilder#addClass(java.lang.Class)
		 */
		@Override
		@SuppressWarnings("unchecked")
		public EzyBindingContextBuilder addClass(Class clazz) {
			if(clazz.isAnnotationPresent(EzyArrayBinding.class))
				arrayBindingClasses.add(clazz);
			else
				objectBindingClasses.add(clazz);
			return this;
		}
		
		/* (non-Javadoc)
		 * @see com.tvd12.ezyfox.binding.impl.EzyBindingContextBuilder#addObjectBindingClass(java.lang.Class)
		 */
		@Override
		public EzyBindingContextBuilder addObjectBindingClass(Class clazz) {
			objectBindingClasses.add(clazz);
			return this;
		}
		
		/* (non-Javadoc)
		 * @see com.tvd12.ezyfox.binding.impl.EzyBindingContextBuilder#addArrayBindingClass(java.lang.Class)
		 */
		@Override
		public EzyBindingContextBuilder addArrayBindingClass(Class clazz) {
			arrayBindingClasses.add(clazz);
			return this;
		}
		
		/* (non-Javadoc)
		 * @see com.tvd12.ezyfox.binding.impl.EzyBindingContextBuilder#addClasses(java.lang.Class)
		 */
		@Override
		public EzyBindingContextBuilder addClasses(Class... classes) {
			return addClasses(Sets.newHashSet(classes));
		}
		
		/* (non-Javadoc)
		 * @see com.tvd12.ezyfox.binding.impl.EzyBindingContextBuilder#addClasses(java.lang.Iterable)
		 */
		@Override
		public EzyBindingContextBuilder addClasses(Iterable<Class> classes) {
			classes.forEach(this::addClass);
			return this;
		}
		
		/* (non-Javadoc)
		 * @see com.tvd12.ezyfox.binding.impl.EzyBindingContextBuilder#addTemplate(java.lang.Object)
		 */
		@Override
		public EzyBindingContextBuilder addTemplate(Object template) {
			if(template instanceof EzyWriter)
				writers.add((EzyWriter) template);
			if(template instanceof EzyReader)
				readers.add((EzyReader) template);
			return this;
		}
		
		/* (non-Javadoc)
		 * @see com.tvd12.ezyfox.binding.impl.EzyBindingContextBuilder#addTemplateClass(java.lang.Class)
		 */
		@Override
		public EzyBindingContextBuilder addTemplateClass(Class clazz) {
			if(EzyWriter.class.isAssignableFrom(clazz))
				writerImplClasses.add(clazz);
			if(EzyReader.class.isAssignableFrom(clazz))
				readerImplClasses.add(clazz);
			return this;
		}
		
		/* (non-Javadoc)
		 * @see com.tvd12.ezyfox.binding.impl.EzyBindingContextBuilder#addTemplateClasses(java.lang.Iterable)
		 */
		@Override
		public EzyBindingContextBuilder addTemplateClasses(Iterable<Class<?>> classes) {
			classes.forEach(this::addTemplateClass);
			return this;
		}
		
		/* (non-Javadoc)
		 * @see com.tvd12.ezyfox.binding.impl.EzyBindingContextBuilder#addTemplate(java.lang.Class, java.lang.Object)
		 */
		@Override
		public EzyBindingContextBuilder addTemplate(Class type, Object template) {
			if(template instanceof EzyWriter) {
				writers.add((EzyWriter) template);
				writersByObjectType.put(type, (EzyWriter) template);
			}
			if(template instanceof EzyReader) {
				readers.add((EzyReader) template);
				readersByObjectType.put(type, (EzyReader) template);
			}
			if(template instanceof EzyUnwrapper) {
				unwrappersByObjectType.put(type, (EzyUnwrapper)template);
			}
			return this;
		}
		
		/* (non-Javadoc)
		 * @see com.tvd12.ezyfox.binding.impl.EzyBindingContextBuilder#build()
		 */
		@Override
		public EzySimpleBindingContext build() {
			EzySimpleBindingContext context = new EzySimpleBindingContext();
			scanPackagesScanClasses();
			parseObjectBindingClasses();
			parseArrayBindingClasses();
			parseTemplateClasses();
			mapSubTypesToReaders();
			mapSubTypesToWriters();
			context.writers.add(EzyMapArrayWriter.getInstance());
			context.writers.add(EzyMapObjectWriter.getInstance());
			context.writers.addAll(writers);
			context.readers.addAll(readers);
			context.writersByObjectType.putAll(writersByObjectType);
			context.readersByObjectType.putAll(readersByObjectType);
			context.unwrappersByObjectType.putAll(unwrappersByObjectType);
			loadConfigurationClasses(context);
			return context;
		}
		
		private void parseObjectBindingClasses() {
			objectBindingClasses.forEach(this::parseObjectBindingClass);
		}
		
		private void parseArrayBindingClasses() {
			arrayBindingClasses.forEach(this::parseArrayBindingClass);
		}
		
		private void parseObjectBindingClass(Class<?> clazz) {
			EzyObjectBinding anno = clazz.getAnnotation(EzyObjectBinding.class);
			if(anno == null || anno.write()) {
				addTemplate(clazz, new EzyObjectWriterBuilder(new EzyClass(clazz)).build());
			}
			if(anno == null || anno.read()) {
				addTemplate(clazz, new EzyObjectReaderBuilder(new EzyClass(clazz)).build());
				addTemplate(clazz, new EzyObjectUnwrapperBuilder(new EzyClass(clazz)).build());
			}
		}
		
		private void parseArrayBindingClass(Class<?> clazz) {
			EzyArrayBinding anno = clazz.getAnnotation(EzyArrayBinding.class);
			if(anno == null || anno.write()) {
				addTemplate(clazz, new EzyArrayWriterBuilder(new EzyClass(clazz)).build());
			}
			if(anno == null || anno.read()) {
				addTemplate(clazz, new EzyArrayReaderBuilder(new EzyClass(clazz)).build());
				addTemplate(clazz, new EzyArrayUnwrapperBuilder(new EzyClass(clazz)).build());
			}
		}
		
		private void parseTemplateClasses() {
			writerImplClasses.forEach(this::parseTemplateClass);
			readerImplClasses.forEach(this::parseTemplateClass);
		}
		
		@SuppressWarnings("unchecked")
		private void parseTemplateClass(Class templateClass) {
			addTemplate(EzyClasses.newInstance(templateClass));
		}
		
		private void scanPackagesScanClasses() {
			packagesScanClasses.forEach(this::scanPackagesScanClass);
		}
		
		private void scanPackagesScanClass(Class<?> clazz) {
			scan(clazz.getAnnotation(EzyPackagesScan.class).value());
		}
		
		private void loadConfigurationClasses(EzyBindingContext context) {
			for(Class clazz : configurationClasses)
				loadConfigurationClass(clazz, context);
		}
		
		private void loadConfigurationClass(Class<?> clazz, EzyBindingContext context) {
			new EzySimpleConfigurationLoader(new EzyClass(clazz)).load(context);
		}
		
		private void mapSubTypesToWriters() {
			Set<Class> classes = new HashSet<>(writersByObjectType.keySet());
			for(Class clazz : classes)
				mapSubTypesToWriter(clazz, writersByObjectType.get(clazz));
		}
		
		private void mapSubTypesToWriter(Class clazz, EzyWriter writer) {
			Set<Class> subTypes = getSubTypes(clazz);
			for(Class subType : subTypes)
				writersByObjectType.put(subType, writer);
		}
		
		private void mapSubTypesToReaders() {
			Set<Class> classes = new HashSet<>(readersByObjectType.keySet());
			for(Class clazz : classes)
				mapSubTypesToReader(clazz, readersByObjectType.get(clazz));
		}
		
		private void mapSubTypesToReader(Class clazz, EzyReader writer) {
			Set<Class> subTypes = getSubTypes(clazz);
			for(Class subType : subTypes)
				readersByObjectType.put(subType, writer);
		}
		
		private Set<Class> getSubTypes(Class<?> clazz) {
			boolean includeSubTypes = false;
			Set<Class> answer = new HashSet<>();
			if(clazz.isAnnotationPresent(EzyObjectBinding.class)) {
				EzyObjectBinding anno = clazz.getAnnotation(EzyObjectBinding.class);
				includeSubTypes = anno.subTypes();
				answer.addAll(Sets.newHashSet(anno.subTypeClasses()));
			}
			else if(clazz.isAnnotationPresent(EzyArrayBinding.class)) {
				EzyArrayBinding anno = clazz.getAnnotation(EzyArrayBinding.class);
				includeSubTypes = anno.subTypes();
				answer.addAll(Sets.newHashSet(anno.subTypeClasses()));
			}
			if(!includeSubTypes)
				return new HashSet<>();
			if(!answer.isEmpty())
				return answer;
			return EzyClasses.flatSuperAndInterfaceClasses(clazz);
		}
		
		private void addAllClasses(EzyReflection reflection) {
			objectBindingClasses.addAll(
					reflection.getAnnotatedClasses(EzyObjectBinding.class));
			arrayBindingClasses.addAll(
					reflection.getAnnotatedClasses(EzyArrayBinding.class));
			writerImplClasses.addAll(
					reflection.getAnnotatedClasses(EzyWriterImpl.class));
			readerImplClasses.addAll(
					reflection.getAnnotatedClasses(EzyReaderImpl.class));
			addTemplateClasses(
					reflection.getAnnotatedClasses(EzyTemplateImpl.class));
			packagesScanClasses.addAll(
					reflection.getAnnotatedClasses(EzyPackagesScan.class));
			configurationClasses.addAll(
					reflection.getAnnotatedClasses(EzyConfiguration.class));
		}
		
	}
	
}
