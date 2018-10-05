package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests;

public enum  RequestProperty
{
    CONTENT_TYPE("Content-Type"),
    AUTHORIZATION("Authorization");

    private String key;
    RequestProperty(String key)
    {
        this.key = key;
    }

    public String getKey()
    {
        return key;
    }
}
