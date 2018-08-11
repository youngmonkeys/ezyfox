package com.tvd12.ezyfox.hazelcast.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.tvd12.ezyfox.hazelcast.constant.EzyMapNames;
import com.tvd12.ezyfox.hazelcast.entity.EzyAbstractAccount;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(value = EzyMapNames.ACCOUNT, noClassnameStored = true)
public class EzyMorphiaAccount extends EzyAbstractAccount {
	private static final long serialVersionUID = 3884020036545997524L;
	
	@Id
	private Long id;
	
}
