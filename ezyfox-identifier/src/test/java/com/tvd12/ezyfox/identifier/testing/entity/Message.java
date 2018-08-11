package com.tvd12.ezyfox.identifier.testing.entity;

import com.tvd12.ezyfox.util.EzyHasIdEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message implements EzyHasIdEntity<Long> {

	private Long id;
	private String name;
	
}
