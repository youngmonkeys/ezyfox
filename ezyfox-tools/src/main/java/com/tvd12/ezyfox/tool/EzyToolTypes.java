package com.tvd12.ezyfox.tool;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.tvd12.ezyfox.reflect.EzyTypes;

@SuppressWarnings("rawtypes")
public final class EzyToolTypes {

    public static final Set<Class> ALL_TYPES = allTypes();

    private EzyToolTypes() {}

    public static boolean isCustomerClass(Class<?> clazz) {
        if(clazz.isEnum())
            return false;
        Set<Class> allTypes = ALL_TYPES;
        if(allTypes.contains(clazz))
            return false;
        Set<Class> genericsClassTypes = EzyTypes.COMMON_GENERIC_TYPES;
        for(Class<?> type : genericsClassTypes) {
            if(type.isAssignableFrom(clazz))
                return false;
        }
        return true;
    }

    private static Set<Class> allTypes() {
        Set<Class> allTypes = new HashSet<>(EzyTypes.ALL_TYPES);
        allTypes.add(Number.class);
        allTypes.add(BigDecimal.class);
        allTypes.add(BigInteger.class);
        allTypes.add(java.util.Date.class);
        allTypes.add(java.sql.Date.class);
        allTypes.add(LocalDate.class);
        allTypes.add(LocalDateTime.class);
        allTypes.add(Class.class);
        return Collections.unmodifiableSet(allTypes);
    }

}
