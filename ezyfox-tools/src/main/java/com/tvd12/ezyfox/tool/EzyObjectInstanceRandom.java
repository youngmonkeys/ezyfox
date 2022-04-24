package com.tvd12.ezyfox.tool;

import com.tvd12.ezyfox.function.EzyParamsFunction;
import com.tvd12.ezyfox.function.EzyVoidParamsFunction;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzyFields;
import com.tvd12.ezyfox.reflect.EzyGenerics;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;

@SuppressWarnings({"rawtypes", "unchecked"})
public class EzyObjectInstanceRandom {

    protected static final char[] DEFAULT_CHARACTERS = new char[]{
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
        'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
        'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };
    protected static final String[] DEFAULT_STRINGS = new String[]{
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
        "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
        "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
        "1", "2", "3", "4", "5", "6", "7", "8", "9"
    };
    protected final Map<Class<?>, Supplier<Object>> valueRandoms;

    public EzyObjectInstanceRandom() {
        this.valueRandoms = defaultValueRandoms();
    }

    public Map<String, Object> randomObjectToMap(
        Class<?> clazz,
        boolean includeAllFields
    ) {
        Object map = randomObject(
            clazz,
            includeAllFields,
            params -> new HashMap<>(),
            params -> randomValue((Class<?>) params[0]),
            params -> {
                EzyField field = (EzyField) params[1];
                ((Map) params[0]).put(field.getName(), params[2]);
            });
        return (Map) map;
    }

    public Object randomValue(Class<?> valueType) {
        return randomValue(valueType, params ->
            randomObjectToMap((Class<?>) params[0], false)
        );
    }

    public Object randomValue(
        Class<?> valueType,
        EzyParamsFunction<Object> randomObjectFunc
    ) {
        Object randomValue;
        Supplier<Object> random = valueRandoms.get(valueType);
        if (random != null) {
            randomValue = random.get();
        } else if (valueType.isArray()) {
            randomValue = randomArray(valueType.getComponentType());
        } else if (valueType.isEnum()) {
            randomValue = randomEnumValue(valueType);
        } else {
            randomValue = randomObjectFunc.apply(valueType);
        }
        return randomValue;
    }

    public <T> T randomObject(Class<T> clazz, boolean includeAllFields) {
        Object instance = randomObject(
            clazz,
            includeAllFields,
            params -> ((EzyClass) params[0]).newInstance(),
            params -> randomObjectValue((Class<?>) params[0]),
            params -> {
                EzyField field = (EzyField) params[1];
                field.getField().setAccessible(true);
                field.set(params[0], params[2]);
            });
        return (T) instance;
    }

    public Object randomObject(
        Class<?> clazz,
        boolean includeAllFields,
        EzyParamsFunction<Object> newInstanceFunc,
        EzyParamsFunction<Object> randomValueFunc,
        EzyVoidParamsFunction setValueFunc
    ) {
        EzyClass classProxy = new EzyClass(clazz);
        List<EzyField> fields = includeAllFields
            ? classProxy.getFields()
            : classProxy.getDeclaredFields();
        Object instance = newInstanceFunc.apply(classProxy);
        for (EzyField field : fields) {
            Field javaField = field.getField();
            if (Modifier.isStatic(javaField.getModifiers())) {
                continue;
            }
            Class<?> fieldType = javaField.getType();
            Object randomValue;
            if (fieldType.isAssignableFrom(Map.class)) {
                randomValue = randomMapValue(field.getGenericType());
            } else if (fieldType.isAssignableFrom(List.class)) {
                randomValue = randomCollectionValue(field.getGenericType());
            } else if (fieldType.isAssignableFrom(Set.class)) {
                randomValue = randomCollectionValue(field.getGenericType());
            } else if (fieldType.isAssignableFrom(Collection.class)) {
                randomValue = randomCollectionValue(field.getGenericType());
            } else {
                randomValue = randomValueFunc.apply(fieldType);
            }
            setValueFunc.apply(instance, field, randomValue);
        }
        return instance;
    }

    public <T> List<T> randomObjectList(
        Class<T> itemClass,
        int size,
        boolean includeAllFields
    ) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            list.add(randomObject(itemClass, includeAllFields));
        }
        return list;
    }

    public Object randomObjectValue(Class<?> valueType) {
        return randomValue(valueType, params ->
            randomObject((Class<?>) params[0], false)
        );
    }

    public Object randomMapValue(Type mapType) {
        try {
            return randomMapValue0(mapType);
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    protected Object randomMapValue0(Type mapType) {
        Class[] keyValueTypes = EzyGenerics.getTwoGenericClassArguments(mapType);
        Random random = new Random();
        int maxEntries = 1 + random.nextInt(5);
        Map<Object, Object> map = new HashMap<>();
        for (int i = 0; i < maxEntries; ++i) {
            Object key = randomValue(keyValueTypes[0]);
            Object value = randomValue(keyValueTypes[1]);
            map.put(key, value);
        }
        return map;
    }

    public Object randomCollectionValue(Type collectionType) {
        try {
            return randomCollectionValue0(collectionType);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    protected Object randomCollectionValue0(Type collectionType) {
        Class itemType = EzyGenerics.getOneGenericClassArgument(collectionType);
        Random random = new Random();
        int maxItems = 1 + random.nextInt(5);
        Collection<Object> collection;
        ParameterizedType parameterizedType = (ParameterizedType) collectionType;
        Class<?> rawType = (Class<?>) parameterizedType.getRawType();
        if (rawType.isAssignableFrom(Set.class)) {
            collection = new HashSet<>();
        } else {
            collection = new ArrayList<>();
        }
        for (int i = 0; i < maxItems; ++i) {
            Object value = randomValue(itemType);
            collection.add(value);
        }
        return collection;
    }

    public Object randomArray(Class<?> itemType) {
        Random random = new Random();
        int maxLength = 1 + random.nextInt(5);
        Object array = Array.newInstance(itemType, maxLength);
        for (int i = 0; i < maxLength; ++i) {
            Object itemValue = randomValue(itemType);
            Array.set(array, i, itemValue);
        }
        return array;
    }

    public Object randomEnumValue(Class<?> enumClass) {
        try {
            return randomEnumValue0(enumClass);
        } catch (Exception e) {
            return "";
        }
    }

    protected Object randomEnumValue0(Class<?> enumClass) throws Exception {
        Field field = enumClass.getDeclaredField("$VALUES");
        field.setAccessible(true);
        Object[] values = (Object[]) field.get(null);
        if (values.length == 0) {
            return "";
        }
        Random random = new Random();
        int index = random.nextInt(values.length);
        return values[index];
    }

    public String randomValueScript(Class<?> type) {
        String script;
        if (Map.class.isAssignableFrom(type)) {
            script = "new HashMap<>()";
        } else if (List.class.isAssignableFrom(type)) {
            script = "new ArrayList<>()";
        } else if (Set.class.isAssignableFrom(type)) {
            script = "new HashSet<>()";
        } else if (Collection.class.isAssignableFrom(type)) {
            script = "new ArrayList<>()";
        } else if (BigDecimal.class.isAssignableFrom(type)) {
            script = "new BigDecimal(10)";
        } else if (BigInteger.class.isAssignableFrom(type)) {
            script = "new BigInteger(10)";
        } else if (java.util.Date.class.isAssignableFrom(type)) {
            script = "new Date()";
        } else if (java.sql.Date.class.isAssignableFrom(type)) {
            script = "new java.sql.Date()";
        } else if (LocalDate.class.isAssignableFrom(type)) {
            script = "LocalDate.now()";
        } else if (LocalDateTime.class.isAssignableFrom(type)) {
            script = "LocalDateTime.now()";
        } else {
            script = randomValue(type).toString();
        }
        if (byte.class.equals(type) || Byte.class.equals(type)) {
            script = "(byte)" + script;
        } else if (char.class.equals(type) || Character.class.equals(type)) {
            script = "'" + script + "'";
        } else if (double.class.equals(type) || Double.class.equals(type)) {
            script = script + "D";
        } else if (float.class.equals(type) || Float.class.equals(type)) {
            script = script + "F";
        } else if (long.class.equals(type) || Long.class.equals(type)) {
            script = script + "L";
        }
        if (short.class.equals(type) || Short.class.equals(type)) {
            script = "(short)" + script;
        } else if (String.class.equals(type)) {
            script = EzyStrings.quote(script);
        } else if (type.isArray()) {
            script = randomArrayScript(type.getComponentType());
        } else if (isCustomerClass(type)) {
            script = "new" + type.getSimpleName() + "()";
        }

        return script;
    }

    public String randomArrayScript(Class<?> itemType) {
        return "new " +
            itemType.getSimpleName() +
            "[] {" +
            randomValueScript(itemType) +
            "}";
    }

    public String randomObjectFuncScript(Class<?> clazz) {
        return randomObjectFuncScript(clazz, true);
    }

    public String randomObjectFuncScript(Class<?> clazz, boolean allFields) {
        String bodyScript = randomObjectScript(clazz, null, allFields);
        return "protected " +
            clazz.getSimpleName() + " new" +
            clazz.getSimpleName() + "() {\n" +
            EzyStringTool.tabAll(bodyScript, 1) +
            "\n}";
    }

    public String randomObjectScript(Class<?> clazz, Set<Class<?>> customClasses) {
        return randomObjectScript(clazz, customClasses, true);
    }

    public String randomObjectScript(
        Class<?> clazz, Set<Class<?>> customClasses, boolean allFields) {
        if (Throwable.class.isAssignableFrom(clazz)) {
            return randomExceptionScript();
        }
        if (clazz.isInterface()) {
            return randomInterfaceObjectScript(clazz);
        }
        if (Modifier.isAbstract(clazz.getModifiers())) {
            return randomAbstractObjectScript(clazz);
        }
        return randomClassObjectScript(clazz, customClasses, allFields);
    }

    public String randomExceptionScript() {
        return "Exception e = new Exception(\"test\");" +
            "\nreturn e;";
    }

    public String randomInterfaceObjectScript(Class<?> intf) {
        return intf.getSimpleName() +
            " v = mock(" +
            intf.getSimpleName() + ".class);" +
            "\nreturn v;";
    }

    public String randomAbstractObjectScript(Class<?> clazz) {
        return clazz.getSimpleName() +
            " v = spy(" +
            clazz.getSimpleName() + ".class);" +
            "\nreturn v;";
    }

    public String randomClassObjectScript(
        Class<?> clazz,
        Set<Class<?>> customClasses,
        boolean allFields
    ) {
        StringBuilder builder = new StringBuilder()
            .append(clazz.getSimpleName())
            .append(" v = new ")
            .append(clazz.getSimpleName()).append("();\n");
        List<Field> fields = allFields
            ? EzyFields.getFields(clazz) : EzyFields.getDeclaredFields(clazz);
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            EzyField f = new EzyField(field);
            Class<?> fieldType = field.getType();
            String randomValue = randomValueScript(fieldType);
            builder.append("v.")
                .append(f.getSetterMethod())
                .append("(").append(randomValue).append(");");
            if (isCustomerClass(fieldType)) {
                if (customClasses != null) {
                    customClasses.add(fieldType);
                }
                builder.append(" // customer class");
            }
            builder.append("\n");
        }
        return builder.append("return v;").toString();
    }

    protected boolean isCustomerClass(Class<?> clazz) {
        return EzyToolTypes.isCustomerClass(clazz);
    }

    @SuppressWarnings("MethodLength")
    protected Map<Class<?>, Supplier<Object>> defaultValueRandoms() {
        Map<Class<?>, Supplier<Object>> randoms = new HashMap<>();
        randoms.put(boolean.class, () -> {
            Random random = new Random();
            return random.nextBoolean();
        });
        randoms.put(byte.class, () -> {
            Random random = new Random();
            return (byte) random.nextInt(125);
        });
        randoms.put(char.class, () -> {
            Random random = new Random();
            int index = random.nextInt(DEFAULT_CHARACTERS.length);
            return DEFAULT_CHARACTERS[index];
        });
        randoms.put(double.class, () -> {
            Random random = new Random();
            return (double) random.nextInt(125);
        });
        randoms.put(float.class, () -> {
            Random random = new Random();
            return (float) random.nextInt(125);
        });
        randoms.put(int.class, () -> {
            Random random = new Random();
            return random.nextInt(125);
        });
        randoms.put(long.class, () -> {
            Random random = new Random();
            return (long) random.nextInt(125);
        });
        randoms.put(short.class, () -> {
            Random random = new Random();
            return (short) random.nextInt(125);
        });
        randoms.put(Boolean.class, () -> {
            Random random = new Random();
            return random.nextBoolean();
        });
        randoms.put(Byte.class, () -> {
            Random random = new Random();
            return (byte) random.nextInt(125);
        });
        randoms.put(Character.class, () -> {
            Random random = new Random();
            int index = random.nextInt(DEFAULT_CHARACTERS.length);
            return DEFAULT_CHARACTERS[index];
        });
        randoms.put(Double.class, () -> {
            Random random = new Random();
            return (double) random.nextInt(125);
        });
        randoms.put(Float.class, () -> {
            Random random = new Random();
            return (float) random.nextInt(125);
        });
        randoms.put(Integer.class, () -> {
            Random random = new Random();
            return random.nextInt(125);
        });
        randoms.put(Long.class, () -> {
            Random random = new Random();
            return (long) random.nextInt(125);
        });
        randoms.put(Short.class, () -> {
            Random random = new Random();
            return (short) random.nextInt(125);
        });
        randoms.put(String.class, () -> {
            Random random = new Random();
            int randomLength = 3 + random.nextInt(DEFAULT_STRINGS.length - 3);
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < randomLength; ++i) {
                int index = random.nextInt(DEFAULT_STRINGS.length);
                builder.append(DEFAULT_STRINGS[index]);
            }
            return builder.toString();
        });
        randoms.put(Number.class, () -> {
            Random random = new Random();
            return random.nextInt(125);
        });
        randoms.put(BigDecimal.class, () -> new BigDecimal(10L));
        randoms.put(BigInteger.class, () -> new BigInteger("10"));
        randoms.put(Date.class, () -> {
            Date now = new Date();
            long nowMillis = now.getTime();
            Random random = new Random();
            int nextDays = 1 + random.nextInt(5);
            long nextMillis = nowMillis + nextDays * 24 * 60 * 60 * 1000;
            return new Date(nextMillis);
        });
        randoms.put(java.sql.Date.class, () -> {
            Date now = new Date();
            long nowMillis = now.getTime();
            Random random = new Random();
            int nextDays = 1 + random.nextInt(5);
            long nextMillis = nowMillis + nextDays * 24 * 60 * 60 * 1000;
            return new java.sql.Date(nextMillis);
        });
        randoms.put(LocalDate.class, LocalDate::now);
        randoms.put(LocalDateTime.class, LocalDateTime::now);
        return randoms;
    }
}
