package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class JsonArrayTransformer<T> extends JsonTransformer<List<T>>
{
    private JsonTransformer<T> transformer;

    public JsonArrayTransformer(JsonTransformer<T> transformer)
    {
        this.transformer = transformer;
    }

    private List<T> transform(JsonArray jsonArray)
    {
        List<T> list = new ArrayList<>();
        for( int i = 0; i < jsonArray.size(); i++)
        {
            T element = transformer.transform(jsonArray.get(i));
            if( element != null ) list.add(element);
            else return null;
        }
        return list;
    }

    @Override
    public List<T> transform(JsonElement object)
    {
        if( object.isJsonArray() )
        {
            return transform(object.getAsJsonArray());
        }
        return null;
    }
}
