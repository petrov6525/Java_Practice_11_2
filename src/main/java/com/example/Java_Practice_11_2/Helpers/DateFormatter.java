package com.example.Java_Practice_11_2.Helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {
    public static String getDateForPostgres (String inputDate) throws ParseException {

        SimpleDateFormat inputDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        Date date = inputDateFormat.parse(inputDate);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

        return outputFormatter.format(date.toInstant().atZone(ZoneId.systemDefault()));
    }
}
