package com.tvd12.ezyfox.hazelcast.entity;

import com.tvd12.ezyfox.hazelcast.constant.EzyMapNames;

import lombok.Getter;
import lombok.Setter;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;

@Getter
@Setter
@Entity(value = EzyMapNames.ACCOUNT, noClassnameStored = true)
public class EzyMorphiaAccount extends EzyAbstractAccount {
	private static final long serialVersionUID = 3884020036545997524L;
	
	@Id
	private Long id;
	
}
