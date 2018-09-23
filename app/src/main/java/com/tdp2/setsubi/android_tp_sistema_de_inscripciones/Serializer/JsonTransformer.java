package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import org.json.JSONObject;

public abstract class JsonTransformer<T>
{
    public abstract T transform(JSONObject object);
}
