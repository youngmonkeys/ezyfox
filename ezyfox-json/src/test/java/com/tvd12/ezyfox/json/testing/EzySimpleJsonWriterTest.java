package com.tvd12.ezyfox.json.testing;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.constant.EzyConstant;
import com.tvd12.ezyfox.json.EzySimpleJsonWriter;

import lombok.Getter;

public class EzySimpleJsonWriterTest {

	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		Map<Object, Object> map0 = new HashMap<>();
		map0.put("f1", true);
		map0.put("f2", 2);
		map0.put("f3", 'a');
		map0.put("f4", MyEnum.HELLO);
		map0.put("f5", "hello world");
		Map<Object, Object> map = new HashMap<>();
		map.put("f1", true);
		map.put("f2", 2);
		map.put("f3", 'a');
		map.put("f4", MyEnum.HELLO);
		map.put("f5", "hello world");
		map.put("f6", Lists.newArrayList(true, false, true));
		map.put("f7", Lists.newArrayList(1, 2, 3));
		map.put("f8", Lists.newArrayList('a', 'b', 'c'));
		map.put("f9", Lists.newArrayList(MyEnum.HELLO, MyEnum.HELLO, MyEnum.HELLO));
		map.put("f10", Lists.newArrayList(map0, map0, map0));
		map.put("f11", Lists.newArrayList(Lists.newArrayList(1, 2, 3), Lists.newArrayList(4, 5, 6)));
		map.put("12", map0);
		EzySimpleJsonWriter writer = new EzySimpleJsonWriter();
		String writeAsString = writer.writeAsString(map);
		System.out.println(writeAsString);
	}
	
	public static enum MyEnum implements EzyConstant {
		
		HELLO(10);
		
		@Getter
		private final int id;
		
		private MyEnum(int id) {
			this.id = id;
		}
		
	}
	
}
