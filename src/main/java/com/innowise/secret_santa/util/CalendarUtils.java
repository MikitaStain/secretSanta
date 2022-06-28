package com.innowise.secret_santa.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public final class CalendarUtils {

    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";

    private CalendarUtils() {
    }

    public static String convertMilliSecondsToFormattedDate(Long milliSeconds) {

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        return format.format(calendar.getTime());
    }

    public static LocalDateTime getFormatDate(LocalDateTime localDateTime) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        String data = dtf.format(localDateTime);
        return LocalDateTime.parse(data, dtf);
    }
}
