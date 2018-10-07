package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils;

import java.util.regex.Pattern;

public class TextValidator
{
    private static final Pattern EMAIL_PATTERN
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean isEmail(String string)
    {
        return string != null && EMAIL_PATTERN.matcher(string).matches();
    }
}
