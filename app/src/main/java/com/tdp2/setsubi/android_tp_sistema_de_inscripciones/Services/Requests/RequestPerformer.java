package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonTransformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RequestPerformer<T>
{
    private String url;
    private RequestMethod method;
    private JsonTransformer<T> transformer;
    private Map<String,String> requestProperties;

    public RequestPerformer(String url, RequestMethod method, JsonTransformer<T> transformer)
    {
        this.url = url;
        this.method = method;
        this.transformer = transformer;
        this.requestProperties = new HashMap<>();
    }

    public RequestPerformer<T> addRequestProperty(String propertyName, String propertyValue)
    {
        requestProperties.put(propertyName, propertyValue);
        return this;
    }

    public ServiceResponse<T> perform()
    {
        Log.d("REQ"," url: " + url + " " + method);
        HttpURLConnection client = null;
        try {
            URL url = new URL(this.url);
            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod(method.getMethod());
            for(Map.Entry<String,String> property : requestProperties.entrySet() )
            {
                client.setRequestProperty(property.getKey(), property.getValue());
            }
            client.connect();

            BufferedReader br;

            int statusCode = client.getResponseCode();

            if (400 <= statusCode && statusCode <= 500){
                Log.d("REQ"," error: " + statusCode);
                return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.ERROR);
            }

            if (200 <= statusCode && statusCode <= 299) {
                br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(client.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            String result = sb.toString();
            Log.d("REQ"," result: " + result);
            T transformed;
            try
            {
                transformed = transformer.transform( new Gson().fromJson(result, JsonElement.class));
                if( transformed == null )
                {
                    Log.d("REQ", "Failed deserialization");
                    return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.ERROR);
                } else {
                    Log.d("REQ", "Success request");
                    return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.SUCCESS, transformed);
                }
            } catch (Exception badJson)
            {
                Log.d("REQ", badJson.toString());
                return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.ERROR);
            }
        } catch(Exception error) {
            //Handles an incorrectly entered URL
            Log.d("REQ", error.toString());
            return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.ERROR);
        }
        finally {
            if(client != null) {
                client.disconnect();
            }
        }
    }
}
