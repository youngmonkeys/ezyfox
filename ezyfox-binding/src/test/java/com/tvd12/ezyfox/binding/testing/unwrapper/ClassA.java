package com.tvd12.ezyfox.binding.testing.unwrapper;

import com.tvd12.ezyfox.binding.annotation.EzyArrayBinding;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EzyArrayBinding
public class ClassA {

    private String a;
}
