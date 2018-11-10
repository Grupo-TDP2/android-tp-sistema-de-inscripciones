package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.FinalSubscriptionResult;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.AnyNullCheck;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils;

public class JsonSubscibeFinal extends JsonTransformer<FinalSubscriptionResult> {

    @Override
    public FinalSubscriptionResult transform(JsonElement object) {
        if( object.isJsonObject() )
        {
            Integer id = null;
            Boolean freeSubscription = null;
            JsonObject subscription = object.getAsJsonObject();
            if( JsonUtils.isInt(subscription, JsonKeys.ID) )
            {
                id = JsonUtils.getInt(subscription, JsonKeys.ID);
            }
            if( JsonUtils.isString(subscription, JsonKeys.CONDITION))
            {
                freeSubscription = isFree(JsonUtils.getString(subscription, JsonKeys.CONDITION));
            }
            if(id == null || freeSubscription == null) return null;
            return new FinalSubscriptionResult(id, freeSubscription);
        }
        return null;
    }

    private Boolean isFree(String string) {
        return string.toLowerCase().equals("free");
    }
}
