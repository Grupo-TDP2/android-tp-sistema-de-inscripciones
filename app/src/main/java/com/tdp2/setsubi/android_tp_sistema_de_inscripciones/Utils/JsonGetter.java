package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonGetter
{
    protected class NullGetter extends JsonGetter
    {
        public NullGetter()
        {
            super(null);
        }

        @Override
        public JsonGetter atObject(String key)
        {
            return new NullGetter();
        }

        public JsonElement getValue(String key)
        {
            return null;
        }
    }
    private JsonObject actualObject;

    public JsonGetter(JsonObject object)
    {
        this.actualObject = object;
    }

    public JsonObject getActualObject() {
        return actualObject;
    }

    public JsonGetter atObject(String key)
    {
        if( actualObject.has(key) && actualObject.get(key).isJsonObject() )
        {
            return new JsonGetter(actualObject.getAsJsonObject(key));
        } else
        {
            return new NullGetter();
        }
    }

    public JsonArray getJsonArray(String key)
    {
        if( actualObject.has(key) && actualObject.get(key).isJsonArray() )
        {
            return actualObject.getAsJsonArray(key);
        }
        return null;
    }

    public JsonElement getValue(String key)
    {
        if( actualObject.has(key) )
        {
            return actualObject.get(key);
        }
        return null;
    }

    public String asString(String key)
    {
        if( JsonUtils.isString(actualObject, key) )
        {
            return JsonUtils.getString(actualObject, key);
        }
        return null;
    }

    public Integer asInt(String key)
    {
        if( JsonUtils.isInt(actualObject, key) )
        {
            return JsonUtils.getInt(actualObject, key);
        }
        return null;
    }

    public Boolean asBoolean(String key)
    {
        if( JsonUtils.isBool(actualObject, key) )
        {
            return JsonUtils.getBool(actualObject, key);
        }
        return null;
    }
}
