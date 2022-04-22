package com.tvd12.ezyfox.bean.testing.issue59;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;

@EzySingleton
public class FetchAllRequestQueue extends RequestQueue {

    public FetchAllRequestQueue() {
        super(3);
    }

}
