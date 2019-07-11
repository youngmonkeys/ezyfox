package com.tvd12.ezyfox.tool;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

import com.tvd12.ezyfox.function.EzyParamsFunction;
import com.tvd12.ezyfox.function.EzyVoidParamsFunction;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzyGenerics;

@SuppressWarnings({"rawtypes", "unchecked"})
public class EzyObjectInstanceRandom {

	protected final Map<Class<?>, Supplier<Object>> valueRandoms;
	
	protected static final char[] DEFAULT_CHARACTERS = new char[] {
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			'1', '2', '3', '4', '5', '6', '7', '8', '9'
	};
	
	protected static final String[] DEFAULT_STRINGS = new String[] {
			"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
			"1", "2", "3", "4", "5", "6", "7", "8", "9"
	};
	
	public EzyObjectInstanceRandom() {
		this.valueRandoms = defaultValueRandoms();
	}
	
	public Map<String, Object> randomObjectToMap(Class<?> clazz, boolean includeAllFields) {
		Object map = randomObject(
				clazz, 
				includeAllFields, 
				params -> new HashMap<>(), 
				params -> randomValue((Class<?>)params[0]), 
				params -> {
					EzyField field = (EzyField)params[1];
					((Map)params[0]).put(field.getName(), params[2]);
				});
		return (Map) map;
	}
	
	public Object randomValue(Class<?> valueType) {
		Object randomValue = randomValue(valueType, params ->
			randomObjectToMap((Class<?>)params[0], false)
		);
		return randomValue;
	}
	
	public <T> T randomObject(Class<T> clazz, boolean includeAllFields) {
		Object instance = randomObject(
				clazz, 
				includeAllFields, 
				params -> ((EzyClass)params[0]).newInstance(), 
				params -> randomObjectValue((Class<?>)params[0]), 
				params -> {
					EzyField field = (EzyField)params[1];
					field.getField().setAccessible(true);
					field.set(params[0], params[2]);
				});
		return (T)instance;
	}
	
	public <T> List<T> randomObjectList(Class<T> itemClass, int size, boolean includeAllFields) {
		List<T> list = new ArrayList<>();
		for(int i = 0 ; i < size ; ++i)
			list.add(randomObject(itemClass, includeAllFields));
		return list;
	}
	
	public Object randomObjectValue(Class<?> valueType) {
		Object randomValue = randomValue(valueType, params ->
			randomObject((Class<?>)params[0], false)
		);
		return randomValue;
	}
	
	public Object randomObject(
			Class<?> clazz, 
			boolean includeAllFields,
			EzyParamsFunction<Object> newInstanceFunc,
			EzyParamsFunction<Object> randomValueFunc, EzyVoidParamsFunction setValueFunc) {
		EzyClass classProxy = new EzyClass(clazz);
		List<EzyField> fields = includeAllFields 
				? classProxy.getFields() 
				: classProxy.getDeclaredFields();
		Object instance = newInstanceFunc.apply(classProxy);
		for(EzyField field : fields) {
			Field javaField = field.getField();
			if(Modifier.isStatic(javaField.getModifiers()))
				continue;
			Class<?> fieldType = javaField.getType();
			Object randomValue = null;
			if(fieldType.isAssignableFrom(Map.class))
				randomValue = randomMapValue(field.getGenericType());
			else if(fieldType.isAssignableFrom(List.class))
				randomValue = randomCollectionValue(field.getGenericType());
			else if(fieldType.isAssignableFrom(Set.class))
				randomValue = randomCollectionValue(field.getGenericType());
			else if(fieldType.isAssignableFrom(Collection.class))
				randomValue = randomCollectionValue(field.getGenericType());
			else
				randomValue = randomValueFunc.apply(fieldType);
			setValueFunc.apply(instance, field, randomValue);
		}
		return instance;
	}
	
	public Object randomValue(Class<?> valueType, EzyParamsFunction<Object> randomObjectFunc) {
		Object randomValue = null;
		Supplier<Object> random = valueRandoms.get(valueType);
		if(random != null)
			randomValue = random.get();
		else if(valueType.isArray())
			randomValue = randomArray(valueType.getComponentType());
		else if(valueType.isEnum())
			randomValue = randomEnumValue(valueType);
		else
			randomValue = randomObjectFunc.apply(valueType);
		return randomValue;
	}
	
	protected Object randomMapValue(Type mapType) {
		try {
			Object map = randomMapValue0(mapType);
			return map;
		}
		catch(Exception e) {
			return new HashMap<>();
		}
	}
	
	protected Object randomMapValue0(Type mapType) {
		Class[] keyValueTypes = EzyGenerics.getTwoGenericClassArguments(mapType);
		Random random = new Random();
		int maxEntries = 1 + random.nextInt(5);
		Map<Object, Object> map = new HashMap<>();
		for(int i = 0 ; i < maxEntries ; ++i) {
			Object key = randomValue(keyValueTypes[0]);
			Object value = randomValue(keyValueTypes[1]);
			map.put(key, value);
		}
		return map;
	}
	
	protected Object randomCollectionValue(Type collectionType) {
		try {
			Object collection = randomCollectionValue0(collectionType);
			return collection;
		}
		catch(Exception e) {
			return new ArrayList<>();
		}
	}
	
	protected Object randomCollectionValue0(Type collectionType) {
		Class itemType = EzyGenerics.getOneGenericClassArgument(collectionType);
		Random random = new Random();
		int maxItems = 1 + random.nextInt(5);
		Collection<Object> collection = null;
		ParameterizedType parameterizedType = (ParameterizedType)collectionType;
		Class<?> rawType = (Class<?>) parameterizedType.getRawType();
		if(rawType.isAssignableFrom(Set.class))
			collection = new HashSet<>();
		else
			collection = new ArrayList<>();
		for(int i = 0 ; i < maxItems ; ++i) {
			Object value = randomValue(itemType);
			collection.add(value);
		}
		return collection;
	}
	
	protected Object randomArray(Class<?> itemType) {
		Random random = new Random();
		int maxLength = 1 + random.nextInt(5);
		Object array = Array.newInstance(itemType, maxLength);
		for(int i = 0 ; i < maxLength ; ++i) {
			Object itemValue = randomValue(itemType);
			Array.set(array, i, itemValue);
		}
		return array;
	}
	
	protected Object randomEnumValue(Class<?> enumClass) {
		try {
			Object value = randomEnumValue0(enumClass);
			return value;
		}
		catch(Exception e) {
			return "";
		}
	}
	
	protected Object randomEnumValue0(Class<?> enumClass) throws Exception {
		Field field = enumClass.getDeclaredField("$VALUES");
		field.setAccessible(true);
		Object[] values = (Object[])field.get(null);
		if(values.length == 0)
			return "";
		Random random = new Random();
		int index = random.nextInt(values.length);
		Object value = values[index];
		return value;
	}
	
	protected Map<Class<?>, Supplier<Object>> defaultValueRandoms() {
		Map<Class<?>, Supplier<Object>> randoms = new HashMap<>();
		randoms.put(boolean.class, () -> {
			Random random = new Random();
			Object value = random.nextBoolean();
			return value;
		});
		randoms.put(byte.class, () -> {
			Random random = new Random();
			Object value = (byte)random.nextInt(125);
			return value;
		});
		randoms.put(char.class, () -> {
			Random random = new Random();
			int index = random.nextInt(DEFAULT_CHARACTERS.length);
			Object value = DEFAULT_CHARACTERS[index];
			return value;
		});
		randoms.put(double.class, () -> {
			Random random = new Random();
			Object value = (double)random.nextInt(125);
			return value;
		});
		randoms.put(float.class, () -> {
			Random random = new Random();
			Object value = (float)random.nextInt(125);
			return value;
		});
		randoms.put(int.class, () -> {
			Random random = new Random();
			Object value = random.nextInt(125);
			return value;
		});
		randoms.put(long.class, () -> {
			Random random = new Random();
			Object value = (long)random.nextInt(125);
			return value;
		});
		randoms.put(short.class, () -> {
			Random random = new Random();
			Object value = (short)random.nextInt(125);
			return value;
		});
		randoms.put(Boolean.class, () -> {
			Random random = new Random();
			Object value = random.nextBoolean();
			return value;
		});
		randoms.put(Byte.class, () -> {
			Random random = new Random();
			Object value = (byte)random.nextInt(125);
			return value;
		});
		randoms.put(Character.class, () -> {
			Random random = new Random();
			int index = random.nextInt(DEFAULT_CHARACTERS.length);
			Object value = DEFAULT_CHARACTERS[index];
			return value;
		});
		randoms.put(Double.class, () -> {
			Random random = new Random();
			Object value = (double)random.nextInt(125);
			return value;
		});
		randoms.put(Float.class, () -> {
			Random random = new Random();
			Object value = (float)random.nextInt(125);
			return value;
		});
		randoms.put(Integer.class, () -> {
			Random random = new Random();
			Object value = random.nextInt(125);
			return value;
		});
		randoms.put(Long.class, () -> {
			Random random = new Random();
			Object value = (long)random.nextInt(125);
			return value;
		});
		randoms.put(Short.class, () -> {
			Random random = new Random();
			Object value = (short)random.nextInt(125);
			return value;
		});
		randoms.put(String.class, () -> {
			Random random = new Random();
			int randomLength = 3 + random.nextInt(DEFAULT_STRINGS.length - 3);
			StringBuilder builder = new StringBuilder();
			for(int i = 0 ; i < randomLength ; ++i) {
				int index = random.nextInt(DEFAULT_STRINGS.length);
				builder.append(DEFAULT_STRINGS[index]);
			}
			return builder.toString();
		});
		randoms.put(Date.class, () -> {
			Date now = new Date();
			long nowMilis = now.getTime();
			Random random = new Random();
			int nextDays = 1 + random.nextInt(5);
			long nextMilis = nowMilis + nextDays * 24 * 60 * 60 * 1000;
			Date nextTime = new Date(nextMilis);
			return nextTime;
		});
		randoms.put(LocalDate.class, () -> {
			return LocalDate.now();
		});
		randoms.put(LocalDateTime.class, () -> {
			return LocalDateTime.now();
		});
		return randoms;
	}
}
