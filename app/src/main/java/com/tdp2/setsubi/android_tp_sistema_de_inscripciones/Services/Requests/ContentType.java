package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests;

public enum ContentType
{
    JSON("application/json"),
    HTML("text/html");

    private String value;
    ContentType(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
