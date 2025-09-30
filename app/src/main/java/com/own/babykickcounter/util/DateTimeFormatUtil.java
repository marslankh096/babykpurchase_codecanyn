package com.own.babykickcounter.util;

import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeFormat;

public class DateTimeFormatUtil {
    private static DateTimeFormatUtil instance;

    private DateTimeFormatUtil() {
    }

    public static DateTimeFormatUtil getInstance() {
        if (instance == null) {
            instance = new DateTimeFormatUtil();
        }
        return instance;
    }

    public Date parse(String str, String str2) {
        return DateTimeFormat.forPattern(str2).parseDateTime(str).toDate();
    }

    public String format(Date date, String str) {
        return DateTimeFormat.forPattern(str).print((ReadableInstant) new DateTime((Object) date));
    }
}
