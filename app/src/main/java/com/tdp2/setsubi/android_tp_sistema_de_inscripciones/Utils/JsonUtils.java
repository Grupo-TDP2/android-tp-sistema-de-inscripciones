package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils;

import com.google.gson.JsonObject;

public class JsonUtils
{
    public static boolean isInt(JsonObject object, String key)
    {
        return object.has(key)
                && object.get(key).isJsonPrimitive()
                && object.getAsJsonPrimitive(key).isNumber();
    }

    public static int getInt(JsonObject object, String key)
    {
        return object.getAsJsonPrimitive(key).getAsInt();
    }

    public static boolean isString(JsonObject object, String key)
    {
        return object.has(key)
                && object.get(key).isJsonPrimitive()
                && object.getAsJsonPrimitive(key).isString();
    }

    public static String getString(JsonObject object, String key)
    {
        return object.getAsJsonPrimitive(key).getAsString();
    }

}
