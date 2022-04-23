package com.tvd12.ezyfox.identifier.testing.entity1;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.identifier.testing.annotation.HasIdTest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@HasIdTest
public class Message5 {

    @EzyId
    private long id;
    private String name;
}
