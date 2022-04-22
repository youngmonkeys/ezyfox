package com.tvd12.ezyfox.util;

import java.util.ArrayList;
import java.util.List;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;

public final class EzyArrayToList {

    private static final EzyArrayToList INSTANCE = new EzyArrayToList();

    private EzyArrayToList() {
    }

    public static EzyArrayToList getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List toList(EzyArray array) {
        List answer = new ArrayList<>();
        for(int i = 0 ; i < array.size() ; ++i) {
            Object item = array.get(i);
            Object sitem = item;
            if(item != null) {
                EzyObjectToMap objectToMap = EzyObjectToMap.getInstance();
                if(item instanceof EzyObject)
                    sitem = objectToMap.toMap((EzyObject) item);
                else if(item instanceof EzyArray)
                    sitem = toList((EzyArray) item);
            }
            answer.add(sitem);
        }
        return answer;
    }
}