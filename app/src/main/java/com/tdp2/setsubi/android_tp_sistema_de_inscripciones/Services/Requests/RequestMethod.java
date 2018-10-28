package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests;

public enum  RequestMethod
{
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    PATCH("PATCH");
    private String method;
    RequestMethod(String method)
    {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
