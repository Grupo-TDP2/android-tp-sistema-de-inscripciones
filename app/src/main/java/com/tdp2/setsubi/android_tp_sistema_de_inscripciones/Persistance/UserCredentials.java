package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Persistance;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

public class UserCredentials
{
    private static final String SHARED_NAME = "SHARED_PREFERNECES_FIUBA_INSCRIPCIONES";
    private static final String EMAIL = "EMAIL";
    private static final String PASSWORD = "PASSWORD";
    private static final String IMAGE_START = "IMAGE_OF_";
    private SharedPreferences preferences;

    public UserCredentials(Context context)
    {
        preferences = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
    }

    public String getUserMail()
    {
        return preferences.getString(EMAIL,null);
    }

    public String getUserPassword()
    {
        return preferences.getString(PASSWORD,null);
    }

    public void saveUserCredentials(String email, String password)
    {
        preferences.edit().putString(EMAIL, email).putString(PASSWORD, password).apply();
    }

    public void saveImageUri(int studentId, String uri)
    {
        preferences.edit().putString(IMAGE_START+String.valueOf(studentId),uri).apply();
    }

    public String getImageUriOf(int studentId)
    {
        return preferences.getString(IMAGE_START+String.valueOf(studentId), null);
    }
}
