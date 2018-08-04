package com.tvd12.ezyfox.hazelcast.testing.service;

import com.tvd12.ezyfox.hazelcast.entity.EzyAbstractAccount;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TestAcount extends EzyAbstractAccount {
	private static final long serialVersionUID = 2935749419090105L;

	private Long id;
	
}
