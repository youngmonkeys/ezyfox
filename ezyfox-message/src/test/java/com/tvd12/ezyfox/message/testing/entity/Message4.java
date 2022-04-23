package com.tvd12.ezyfox.message.testing.entity;

import com.tvd12.ezyfox.annotation.EzyId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@com.tvd12.ezyfox.message.annotation.Message
@AllArgsConstructor
public class Message4 {

    @EzyId
    private Long id;
    private String name;
}
