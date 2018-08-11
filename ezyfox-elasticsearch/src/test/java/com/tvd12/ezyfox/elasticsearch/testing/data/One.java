package com.tvd12.ezyfox.elasticsearch.testing.data;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.data.annotation.EzyIndexedData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@EzyIndexedData
@NoArgsConstructor
@AllArgsConstructor
@EzyObjectBinding
public class One {

	@EzyId
	private int id;
	private String foo;
	private String bar;
	
}
