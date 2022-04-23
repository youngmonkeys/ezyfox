package com.tvd12.ezyfox.io;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public final class EzyDates {

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_PATTERN = "HH:mm:ss:SSS";
    public static final String TIME_PATTERN_STANDARD = "HH:mm:ss.SSS";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss:SSS";
    public static final String DATE_TIME_PATTERN_STANDARD = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String DATE_TIME_PATTERN_YYYYMMDD_HHMMSS = "yyyy-MM-dd'T'HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER
            = getDateTimeFormatter(getPattern());
    public static final DateTimeFormatter DATE_TIME_FORMATTER_STANDARD
            = getDateTimeFormatter(getPatternStandard());
    public static final DateTimeFormatter DATE_TIME_FORMATTER_YYYYMMDD_HHMMSS
            = getDateTimeFormatter(DATE_TIME_PATTERN_YYYYMMDD_HHMMSS);

    private static final String[] DATE_TIME_PATTERNS = new String[] {
            DATE_TIME_PATTERN,
            DATE_TIME_PATTERN_STANDARD,
            DATE_TIME_PATTERN_YYYYMMDD_HHMMSS
    };

    private EzyDates() {}

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
        return parseDate(source, DATE_PATTERN);
    }

    public static LocalDate parseDate(String source, String pattern) {
        return parseDate(source, getDateTimeFormatter(pattern));
    }

    public static LocalDate parseDate(String source, DateTimeFormatter formatter) {
        return LocalDate.parse(source, formatter);
    }

    public static LocalTime parseTime(String source) {
        try {
            return parseTime(source, TIME_PATTERN);
        }
        catch (Exception e) {
            return parseTime(source, TIME_PATTERN_STANDARD);
        }
    }

    public static LocalTime parseTime(String source, String pattern) {
        return parseTime(source, getDateTimeFormatter(pattern));
    }

    public static LocalTime parseTime(String source, DateTimeFormatter formatter) {
        return LocalTime.parse(source, formatter);
    }

    public static LocalDateTime parseDateTime(String source) {
        try {
            return parseDateTime(source, getDateTimeFormatter());
        }
        catch (Exception e) {
            try {
                return parseDateTime(source, getDateTimeFormatterStandard());
            }
            catch (Exception e2) {
                try {
                    return parseDateTime(source, DATE_TIME_FORMATTER_YYYYMMDD_HHMMSS);
                }
                catch (Exception e3) {
                    throw new IllegalArgumentException(
                        "invalid date value: " + source +
                        ", format must be of one " + EzyStrings.join(DATE_TIME_PATTERNS, ", ")
                    );
                }
            }
        }
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

    public static DateTimeFormatter getDateTimeFormatterStandard() {
        return DATE_TIME_FORMATTER_STANDARD;
    }

    public static DateTimeFormatter getDateTimeFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern);
    }

    public static Instant toInstant(LocalDate localDate) {
        return toInstant(localDate, ZoneId.systemDefault());
    }

    public static Instant toInstant(LocalDate localDate, ZoneId zoneId) {
        return localDate.atStartOfDay().atZone(zoneId).toInstant();
    }

    public static Instant toInstant(LocalDateTime localDateTime) {
        return toInstant(localDateTime, ZoneId.systemDefault());
    }

    public static Instant toInstant(LocalDateTime localDateTime, ZoneId zoneId) {
        return localDateTime.atZone(zoneId).toInstant();
    }

    public static Date toDate(LocalDate localDate) {
        return toDate(localDate, ZoneId.systemDefault());
    }

    public static Date toDate(LocalDate localDate, ZoneId zoneId) {
        return Date.from(toInstant(localDate, zoneId));
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return toDate(localDateTime, ZoneId.systemDefault());
    }

    public static Date toDate(LocalDateTime localDateTime, ZoneId zoneId) {
        return Date.from(toInstant(localDateTime, zoneId));
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
        try {
            return parse(source, getPattern());
        }
        catch (Exception e) {
            try {
                return parse(source, getPatternStandard());
            }
            catch (Exception e2) {
                try {
                    return parse(source, DATE_TIME_PATTERN_YYYYMMDD_HHMMSS);
                }
                catch (Exception e3) {
                    throw new IllegalArgumentException(
                        "invalid date value: " + source +
                        ", format must be one of " + EzyStrings.join(DATE_TIME_PATTERNS, ", ")
                    );
                }
            }
        }
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
        return DATE_TIME_PATTERN;
    }

    public static String getPatternStandard() {
        return DATE_TIME_PATTERN_STANDARD;
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
        return millisToDateTime(millis, ZoneId.systemDefault());
    }

    public static LocalDateTime millisToDateTime(long millis, ZoneId zoneId) {
        Instant instant = Instant.ofEpochMilli(millis);
        return instantToDateTime(instant, zoneId);
    }

    public static LocalDateTime instantToDateTime(Instant instant) {
        return instantToDateTime(instant, ZoneId.systemDefault());
    }

    public static LocalDateTime instantToDateTime(Instant instant, ZoneId zoneId) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, zoneId);
        return dateTime;
    }

    // =============================================
    public static Date parseToDateOrNull(Object value) {
        if (value instanceof Date) {
            return (Date)value;
        }
        if (value instanceof String) {
            return parse((String)value);
        }
        if (value instanceof Number) {
            return new Date(((Number)value).longValue());
        }
        if (value instanceof Instant) {
            return Date.from(((Instant)value));
        }
        if (value instanceof LocalDate) {
            return toDate((LocalDate)value);
        }
        if (value instanceof LocalDateTime) {
            return toDate((LocalDateTime)value);
        }
        return null;
    }

    public static Instant parseToInstantOrNull(Object value) {
        if (value instanceof Date) {
            return ((Date)value).toInstant();
        }
        if (value instanceof String) {
            return parse((String)value).toInstant();
        }
        if (value instanceof Number) {
            return Instant.ofEpochMilli(((Number)value).longValue());
        }
        if (value instanceof Instant) {
            return (Instant)value;
        }
        if (value instanceof LocalDate) {
            return toInstant((LocalDate)value);
        }
        if (value instanceof LocalDateTime) {
            return toInstant((LocalDateTime)value);
        }
        return null;
    }

    public static LocalDate parseToLocalDateOrNull(Object value) {
        if (value instanceof Date) {
            return dateToDateTime((Date)value).toLocalDate();
        }
        if (value instanceof String) {
            return parseDate((String)value);
        }
        if (value instanceof Number) {
            return millisToDateTime(((Number)value).longValue()).toLocalDate();
        }
        if (value instanceof Instant) {
            return ((Instant)value).atZone(ZoneId.systemDefault()).toLocalDate();
        }
        if (value instanceof LocalDate) {
            return (LocalDate)value;
        }
        if (value instanceof LocalDateTime) {
            return ((LocalDateTime)value).toLocalDate();
        }
        return null;
    }

    public static LocalTime parseToLocalTimeOrNull(Object value) {
        if (value instanceof Date) {
            return dateToDateTime((Date)value).toLocalTime();
        }
        if (value instanceof String) {
            return parseTime((String)value);
        }
        if (value instanceof Number) {
            return millisToDateTime(((Number)value).longValue()).toLocalTime();
        }
        if (value instanceof Instant) {
            return ((Instant)value).atZone(ZoneId.systemDefault()).toLocalTime();
        }
        if (value instanceof LocalDate) {
            return LocalTime.of(0, 0);
        }
        if (value instanceof LocalDateTime) {
            return ((LocalDateTime)value).toLocalTime();
        }
        return null;
    }

    public static LocalDateTime parseToLocalDateTimeOrNull(Object value) {
        if (value instanceof Date) {
            return dateToDateTime((Date)value);
        }
        if (value instanceof String) {
            return parseDateTime((String)value);
        }
        if (value instanceof Number) {
            return millisToDateTime(((Number)value).longValue());
        }
        if (value instanceof Instant) {
            return ((Instant)value).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        if (value instanceof LocalDate) {
            LocalDate localDate = ((LocalDate)value);
            return LocalDateTime.of(
                localDate.getYear(), 
                localDate.getMonth(),
                localDate.getDayOfMonth(),
                0,
                0
            );
        }
        if (value instanceof LocalDateTime) {
            return (LocalDateTime)value;
        }
        return null;
    }
}
