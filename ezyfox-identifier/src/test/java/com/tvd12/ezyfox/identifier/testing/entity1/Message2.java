package com.tvd12.ezyfox.identifier.testing.entity1;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.identifier.testing.annotation.HasIdTest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@HasIdTest
@AllArgsConstructor
public class Message2 {
    @EzyId
    private Long id;
    private String name;
}
