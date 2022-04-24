package com.tvd12.ezyfox.data.testing.index_data;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.data.annotation.EzyIndexedData;
import com.tvd12.ezyfox.message.annotation.EzyMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EzyMessage
@EzyIndexedData
public class ClassA {

    @EzyId
    private int id;
}
