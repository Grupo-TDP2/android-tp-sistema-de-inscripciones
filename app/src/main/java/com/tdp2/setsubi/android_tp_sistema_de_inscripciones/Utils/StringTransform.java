package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StringTransform {

    public static String getTime(Date time, String format)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return simpleDateFormat.format(time);
    }

    public static String getDayOfWeek(DayOfWeek dayOfWeek)
    {
        Calendar c = Calendar.getInstance();
        // date doesn't matter - it has to be a Monday
        // I new that first August 2011 is one ;-)
        c.set(2011, 7, 1, 0, 0, 0);
        c.add(Calendar.DAY_OF_MONTH, dayOfWeek.getValue());
        return asUpperCaseFirstChar(String.format("%tA", c));
    }

    public static String asUpperCaseFirstChar(final String target)
    {
        if ((target == null) || (target.length() == 0)) {
            return target; // You could omit this check and simply live with an
            // exception if you like
        }
        return Character.toUpperCase(target.charAt(0))
                + (target.length() > 1 ? target.substring(1) : "");
    }
}
