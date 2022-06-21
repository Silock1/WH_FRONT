package com.warehouse_accounting.components.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateConvertor {

    public static final String datePattern = "dd.MM.yyyy";

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern + " HH:mm:dd");

    public static LocalDate fromTextDate(CharSequence text){ return LocalDate.parse(text, dateFormatter); }

    public static LocalDateTime fromTextDateTime(CharSequence text){ return LocalDateTime.parse(text, dateTimeFormatter); }

    public static String asText(LocalDate date) { return date.format(dateFormatter); }

    public static String asText(LocalDateTime date) { return date.format(dateTimeFormatter); }
}
