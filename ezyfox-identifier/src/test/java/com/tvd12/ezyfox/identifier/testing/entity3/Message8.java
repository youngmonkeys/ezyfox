package com.tvd12.ezyfox.identifier.testing.entity3;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.identifier.testing.annotation.HasIdTest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@HasIdTest
public class Message8 {
    @EzyId
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String name;

    protected Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }
}
