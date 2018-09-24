package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.JsonElement;

public abstract class JsonTransformer<T>
{
    public abstract T transform(JsonElement object);
}
