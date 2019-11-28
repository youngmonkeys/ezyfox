package com.tvd12.ezyfox.data.testing.index_data;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.data.annotation.IndexedData;
import com.tvd12.ezyfox.message.annotation.Message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Message
@IndexedData
public class ClassB {

	@EzyId
	private int id;
	
}
