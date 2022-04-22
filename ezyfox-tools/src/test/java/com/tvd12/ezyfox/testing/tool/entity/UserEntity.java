package com.tvd12.ezyfox.testing.tool.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(name = "bool-value-1")
    protected boolean booleanValue1;
    protected byte byteValue1;
    protected char charValue1;
    protected double doubleValue1;
    protected float floatValue1;
    protected int intValue1;
    protected long longValue1;
    protected short shortValue1;
    protected Boolean booleanValue2;
    protected Byte byteValue2;
    @Column(nullable = false)
    protected Character charValue2;
    protected Double doubleValue2;
    protected Float floatValue2;
    protected Integer intValue2;
    protected Long longValue2;
    protected Short shortValue2;
    @Column(unique = true, length = 100)
    protected String stringValue;
    protected Date date1;
    protected java.sql.Date date2;
    protected LocalDate localDate;
    protected LocalDateTime localDateTime;
    protected BigInteger bigInteger;
    protected BigDecimal bigDecimal;
    protected byte[] byteArray;
}
