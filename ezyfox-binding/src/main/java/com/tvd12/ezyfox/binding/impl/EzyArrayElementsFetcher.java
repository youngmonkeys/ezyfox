package com.tvd12.ezyfox.binding.impl;

import com.tvd12.ezyfox.binding.annotation.EzyArrayBinding;
import com.tvd12.ezyfox.binding.annotation.EzyIndex;
import com.tvd12.ezyfox.io.EzyMaps;
import com.tvd12.ezyfox.reflect.*;

import java.util.*;

import static com.tvd12.ezyfox.binding.EzyAccessType.NONE;

//================== fetcher by array ================
public abstract class EzyArrayElementsFetcher extends EzyAbstractElementsFetcher {

    @Override
    protected List<Object> doGetElements(EzyClass clazz, int accessType) {
        String[] indexes = getIndexes(clazz);
        if (indexes.length > 0) {
            return getElementsByCustomIndexes(clazz, indexes);
        } else if (accessType == NONE) {
            return getAnnotatedElements(clazz);
        } else {
            return getElementsByNativeIndexes(clazz, accessType);
        }
    }

    private List<Object> getElementsByNativeIndexes(EzyClass clazz, int accessType) {
        return getElementsByNativeIndexes(
            getFields(clazz, accessType),
            getMethods(clazz, accessType));
    }

    private List<Object> getElementsByNativeIndexes(
        List<EzyField> fields, List<? extends EzyMethod> methods) {

        List<Object> elements = new ArrayList<>();
        List<EzyMethod> remainMethods = new ArrayList<>(methods);

        for (EzyField field : fields) {
            logger.debug("scan field {}", field.getName());

            EzyMethod method = methodsByFieldName.get(field.getName());

            if (method != null) {
                if (isValidGenericMethod(method)) {
                    elements.add(method);
                } else {
                    elements.add(null);
                    logger.debug("unknown generic type of method {}, ignore it", method.getName());
                }
                remainMethods.remove(method);
            } else if (field.isPublic()) {
                if (isValidGenericField(field)) {
                    elements.add(field);
                } else {
                    logger.debug("unknown generic type of field {}, ignore it", field.getName());
                    elements.add(null);
                }
            } else {
                logger.debug("field {} has not getter/setter, ignore it", field.getName());
            }
        }
        for (EzyMethod method : remainMethods) {
            logger.debug("scan method {}", method.getName());

            if (isValidGenericMethod(method)) {
                elements.add(method);
            } else {
                logger.debug("unknown generic type of method {}, ignore it", method.getName());
            }
        }
        return elements;
    }

    private List<Object> getElementsByCustomIndexes(EzyClass clazz, String[] indexes) {
        return getElementsByCustomIndexes(
            getFieldsByName(clazz),
            indexes);
    }

    private List<Object> getElementsByCustomIndexes(Map<String, EzyField> fieldsByName, String[] indexes) {
        List<Object> elements = new ArrayList<>();

        for (String property : indexes) {
            logger.debug("scan property {}", property);

            EzyMethod method = methodsByFieldName.get(property);

            if (method != null) {
                if (!isValidGenericMethod(method)) {
                    logger.debug("unknown generic type of method {}, ignore it", method.getName());
                } else {
                    elements.add(method);
                    continue;
                }
            } else {
                logger.debug("has no getter/setter method map to property {}", property);
            }

            EzyField field = fieldsByName.get(property);

            if (field != null) {
                if (!field.isPublic()) {
                    logger.debug("has no public field map to property {}", property);
                } else if (isValidGenericField(field)) {
                    elements.add(field);
                } else {
                    logger.debug("unknown generic type of field {}, ignore it", field.getName());
                }
            } else {
                elements.add(null);
                logger.debug("nothing map to property {}, ignore it", property);
            }

        }
        return elements;
    }

    private List<Object> getAnnotatedElements(EzyClass clazz) {
        return getAnnotatedElements(
            clazz,
            getAnnotedFields(clazz),
            getAnnotatedMethods(clazz));
    }

    private List<Object> getAnnotatedElements(
        EzyClass clazz,
        List<EzyField> fields,
        List<? extends EzyMethod> methods
    ) {
        List<Object> elements = new ArrayList<>();

        for (EzyField field : fields) {
            logger.debug("scan field {}", field.getName());

            EzyMethod method = methodsByFieldName.get(field.getName());

            if (method != null) {
                if (!isValidGenericMethod(method)) {
                    logger.debug("unknown generic type of method {}, ignore it", method.getName());
                } else {
                    elements.add(method);
                    methods.remove(method);
                }
            } else if (field.isPublic()) {
                if (!isValidGenericField(field)) {
                    logger.debug("unknown generic type of field {}, ignore it", field.getName());
                } else {
                    elements.add(field);
                }
            } else {
                logger.debug("field {} has not getter/setter, ignore it", field.getName());
            }
        }
        for (EzyMethod method : methods) {
            logger.debug("scan method {}", method.getName());

            if (isValidGenericMethod(method)) {
                elements.add(method);
            } else {
                logger.debug("unknown generic type of method {}, ignore it", method.getName());
            }
        }

        Object max = Collections.max(
            elements,
            Comparator.comparingInt(e -> getIndex(clazz, e))
        );

        int maxIndex = getIndex(clazz, max);

        Map<Integer, Object> elementsByIndex = EzyMaps.newHashMap(elements, e -> getIndex(clazz, e));
        List<Object> answer = new ArrayList<>();

        for (int index = 0; index <= maxIndex; index++) {
            if (elementsByIndex.containsKey(index)) {
                answer.add(elementsByIndex.get(index));
            } else {
                answer.add(null);
                logger.debug("has not property at index {}, so at this index value is null", index);
            }
        }
        return answer;
    }

    private Map<String, EzyField> getFieldsByName(EzyClass clazz) {
        return EzyMaps.newHashMap(clazz.getFields(), EzyField::getName);
    }

    private List<EzyField> getAnnotedFields(EzyClass clazz) {
        return clazz.getFields(f -> f.isAnnotated(EzyIndex.class));
    }

    @Override
    protected boolean shouldAddAnnotatedMethod(EzyMethod method) {
        return method.isPublic()
            && method.isAnnotated(EzyIndex.class)
            && isValidAnnotatedMethod(method);
    }

    protected abstract boolean isValidAnnotatedMethod(EzyMethod method);

    private String[] getIndexes(EzyClass clazz) {
        EzyArrayBinding ann = clazz.getAnnotation(EzyArrayBinding.class);
        return ann != null ? ann.indexes() : new String[0];
    }

    private int getIndex(EzyClass clazz, Object element) {
        EzyIndex index = ((EzyAnnotatedElement) element).getAnnotation(EzyIndex.class);
        if (index != null) {
            return index.value();
        }
        return clazz
            .getField(((EzyByFieldMethod) element).getFieldName())
            .getAnnotation(EzyIndex.class).value();
    }

}
