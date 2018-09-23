package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services;

import android.net.Uri;

public class APIUriBuilder
{
    public static Uri.Builder getURIBuiled()
    {
        return new Uri.Builder()
                .scheme("https").authority("www.herko.com");
    }
}
