package com.tvd12.ezyfox.binding.testing.arraybinding;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.ezyfox.binding.annotation.EzyArrayBinding;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EzyArrayBinding(indexes = {
		"ignore1", "null1", "a", "b", "c", "d", "e", "f",
		"method1", "method2", "map", "mapC"
})
@ToString
public class ClassB1 {

	@Setter(AccessLevel.NONE)
	@Getter(AccessLevel.NONE)
	private String ignore1;
	
	private String null1;
	
	private String a = "1";
	private String b = "2";
	private String c = "3";
	
	@Setter(AccessLevel.NONE)
	@Getter(AccessLevel.NONE)
	public String d = "4";
	
	@Setter(AccessLevel.NONE)
	@Getter(AccessLevel.NONE)
	public String e = "5";
	
	@Setter(AccessLevel.NONE)
	@Getter(AccessLevel.NONE)
	public String f = "6";
	
	private Map<String, String> map = newMap();
	
	@Getter
	@Setter
	private Map<String, ClassC> mapC = newMap2();
	
	protected String getMethod1() {
		return "dung";
	}
	
	public String getMethod2() {
		return "dung";
	}
	
	protected Map<String, String> newMap() {
		Map<String, String> map = new HashMap<>();
		map.put("hello", "world");
		return map;
	}
	
	protected Map<String, ClassC> newMap2() {
		Map<String, ClassC> map = new HashMap<>();
		map.put("hello", new ClassC());
		return map;
	}
	
}
