package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonTransformer;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestPerformer<T>
{
    public enum Method
    {
        GET("GET"),
        PUT("PUT");
        private String method;
        Method(String method)
        {
            this.method = method;
        }

        public String getMethod() {
            return method;
        }
    }

    private String url;
    private Method method;
    private JsonTransformer<T> transformer;

    public RequestPerformer(String url, Method method, JsonTransformer<T> transformer)
    {
        this.url = url;
        this.method = method;
        this.transformer = transformer;
    }

    public ServiceResponse<T> perform()
    {
        Log.d("REQ"," url: " + url + " " + method);
        HttpURLConnection client = null;
        try {
            URL url = new URL(this.url);
            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod(method.getMethod());

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
