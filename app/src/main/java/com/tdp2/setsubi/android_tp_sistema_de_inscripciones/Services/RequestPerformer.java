package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class RequestPerformer<T>
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

    public RequestPerformer(String url, Method method)
    {
        this.url = url;
        this.method = method;
    }

    public ServiceResponse<T> perform()
    {
        HttpURLConnection client = null;
        try {
            URL url = new URL(this.url);
            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod(method.getMethod());

            client.connect();

            BufferedReader br;

            int statusCode = client.getResponseCode();

            if (400 <= statusCode && statusCode <= 500){
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

            return deserialzie(new JSONObject(result));

        } catch(Exception error) {
            //Handles an incorrectly entered URL
            return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.ERROR);
        }
        finally {
            if(client != null) {
                client.disconnect();
            }
        }
    }

    protected abstract ServiceResponse<T> deserialzie(JSONObject jsonObject);
}
