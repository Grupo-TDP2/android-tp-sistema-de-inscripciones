package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests;

import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class RequestBuilder
{
    private RequestMethod requestMethod;
    private Map<String,String> requestProperties = new HashMap<>();
    private String body = null;

    public RequestBuilder(RequestMethod method)
    {
        this.requestMethod = method;
    }

    public RequestBuilder(RequestMethod method, String bodyText)
    {
        this(method);
        this.body = bodyText;
    }

    public RequestBuilder addRequestProperty(String key, String value)
    {
        requestProperties.put(key, value);
        return this;
    }

    public RequestBuilder addRequestProperty(RequestProperty key, String value)
    {
       return addRequestProperty(key.getKey(), value);
    }

    public RequestBuilder setBodyText(String body)
    {
        this.body = body;
        return this;
    }

    void buildConnection(HttpURLConnection connection) throws IOException
    {
        Log.d("REQ","" + requestMethod);
        connection.setRequestMethod(requestMethod.getMethod());

        for(Map.Entry<String,String> property : requestProperties.entrySet() )
        {
            Log.d("REQ","Adding header property: " + property.getKey() + ":" + property.getValue());
            connection.setRequestProperty(property.getKey(), property.getValue());
        }

        if( body != null ) {
            Log.d("REQ","Set body: " + body);
            connection.getOutputStream().write(body.getBytes("UTF-8"));
        }
    }

    public RequestMethod getMethod() {
        return requestMethod;
    }
}
