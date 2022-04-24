package com.tvd12.ezyfox.identifier.testing.entity3;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.identifier.testing.annotation.HasIdTest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@HasIdTest
public class Message9 {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String name;

    @EzyId
    public Long getId() {
        return id;
    }

    @EzyId
    public void setId(Long id) {
        this.id = id;
    }
}
