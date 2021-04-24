package com.tvd12.ezyfox.testing.io;

import static org.testng.Assert.assertEquals;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.io.EzyDates;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;

public class EzyDatesTest extends BaseTest {

	@Test
	public void test() {
		assertEquals(EzyDates.format((Date)null), null);
	}
	
	@Test
	public void test1() {
		EzyDates.format(LocalDateTime.of(2017, 05, 30, 12, 34, 56, 0));
		assertEquals(EzyDates.format(LocalDateTime.of(
				2017, 05, 30, 12, 34, 56, 0),
				"yyyy-MM-dd'T'HH:mm:ss"), 
				"2017-05-30T12:34:56");
		assertEquals(EzyDates.format(LocalDateTime.of(
				2017, 05, 30, 12, 34, 56, 0),
				"yyyy-MM-dd'T'HH:mm:ss"), 
				"2017-05-30T12:34:56");
		assertEquals(EzyDates.format((TemporalAccessor)null), null);
		assertEquals(EzyDates.parseDate("2017-05-30T12:34:56:000"), 
				LocalDate.of(2017, 05, 30));
		assertEquals(EzyDates.parseDate("2017-05-30", "yyyy-MM-dd"), 
				LocalDate.of(2017, 05, 30));
		assertEquals(EzyDates.parseDateTime("2017-05-30T12:34:56:000"), 
				LocalDateTime.of(2017, 05, 30, 12, 34, 56, 0));
		assertEquals(EzyDates.parseDateTime("2017-05-30T12:34:56", "yyyy-MM-dd'T'HH:mm:ss"), 
				LocalDateTime.of(2017, 05, 30, 12, 34, 56, 0));
		Date now = new Date();
		EzyDates.format(now.getTime()).equals(EzyDates.format(now));
	}
	
	@Test
	public void test2() {
		assertEquals(EzyDates.format((Date)null), null);
		assertEquals(EzyDates.format(new Date()).length() > 0, true);
		assertEquals(EzyDates.parse("2017-05-30T12:34:56:000").getTime() > 0, true);
	}
	
	@Test(expectedExceptions = {IllegalArgumentException.class})
	public void test3() {
		EzyDates.parse("abcc");
	}
	
	@Test
	public void test4() {
		Date now = new Date();
		long nowTime = now.getTime();
		Date before = new Date(nowTime - 10000);
		Date after = new Date(nowTime + 10000);
		assert EzyDates.between(now, before, after);
		
		now = new Date(nowTime - 20000);
		assert !EzyDates.between(now, before, after);
		
		now = new Date(nowTime + 20000);
		assert !EzyDates.between(now, before, after);
	}
	
	@Test
	public void test5() {
		int value = EzyDates.formatAsInteger(new Date());
		System.out.println(value);
	}
	
	@Test
	public void localDateToInstantTest() {
		// given
		LocalDate now = LocalDate.now();
		
		// when
		Instant actual = EzyDates.toInstant(now);
		
		// then
		Instant expected = now
				.atStartOfDay()
				.atZone(ZoneId.systemDefault())
				.toInstant();
		Asserts.assertEquals(expected, actual);
	}
	
	@Test
	public void localDateTimeToInstantTest() {
		// given
		LocalDateTime now = LocalDateTime.now();
		
		// when
		Instant actual = EzyDates.toInstant(now);
		
		// then
		Instant expected = now
				.atZone(ZoneId.systemDefault())
				.toInstant();
		Asserts.assertEquals(expected, actual);
	}
	
	@Test
	public void localDateToDateTest() {
		// given
		LocalDate now = LocalDate.now();
		
		// when
		Date actual = EzyDates.toDate(now);
		
		// then
		Date expected = Date.from(
				now
				.atStartOfDay()
				.atZone(ZoneId.systemDefault())
				.toInstant()
		);
		Asserts.assertEquals(expected, actual);
	}
	
	@Test
	public void localDateTimeToDateTest() {
		// given
		LocalDateTime now = LocalDateTime.now();
		
		// when
		Date actual = EzyDates.toDate(now);
		
		// then
		Date expected = Date.from(
				now
				.atZone(ZoneId.systemDefault())
				.toInstant()
		);
		Asserts.assertEquals(expected, actual);
	}
	
	@Override
	public Class<?> getTestClass() {
		return EzyDates.class;
	}
	
}
