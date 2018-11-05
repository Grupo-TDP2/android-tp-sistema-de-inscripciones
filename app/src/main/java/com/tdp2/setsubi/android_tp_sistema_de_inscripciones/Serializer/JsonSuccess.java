package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.JsonElement;

public class JsonSuccess extends JsonTransformer<Boolean> {
    @Override
    public Boolean transform(JsonElement object) {
        return true;
    }
}
