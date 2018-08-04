package com.tvd12.ezyfox.elasticsearch.testing.data;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.data.annotation.EzyIndexedData;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EzyIndexedData
public class Person3 {
	
	@EzyId
	private long id;
	
}
