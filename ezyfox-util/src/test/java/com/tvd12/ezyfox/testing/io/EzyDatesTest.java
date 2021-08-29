package com.tvd12.ezyfox.testing.io;

import static org.testng.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
		assertEquals(EzyDates.parseDate("2017-05-30"), 
				LocalDate.of(2017, 05, 30));
		assertEquals(EzyDates.parseDate("2017-05-30", "yyyy-MM-dd"), 
				LocalDate.of(2017, 05, 30));
		assertEquals(EzyDates.parseDateTime("2017-05-30T12:34:56:000"), 
				LocalDateTime.of(2017, 05, 30, 12, 34, 56, 0));
		assertEquals(EzyDates.parseDateTime("2017-05-30T12:34:56", "yyyy-MM-dd'T'HH:mm:ss"), 
				LocalDateTime.of(2017, 05, 30, 12, 34, 56, 0));
		Date now = new Date();
		EzyDates.format(now.getTime()).equals(EzyDates.format(now));
		
		assertEquals(EzyDates.parseDate("2017-05-30"), 
				LocalDate.of(2017, 05, 30));
		
		assertEquals(EzyDates.parseTime("01:01:01:001"), 
				LocalTime.of(1, 1, 1, 1000000));
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
	
	@Test
	public void parTimeStandard() {
		// given
		String time = "10:08:22.999";
		
		// when
		LocalTime actual = EzyDates.parseTime(time);
		
		// then
		LocalTime expectation = LocalTime.of(10, 8, 22, 999000000);
		Asserts.assertEquals(expectation, actual);
	}
	
	@Test
	public void parDateTimeStandard() {
		// given
		String time = "2021-08-22T10:08:22.999";
		
		// when
		LocalDateTime actual = EzyDates.parseDateTime(time);
		
		// then
		LocalDateTime expectation = LocalDateTime.of(2021, 8, 22, 10, 8, 22, 999000000);
		Asserts.assertEquals(expectation, actual);
	}
	
	@Test
	public void parDateTimeYYYMMDDHHMMSS() {
		// given
		String time = "2021-08-22T10:08:22";
		
		// when
		LocalDateTime actual = EzyDates.parseDateTime(time);
		
		// then
		LocalDateTime expectation = LocalDateTime.of(2021, 8, 22, 10, 8, 22);
		Asserts.assertEquals(expectation, actual);
	}
	
	@Test
	public void parDateTimeFailed() {
		// given
		String time = "2021-08-22.10:08:22";
		
		// when
		Throwable exception = Asserts.assertThrows(() -> 
			EzyDates.parseDateTime(time)
		);
		
		// then
		Asserts.assertEquals(IllegalArgumentException.class, exception.getClass());
	}
	
	@Test
	public void parDateUtilStandard() throws Exception {
		// given
		String time = "2021-08-22T10:08:22.999";
		
		// when
		Date actual = EzyDates.parse(time);
		
		// then
		Date expectation = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
				.parse(time);
		Asserts.assertEquals(expectation, actual);
	}
	
	@Test
	public void parDateUtilYYYMMDDHHMMSS() throws Exception {
		// given
		String time = "2021-08-22T10:08:22";
		
		// when
		Date actual = EzyDates.parse(time);
		
		// then
		Date expectation = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
				.parse(time);
		Asserts.assertEquals(expectation, actual);
	}
	
	@Test
	public void parDateUtilFailed() {
		// given
		String time = "2021-08-22.10:08:22";
		
		// when
		Throwable exception = Asserts.assertThrows(() -> 
			EzyDates.parse(time)
		);
		
		// then
		Asserts.assertEquals(IllegalArgumentException.class, exception.getClass());
	}
	
	@Override
	public Class<?> getTestClass() {
		return EzyDates.class;
	}
	
}
