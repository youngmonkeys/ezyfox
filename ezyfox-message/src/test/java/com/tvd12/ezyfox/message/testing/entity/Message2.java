package com.tvd12.ezyfox.message.testing.entity;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.message.annotation.EzyMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EzyMessage
@AllArgsConstructor
public class Message2 {

    @EzyId
    private Long id;
    private String name;
}