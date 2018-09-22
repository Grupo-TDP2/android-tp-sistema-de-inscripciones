package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class JsonArrayTransformer<T>
{
    private JsonTransformer<T> transformer;

    public JsonArrayTransformer(JsonTransformer<T> transformer)
    {
        this.transformer = transformer;
    }

    public List<T> transform(JSONArray jsonArray)
    {
        List<T> list = new ArrayList<>();
        for( int i = 0; i < jsonArray.length(); i++)
        {
            try {
                T element = transformer.transform(jsonArray.getJSONObject(i));
                if( element != null ) list.add(element);
                else return null;
            } catch (JSONException e) {
                return null;
            }
        }
        return list;
    }
}
