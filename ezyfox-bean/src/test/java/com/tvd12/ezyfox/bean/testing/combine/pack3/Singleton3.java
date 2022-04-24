package com.tvd12.ezyfox.bean.testing.combine.pack3;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@EzySingleton("s3")
public class Singleton3 {

    @Setter
    @EzyAutoBind
    private ArrayList<String> list;
}
