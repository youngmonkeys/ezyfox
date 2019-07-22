package com.tvd12.ezyfox.io;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public final class EzyDates {

	private static final DateTimeFormatter DATE_TIME_FORMATTER
			= getDateTimeFormatter(getPattern());
	
	private EzyDates() {
	}
	
	// ============= java 8 ============
	public static String format(TemporalAccessor temporal) {
		return format(temporal, getDateTimeFormatter());
	}
	
	public static String format(TemporalAccessor temporal, String pattern) {
		return format(temporal, getDateTimeFormatter(pattern));
	}
	
	public static String format(TemporalAccessor temporal, DateTimeFormatter formatter) {
		return temporal == null ? null : formatter.format(temporal);
	}
	
	public static LocalDate parseDate(String source) {
		return parseDate(source, getDateTimeFormatter());
	}
	
	public static LocalDate parseDate(String source, String pattern) {
		return parseDate(source, getDateTimeFormatter(pattern));
	}
	
	public static LocalDate parseDate(String source, DateTimeFormatter formatter) {
		return LocalDate.parse(source, formatter);
	}
	
	public static LocalDateTime parseDateTime(String source) {
		return parseDateTime(source, getDateTimeFormatter());
	}
	
	public static LocalDateTime parseDateTime(String source, String pattern) {
		return parseDateTime(source, getDateTimeFormatter(pattern));
	}
	
	public static LocalDateTime parseDateTime(String source, DateTimeFormatter formatter) {
		return LocalDateTime.parse(source, formatter);
	}
	
	public static DateTimeFormatter getDateTimeFormatter() {
		return DATE_TIME_FORMATTER;
	}
	
	public static DateTimeFormatter getDateTimeFormatter(String pattern) {
		return DateTimeFormatter.ofPattern(pattern);
	}
	//=================================
	
	
	// =================== java 7 ===============
	public static String format(long millis) {
		return format(millis, getPattern());
	}
	
	public static String format(Date date) {
		return format(date, getPattern());
	}
	
	public static Date parse(String source) {
		return parse(source, getPattern());
	}
	
	public static String format(long millis, String pattern) {
		return format(new Date(millis), pattern);
	}
	
	public static String format(Date date, String pattern) {
		if(date == null)
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		String answer = formatter.format(date);
		return answer;
	}
	
	public static Date parse(String source, String pattern) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			Date answer = formatter.parse(source);
			return answer;
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public static String getPattern() {
		return "yyyy-MM-dd'T'HH:mm:ss:SSS";
	}
	
	// =========================================
	public static boolean between(Date date, Date before, Date after) {
		long time = date.getTime();
		return time >= before.getTime() && time <= after.getTime();
	}
	
	public static int formatAsInteger(Date date) {
		LocalDateTime dateTime = dateToDateTime(date);
		int year = dateTime.getYear();
		int month = dateTime.getMonthValue();
		int day = dateTime.getDayOfMonth();
		return year * 10000 + month * 100 + day;
	}
	
	public static LocalDateTime dateToDateTime(Date date) {
		LocalDateTime dateTime = millisToDateTime(date.getTime());
		return dateTime;
	}
	
	public static LocalDateTime millisToDateTime(long millis) {
		Instant instant = Instant.ofEpochMilli(millis);
		LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		return dateTime;
	}
	
}
