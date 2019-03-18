package com.tvd12.ezyfox.testing.tool;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.tool.EzyObjectInstanceRandom;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class EzyObjectInstanceRandomTest {

	@Test
	public void test() {
		EzyObjectInstanceRandom random = new EzyObjectInstanceRandom();
		Map<String, Object> map = random.randomObjectToMap(ClassA.class, false);
		System.out.println(map);
	}
	
	@Test
	public void test1() {
		EzyObjectInstanceRandom random = new EzyObjectInstanceRandom();
		Object a = random.randomObject(ClassA.class, false);
		System.out.println(a);
	}
	
	@Getter
	@Setter
	@ToString
	public static class ClassA {
		private Date date;
		private LocalDate localDate;
		private LocalDateTime localDateTime;
		private Boolean aa;
		private Byte ab;
		private Character ac;
		private Double ad;
		private Float ae;
		private Integer af;
		private Long ag;
		private Short ah;
		private String str;
		private Boolean[] aa1;
		private Byte[] ab1;
		private Character[] ac1;
		private Double[] ad1;
		private Float[] ae1;
		private Integer[] af1;
		private Long[] ag1;
		private Short[] ah1;
		private String[] strs;
		private List<Integer> listInt;
		private List<String> listString;
		private Map<Integer, Integer> mapInt;
		private Map<String, String> mapString;
		private List<ClassB> listObject;
		private Set<ClassB> setObject;
		private Map<String, ClassB> mapObject;
	}
	
	@Getter
	@Setter
	@ToString
	public static class ClassB {
		private boolean ba;
		private byte bb;
		private char bc;
		private double bd;
		private float be;
		private int bf;
		private long bg;
		private short bh;
		private boolean[] bas;
	}
	
}
