package com.tvd12.ezyfox.binding.testing.chat;

import com.tvd12.ezyfox.binding.annotation.EzyValue;
import com.tvd12.ezyfox.io.EzyDates;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class ChatData implements Serializable {
    private static final long serialVersionUID = -1053536008550958384L;

    @EzyValue("2")
    private Date creationDate = new Date();
    @EzyValue("3")
    private Date lastReadDate = new Date();
    @EzyValue("4")
    private Date lastModifiedDate = new Date();
    @EzyValue("5")
    private int day = EzyDates.formatAsInteger(new Date());
}
