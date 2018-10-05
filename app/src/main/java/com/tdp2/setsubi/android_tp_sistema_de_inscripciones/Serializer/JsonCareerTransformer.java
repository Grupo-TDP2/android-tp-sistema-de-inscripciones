package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Career;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils;

public class JsonCareerTransformer extends JsonTransformer<Career> {
    @Override
    public Career transform(JsonElement object)
    {
        if( object.isJsonObject() )
        {
            JsonObject jsonObject = object.getAsJsonObject();
            int id;
            String name = null;
            if( JsonUtils.isInt(jsonObject, JsonKeys.ID) )
            {
                id = JsonUtils.getInt(jsonObject, JsonKeys.ID);
            } else return null;
            if( JsonUtils.isString(jsonObject, JsonKeys.NAME) )
            {
                name = JsonUtils.getString(jsonObject, JsonKeys.NAME);
            } else return null;
            return new Career(id, name);
        }
        return null;
    }
}
