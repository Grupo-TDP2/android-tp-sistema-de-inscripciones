package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services;

public class ServiceResponse<T>
{
    public enum ServiceStatusCode {
        SUCCESS, UNAUTHORIZED, ERROR, CONFLICT
    }

    private ServiceStatusCode statusCode;
    private T serviceResponse;

    public ServiceResponse(ServiceStatusCode statusCode){
        this(statusCode, null);
    }

    public ServiceResponse(ServiceStatusCode statusCode, T serviceResponse){
        this.statusCode = statusCode;
        this.serviceResponse = serviceResponse;
    }

    public ServiceStatusCode getStatusCode() {
        return statusCode;
    }

    public T getServiceResponse(){
        return serviceResponse;
    }
}
