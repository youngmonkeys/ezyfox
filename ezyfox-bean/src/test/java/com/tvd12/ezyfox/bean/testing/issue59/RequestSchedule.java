package com.tvd12.ezyfox.bean.testing.issue59;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

@EzySingleton
public class RequestSchedule {

    @EzyAutoBind("fetchAllRequestQueue")
    public void setQueue(RequestQueue requestQueue) {
        
    }
    }