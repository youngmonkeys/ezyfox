package com.tvd12.ezyfox.identifier.testing.entity2;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.identifier.testing.annotation.HasIdTest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@HasIdTest
public class Message6 {

    @EzyId
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    public Long id;
    private String name;

}
