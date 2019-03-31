package com.tvd12.ezyfox.elasticsearch.testing.data;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.data.annotation.EzyIndexedData;
import com.tvd12.ezyfox.elasticsearch.annotation.EzyDataIndex;
import com.tvd12.ezyfox.elasticsearch.annotation.EzyDataIndexes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EzyObjectBinding
@EzyIndexedData
@EzyDataIndexes({
	@EzyDataIndex(index = "test", types = {"person"}),
})
@NoArgsConstructor
@AllArgsConstructor
public class TestPerson2 {
	
	@EzyId
	private String email;
	private Name name;
	private String[] phoneNumber;
	private int age;
	
	@Getter
	@Setter
	@ToString
	@NoArgsConstructor
	@AllArgsConstructor
	@EzyObjectBinding
	public static class Name {
		private String vietnamese;
		private String english;
	}
	
}
