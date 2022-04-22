package com.tvd12.ezyfox.data.testing.index_data1;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.data.annotation.IndexedData;
import com.tvd12.ezyfox.message.annotation.Message;

@Message
@IndexedData
public class ClassF {

    @EzyId
    public int id;
}