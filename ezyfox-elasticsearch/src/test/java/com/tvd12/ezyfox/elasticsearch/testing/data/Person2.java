package com.tvd12.ezyfox.elasticsearch.testing.data;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.data.annotation.EzyIndexedData;
import com.tvd12.ezyfox.elasticsearch.annotation.EzyDataIndex;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EzyIndexedData
@EzyDataIndex("test1")
public class Person2 {
	
	@EzyId
	private long id;
	
}
