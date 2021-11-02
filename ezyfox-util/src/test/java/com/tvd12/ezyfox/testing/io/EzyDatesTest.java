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
		
		assertEquals(EzyDates.instantToDateTime(now.toInstant()), 
		        EzyDates.millisToDateTime(now.getTime()));
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
	
	@Test
	public void parseToDateOrNullTest() throws Exception {
	    // given
	    String dateString = "2019-07-22T09:15:00.001";
	    SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
	    
	    // when
	    // then
	    Asserts.assertEquals(
            dateTimeFormat.parse(dateString),
            EzyDates.parseToDateOrNull(dateTimeFormat.parse(dateString))
        );
	    Asserts.assertEquals(
            dateTimeFormat.parse(dateString),
            EzyDates.parseToDateOrNull(dateString)
        );
	    Asserts.assertEquals(
            dateTimeFormat.parse(dateString),
            EzyDates.parseToDateOrNull(dateTimeFormat.parse(dateString).getTime())
        );
	    Asserts.assertEquals(
            dateTimeFormat.parse(dateString),
            EzyDates.parseToDateOrNull(dateTimeFormat.parse(dateString).toInstant())
        );
	    Asserts.assertEquals(
            "2019-07-22T00:00:00.000",
            dateTimeFormat.format(EzyDates.parseToDateOrNull(LocalDate.of(2019, 7, 22)))
        );
	    Asserts.assertEquals(
            "2019-07-22T09:15:00.000",
            dateTimeFormat.format(EzyDates.parseToDateOrNull(LocalDateTime.of(2019, 7, 22, 9, 15)))
        );
	    Asserts.assertNull(EzyDates.parseToDateOrNull(new Object()));
	}
	
	@Test
    public void parseToInstantOrNullTest() throws Exception {
        // given
        String dateString = "2019-07-22T09:15:00.001";
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        
        // when
        // then
        Asserts.assertEquals(
            dateTimeFormat.parse(dateString).toInstant(),
            EzyDates.parseToInstantOrNull(dateTimeFormat.parse(dateString))
        );
        Asserts.assertEquals(
            dateTimeFormat.parse(dateString).toInstant(),
            EzyDates.parseToInstantOrNull(dateString)
        );
        Asserts.assertEquals(
            dateTimeFormat.parse(dateString).toInstant(),
            EzyDates.parseToInstantOrNull(dateTimeFormat.parse(dateString).getTime())
        );
        Asserts.assertEquals(
            dateTimeFormat.parse(dateString).toInstant(),
            EzyDates.parseToInstantOrNull(dateTimeFormat.parse(dateString).toInstant())
        );
        Asserts.assertEquals(
            "2019-07-22T00:00:00.000",
            dateTimeFormat.format(Date.from(EzyDates.parseToInstantOrNull(LocalDate.of(2019, 7, 22))))
        );
        Asserts.assertEquals(
            "2019-07-22T09:15:00.000",
            dateTimeFormat.format(Date.from(EzyDates.parseToInstantOrNull(LocalDateTime.of(2019, 7, 22, 9, 15))))
        );
        Asserts.assertNull(EzyDates.parseToInstantOrNull(new Object()));
    }
	
	@Test
    public void parseToLocalDateOrNullTest() throws Exception {
        // given
        String dateString = "2019-07-22T09:15:00.001";
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        
        // when
        // then
        Asserts.assertEquals(
            LocalDate.of(2019, 07, 22),
            EzyDates.parseToLocalDateOrNull(dateTimeFormat.parse(dateString))
        );
        Asserts.assertEquals(
            LocalDate.of(2019, 07, 22),
            EzyDates.parseToLocalDateOrNull("2019-07-22")
        );
        Asserts.assertEquals(
            LocalDate.of(2019, 07, 22),
            EzyDates.parseToLocalDateOrNull(dateTimeFormat.parse(dateString).getTime())
        );
        Asserts.assertEquals(
            LocalDate.of(2019, 07, 22),
            EzyDates.parseToLocalDateOrNull(dateTimeFormat.parse(dateString).toInstant())
        );
        Asserts.assertEquals(
            LocalDate.of(2019, 07, 22),
            EzyDates.parseToLocalDateOrNull(LocalDate.of(2019, 7, 22))
        );
        Asserts.assertEquals(
            LocalDate.of(2019, 07, 22),
            EzyDates.parseToLocalDateOrNull(LocalDateTime.of(2019, 7, 22, 9, 15))
        );
        Asserts.assertNull(EzyDates.parseToLocalDateOrNull(new Object()));
    }
	
	@Test
    public void parseToLocalTimeOrNullTest() throws Exception {
        // given
        String dateString = "2019-07-22T09:15:00.001";
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        
        // when
        // then
        Asserts.assertEquals(
            LocalTime.of(9, 15, 0, 1000000),
            EzyDates.parseToLocalTimeOrNull(dateTimeFormat.parse(dateString))
        );
        Asserts.assertEquals(
            LocalTime.of(9, 15, 0, 1000000),
            EzyDates.parseToLocalTimeOrNull("09:15:00.001")
        );
        Asserts.assertEquals(
            LocalTime.of(9, 15, 0, 1000000),
            EzyDates.parseToLocalTimeOrNull(dateTimeFormat.parse(dateString).getTime())
        );
        Asserts.assertEquals(
            LocalTime.of(9, 15, 0, 1000000),
            EzyDates.parseToLocalTimeOrNull(dateTimeFormat.parse(dateString).toInstant())
        );
        Asserts.assertEquals(
                LocalTime.of(0, 0, 0, 0),
            EzyDates.parseToLocalTimeOrNull(LocalDate.of(2019, 7, 22))
        );
        Asserts.assertEquals(
            LocalTime.of(9, 15, 0, 1000000),
            EzyDates.parseToLocalTimeOrNull(LocalDateTime.of(2019, 7, 22, 9, 15, 0, 1000000))
        );
        Asserts.assertNull(EzyDates.parseToLocalTimeOrNull(new Object()));
    }
	
	@Test
    public void parseToLocalDateTimeOrNullTest() throws Exception {
        // given
        String dateString = "2019-07-22T09:15:00.001";
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        
        // when
        // then
        Asserts.assertEquals(
            LocalDateTime.of(2019, 07, 22, 9, 15, 0, 1000000),
            EzyDates.parseToLocalDateTimeOrNull(dateTimeFormat.parse(dateString))
        );
        Asserts.assertEquals(
            LocalDateTime.of(2019, 07, 22, 9, 15, 0, 1000000),
            EzyDates.parseToLocalDateTimeOrNull(dateString)
        );
        Asserts.assertEquals(
            LocalDateTime.of(2019, 07, 22, 9, 15, 0, 1000000),
            EzyDates.parseToLocalDateTimeOrNull(dateTimeFormat.parse(dateString).getTime())
        );
        Asserts.assertEquals(
            LocalDateTime.of(2019, 07, 22, 9, 15, 0, 1000000),
            EzyDates.parseToLocalDateTimeOrNull(dateTimeFormat.parse(dateString).toInstant())
        );
        Asserts.assertEquals(
            LocalDateTime.of(2019, 07, 22, 0, 0, 0, 0),
            EzyDates.parseToLocalDateTimeOrNull(LocalDate.of(2019, 7, 22))
        );
        Asserts.assertEquals(
            LocalDateTime.of(2019, 07, 22, 9, 15, 0, 1000000),
            EzyDates.parseToLocalDateTimeOrNull(LocalDateTime.of(2019, 7, 22, 9, 15, 0, 1000000))
        );
        Asserts.assertNull(EzyDates.parseToLocalDateTimeOrNull(new Object()));
    }
	
	@Override
	public Class<?> getTestClass() {
		return EzyDates.class;
	}
	
}
