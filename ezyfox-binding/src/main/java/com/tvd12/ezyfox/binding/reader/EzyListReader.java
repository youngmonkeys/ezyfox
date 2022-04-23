package com.tvd12.ezyfox.binding.reader;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.entity.EzyArray;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class EzyListReader implements EzyReader<EzyArray, List> {

    private static final EzyListReader INSTANCE = new EzyListReader();

    private EzyListReader() {}

    public static EzyListReader getInstance() {
        return INSTANCE;
    }

    @Override
    public List read(EzyUnmarshaller unmarshaller, EzyArray value) {
        List answer = new ArrayList<>();
        for (int i = 0; i < value.size(); ++i) {
            answer.add(value.get(i));
        }
        return answer;
    }
}
