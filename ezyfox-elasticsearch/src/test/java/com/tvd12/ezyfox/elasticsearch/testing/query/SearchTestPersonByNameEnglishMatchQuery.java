package com.tvd12.ezyfox.elasticsearch.testing.query;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.binding.annotation.EzyValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EzyObjectBinding(read = false)
public class SearchTestPersonByNameEnglishMatchQuery {

	private Match match;
	
	@Getter
	@AllArgsConstructor
	public static class Match {
		@EzyValue("name.english")
		private String name;
	}
	
}
