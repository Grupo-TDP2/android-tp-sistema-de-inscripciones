package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services;

import android.net.Uri;

public class APIUriBuilder
{
    public static Uri.Builder getURIBuiled()
    {
        https://api-sistema-de-inscripciones.herokuapp.com
        return new Uri.Builder()
                .scheme("https")
                .authority("api-sistema-de-inscripciones.herokuapp.com")
                .appendPath("api")
                .appendPath("v1");
    }
}
