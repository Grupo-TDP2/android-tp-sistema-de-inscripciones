package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils;

public class JsonSubscibeFinal extends JsonTransformer<Integer> {

    @Override
    public Integer transform(JsonElement object) {
        if( object.isJsonObject() )
        {
            JsonObject subscription = object.getAsJsonObject();
            if( JsonUtils.isInt(subscription, JsonKeys.ID) )
            {
                return JsonUtils.getInt(subscription, JsonKeys.ID);
            }
        }
        return null;
    }
}
